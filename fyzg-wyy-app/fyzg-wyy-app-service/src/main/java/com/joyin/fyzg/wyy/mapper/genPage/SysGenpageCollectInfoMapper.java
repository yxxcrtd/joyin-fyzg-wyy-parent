package com.joyin.fyzg.wyy.mapper.genPage;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joyin.fyzg.wyy.entity.genPage.SysGenpageCollectInfo;

/**
 * @author Administrator
 * @description 针对表【SYS_GENPAGE_COLLECT_INFO(内页配置汇总信息表)】的数据库操作Mapper
 * @createDate 2025-10-10 16:37:56
 * @Entity generator.domain.SysGenpageCollectInfo
 */
public interface SysGenpageCollectInfoMapper extends BaseMapper<SysGenpageCollectInfo> {

    String executeSql(String sql);
}




