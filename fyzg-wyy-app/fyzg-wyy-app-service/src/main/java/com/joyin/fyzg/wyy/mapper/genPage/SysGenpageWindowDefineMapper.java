package com.joyin.fyzg.wyy.mapper.genPage;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joyin.fyzg.wyy.entity.genPage.SysGenpageWindowDefine;

/**
* @author Administrator
* @description 针对表【SYS_GENPAGE_WINDOW_DEFINE(题库定义表 区分不同的题库)】的数据库操作Mapper
* @createDate 2025-09-25 17:21:11
* @Entity generator.domain.SysGenpageWindowDefine
*/
public interface SysGenpageWindowDefineMapper extends BaseMapper<SysGenpageWindowDefine> {

   Long selectMaxId();
}




