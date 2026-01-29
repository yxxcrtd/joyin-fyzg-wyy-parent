package com.joyin.fyzg.wyy.mapper.genPage;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joyin.fyzg.wyy.entity.genPage.SysGenpageConfDefine;

import java.util.List;

/**
* @author Administrator
* @description 针对表【SYS_GENPAGE_CONF_DEFINE(内页配置信息定义表)】的数据库操作Mapper
* @createDate 2025-09-25 17:21:37
* @Entity generator.domain.SysGenpageConfDefine
*/
public interface SysGenpageConfDefineMapper extends BaseMapper<SysGenpageConfDefine> {
    List<SysGenpageConfDefine> selectByPageId(String pageId);
}




