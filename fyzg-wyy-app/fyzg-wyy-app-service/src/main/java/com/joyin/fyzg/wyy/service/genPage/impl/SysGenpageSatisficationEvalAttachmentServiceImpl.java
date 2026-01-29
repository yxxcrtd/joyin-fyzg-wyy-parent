package com.joyin.fyzg.wyy.service.genPage.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.config.exception.ChainExceptionUtils;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.utils.DateUtil8;
import com.joyin.fyzg.wyy.config.cas.config.FileUploadProperties;
import com.joyin.fyzg.wyy.entity.genPage.SysGenpageSatisficationEvalAttachment;
import com.joyin.fyzg.wyy.mapper.genPage.SysGenpageSatisficationEvalAttachmentMapper;
import com.joyin.fyzg.wyy.service.genPage.SysGenpageSatisficationEvalAttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.joyin.fyzg.enums.BaseExceptionEnum.*;

/**
 * @author Administrator
 * @description 针对表【SYS_GENPAGE_SATISFICATION_EVAL_ATTACHMENT(内页配置满意度评价附件表)】的数据库操作Service实现
 * @createDate 2025-10-11 14:09:25
 */
@Service
@Slf4j
public class SysGenpageSatisficationEvalAttachmentServiceImpl implements SysGenpageSatisficationEvalAttachmentService {

    @Autowired
    private FileUploadProperties uploadProperties;

    @Autowired
    SysGenpageSatisficationEvalAttachmentMapper sysGenpageSatisficationEvalAttachmentMapper;

    @Override
    public String uploadTemp(MultipartFile file, String userId, String pageId) throws IOException {
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
        SysGenpageSatisficationEvalAttachment attachment = new SysGenpageSatisficationEvalAttachment();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        attachment.setId(uuid);
        attachment.setUserId(userId);
        attachment.setPageId(pageId);
        attachment.setAttachmentId(attachmentId);
        attachment.setFileName(originalFileName);
        attachment.setFilePath(filePath.toString());
        attachment.setFileType(file.getContentType());
        attachment.setFileSize(file.getSize());
        attachment.setUploadTime(DateUtil8.getNowTime_EN());
        //使用 Mapper 插入数据库
        int result = sysGenpageSatisficationEvalAttachmentMapper.insert(attachment);
        if (result == 0) {
            throw new IOException("附件记录保存失败");
        }

        return uuid;
    }


    @Override
    public Resource download(String id) {
        QueryWrapper<SysGenpageSatisficationEvalAttachment> query = new QueryWrapper<>();
        query.lambda().eq(SysGenpageSatisficationEvalAttachment::getId, id);
        SysGenpageSatisficationEvalAttachment attachment = sysGenpageSatisficationEvalAttachmentMapper.selectOne(query);
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

    @Override
    public boolean deleteTempAttachment(String id) {
        QueryWrapper<SysGenpageSatisficationEvalAttachment> query = new QueryWrapper<>();
        query.lambda()
                .eq(SysGenpageSatisficationEvalAttachment::getId, id);

        SysGenpageSatisficationEvalAttachment attachment = sysGenpageSatisficationEvalAttachmentMapper.selectOne(query);
        if (attachment == null) {
            return false;
        }

        boolean fileDeleted = deletePhysicalFile(attachment.getFilePath());
        if (fileDeleted) {
            sysGenpageSatisficationEvalAttachmentMapper.deleteById(attachment.getId());
            return true;
        }
        return false;
    }

    private boolean deletePhysicalFile(String filePath) {
        if (filePath == null) return false;
        File file = new File(filePath);
        return file.exists() && file.delete();
    }

}




