package com.joyin.fyzg.wyy.service.genPage;


import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.wyy.entity.genPage.SysGenpageCollectInfo;
import com.joyin.fyzg.wyy.vo.genPage.SysGenpageCollectInfoVO;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @description 针对表【SYS_GENPAGE_COLLECT_INFO(内页配置汇总信息表)】的数据库操作Service
 * @createDate 2025-10-10 16:37:56
 */
public interface SysGenpageCollectInfoService {
    List<SysGenpageCollectInfo> queryCollectInfoResult(String collectInfo);

    List<SysGenpageCollectInfoVO> queryCollectInfoResultByParam(String collectInfo, Map<String, Object> paramMap);

    MethodResponse queryGenpageCollectInfo(String collectDesp);

    PageResponse<SysGenpageCollectInfo> queryGenpageCollectInfoByPage(String collectDesp, PageWrapper pageWrapper);

    MethodResponse batchInsertGenpageCollectInfo(List<SysGenpageCollectInfo> sysGenpageCollectInfoList);

    MethodResponse updateGenpageCollectInfo(SysGenpageCollectInfo sysGenpageCollectInfo);

    MethodResponse batchDeleteGenpageCollectInfo(List<String> idList);
}
