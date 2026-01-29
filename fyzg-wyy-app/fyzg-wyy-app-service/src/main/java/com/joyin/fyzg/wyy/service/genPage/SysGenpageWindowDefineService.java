package com.joyin.fyzg.wyy.service.genPage;


import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.wyy.entity.genPage.SysGenpageWindowDefine;
import com.joyin.fyzg.wyy.vo.genPage.SysGenpageWindowDefineVO;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【SYS_GENPAGE_WINDOW_DEFINE(题库定义表 区分不同的题库)】的数据库操作Service
 * @createDate 2025-09-25 17:21:11
 */
public interface SysGenpageWindowDefineService {
    MethodResponse queryGenpageWindowDefine(String title);

    PageResponse<SysGenpageWindowDefine> queryGenpageWindowDefineByPage(String title, PageWrapper pageWrapper);

    public MethodResponse insertGenpageWindowDefine(SysGenpageWindowDefineVO SysGenpageWindowConfDefineVO);

    MethodResponse updateGenpageWindowDefine(SysGenpageWindowDefineVO sysGenpageWindowConfDefineVO);

    MethodResponse batchDeleteGenpageWindowDefine(List<Long> idList);
}
