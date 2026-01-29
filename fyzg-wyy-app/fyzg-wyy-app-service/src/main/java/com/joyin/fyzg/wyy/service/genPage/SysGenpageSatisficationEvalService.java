package com.joyin.fyzg.wyy.service.genPage;


import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.wyy.entity.genPage.SysGenpageSatisficationEval;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【SYS_GENPAGE_SATISFICATION_EVAL(内页配置满意度评价表)】的数据库操作Service
 * @createDate 2025-10-11 14:08:36
 */
public interface SysGenpageSatisficationEvalService {

    MethodResponse queryGenpageSatisficationEval(String pageName);

    PageResponse<SysGenpageSatisficationEval> queryGenpageSatisficationEvalByPage(String pageId, PageWrapper pageWrapper);

    MethodResponse insertGenpageSatisficationEval(SysGenpageSatisficationEval sysGenpageSatisficationEval);

}
