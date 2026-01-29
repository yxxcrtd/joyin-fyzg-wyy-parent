package com.joyin.fyzg.wyy.service.genPage;


import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.wyy.entity.genPage.SysGenpageSatisficationEvalAttachment;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Administrator
 * @description 针对表【SYS_GENPAGE_SATISFICATION_EVAL_ATTACHMENT(内页配置满意度评价附件表)】的数据库操作Service
 * @createDate 2025-10-11 14:09:25
 */
public interface SysGenpageSatisficationEvalAttachmentService {

    String uploadTemp(MultipartFile file, String userId, String pageId) throws IOException;

    Resource download(String id);

    boolean deleteTempAttachment(String id);
}
