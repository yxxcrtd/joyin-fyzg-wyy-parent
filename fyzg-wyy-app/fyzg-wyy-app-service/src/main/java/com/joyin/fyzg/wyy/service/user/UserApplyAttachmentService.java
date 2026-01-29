package com.joyin.fyzg.wyy.service.user;

import com.joyin.fyzg.wyy.entity.rbac.UserApplyAttachmentDO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserApplyAttachmentService {
    /**
     * 临时上传附件（此时无 applyId）
     *
     * @param file     文件
     * @return 附件对象（包含 attachmentId）
     */
    UserApplyAttachmentDO uploadTemp(MultipartFile file,String type) throws IOException;

    /**
     * 将临时附件关联到申请单
     *
     * @param attachmentId 附件ID
     * @param applyId      申请单ID
     * @return 是否关联成功
     */
    boolean associateToApply(String attachmentId, String applyId);

    /**
     * 根据 applyId 删除所有附件（含物理文件）
     */
    int deleteByApplyId(String applyId);

    /**
     * 删除临时附件（上传后未关联的）
     */
    boolean deleteTempAttachment(String attachmentId);

    /**
     * 查询某申请单的所有附件
     */
    List<UserApplyAttachmentDO> listByApplyId(String applyId);

    /**
     * 下载附件
     */
    Resource download(String attachmentId);
}
