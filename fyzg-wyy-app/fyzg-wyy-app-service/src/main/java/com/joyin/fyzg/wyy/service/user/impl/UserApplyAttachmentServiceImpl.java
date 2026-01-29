package com.joyin.fyzg.wyy.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joyin.fyzg.utils.DateUtil8;
import com.joyin.fyzg.wyy.common.utils.UuidGenerator;
import com.joyin.fyzg.wyy.config.cas.config.FileUploadProperties;
import com.joyin.fyzg.wyy.entity.rbac.UserApplyAttachmentDO;
import com.joyin.fyzg.wyy.entity.rbac.UserDO;
import com.joyin.fyzg.wyy.mapper.rbac.UserApplyAttachmentMapper;
import com.joyin.fyzg.wyy.service.user.UserApplyAttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserApplyAttachmentServiceImpl implements UserApplyAttachmentService {
    @Autowired
    private FileUploadProperties uploadProperties;

    @Autowired
    private UserApplyAttachmentMapper attachmentMapper;

    //String attachmentId = uuidGenerator.nextAttachmentId();
    @Override
    public UserApplyAttachmentDO uploadTemp(MultipartFile file,String type) throws IOException {
        String attachmentId = UUID.randomUUID().toString().replace("-", "");
        String originalFileName = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        // 子目录：YYYY-MM-DD
        String dateDir = LocalDate.now().toString();
        Path uploadDir = Paths.get(uploadProperties.getBasePath(), dateDir);
        Files.createDirectories(uploadDir);

        // 文件保存路径
        String fileNameOnServer = attachmentId + fileExtension;
        Path filePath = uploadDir.resolve(fileNameOnServer);
        file.transferTo(filePath.toFile());

        // 构建实体
        UserApplyAttachmentDO attachment = new UserApplyAttachmentDO();
        attachment.setAttachmentId(attachmentId);
        attachment.setFileName(originalFileName);
        attachment.setFilePath(filePath.toString());
        attachment.setFileType(file.getContentType());
        attachment.setFileSize(file.getSize());
        attachment.setUploadTime(DateUtil8.getNowTime_EN());
        attachment.setJyInsertTime(DateUtil8.getNowTime_EN());
        if(StringUtils.isNotBlank(type)){
            attachment.setFileSource(type);
        }else{
            attachment.setFileSource("1");
        }

        // applyId = null，表示临时附件

        //使用 Mapper 插入数据库
        int result = attachmentMapper.insert(attachment);
        if (result == 0) {
            throw new IOException("附件记录保存失败");
        }

        return attachment;
    }

    @Override
    public boolean associateToApply(String attachmentId, String applyId) {
        // 查询附件（只查未关联的）
        QueryWrapper<UserApplyAttachmentDO> query = new QueryWrapper<>();
        query.lambda().eq(UserApplyAttachmentDO::getAttachmentId, attachmentId)
                .isNull(UserApplyAttachmentDO::getApplyId);
        UserApplyAttachmentDO attachment = attachmentMapper.selectOne(query);
        if (attachment == null) {
            return false;
        }
        // 更新 applyId
        attachment.setApplyId(applyId);
        int result = attachmentMapper.updateById(attachment);
        return result > 0;
    }

    @Override
    public int deleteByApplyId(String applyId) {
        QueryWrapper<UserApplyAttachmentDO> query = new QueryWrapper<>();
        query.lambda().eq(UserApplyAttachmentDO::getApplyId, applyId);
        List<UserApplyAttachmentDO> attachments = attachmentMapper.selectList(query);

        int deletedCount = 0;
        for (UserApplyAttachmentDO attachment : attachments) {
            if (deletePhysicalFile(attachment.getFilePath())) {
                attachmentMapper.deleteById(attachment.getRId()); // 删除记录
                deletedCount++;
            }
        }
        return deletedCount;
    }

    @Override
    public boolean deleteTempAttachment(String attachmentId) {
        QueryWrapper<UserApplyAttachmentDO> query = new QueryWrapper<>();
        query.lambda()
                .eq(UserApplyAttachmentDO::getAttachmentId, attachmentId)
                .isNull(UserApplyAttachmentDO::getApplyId);

        UserApplyAttachmentDO attachment = attachmentMapper.selectOne(query);
        if (attachment == null) {
            return false;
        }

        boolean fileDeleted = deletePhysicalFile(attachment.getFilePath());
        if (fileDeleted) {
            attachmentMapper.deleteById(attachment.getRId());
            return true;
        }
        return false;
    }

    @Override
    public List<UserApplyAttachmentDO> listByApplyId(String applyId) {
        QueryWrapper<UserApplyAttachmentDO> query = new QueryWrapper<>();
        query.lambda()
                .eq(UserApplyAttachmentDO::getApplyId, applyId)
                .orderByDesc(UserApplyAttachmentDO::getUploadTime);

        return attachmentMapper.selectList(query);
    }

    @Override
    public Resource download(String attachmentId) {
        QueryWrapper<UserApplyAttachmentDO> query = new QueryWrapper<>();
        query.lambda().eq(UserApplyAttachmentDO::getAttachmentId, attachmentId);

        UserApplyAttachmentDO attachment = attachmentMapper.selectOne(query);
        if (attachment == null || attachment.getFilePath() == null) {
            return null;
        }

        Path filePath = Paths.get(attachment.getFilePath());
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean deletePhysicalFile(String filePath) {
        if (filePath == null) return false;
        File file = new File(filePath);
        return file.exists() && file.delete();
    }

}
