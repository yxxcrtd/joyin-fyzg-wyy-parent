package com.joyin.fyzg.wyy.service.genPage;


import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.wyy.entity.genPage.SysGenpageConfDefine;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【SYS_GENPAGE_CONF_DEFINE(内页配置信息定义表)】的数据库操作Service
 * @createDate 2025-09-25 17:21:37
 */
public interface SysGenpageConfDefineService {

    MethodResponse queryGenpageConfDefine(String pageId);

    PageResponse<SysGenpageConfDefine> queryGenpageConfDefineByPage(String pageId, PageWrapper pageWrapper);

    MethodResponse batchInsertGenpageConfDefine(List<SysGenpageConfDefine> sysGenpageConfDefineList);

    MethodResponse updateGenpageConfDefine(SysGenpageConfDefine sysGenpageConfDefine);

    MethodResponse batchDeleteGenpageConfDefine(List<String> idList);

}
