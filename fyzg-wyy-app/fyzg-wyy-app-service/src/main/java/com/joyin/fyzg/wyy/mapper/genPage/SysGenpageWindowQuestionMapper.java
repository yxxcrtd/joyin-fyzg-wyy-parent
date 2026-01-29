package com.joyin.fyzg.wyy.mapper.genPage;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joyin.fyzg.wyy.entity.genPage.SysGenpageWindowQuestion;

import java.util.List;

/**
* @author Administrator
* @description 针对表【SYS_GENPAGE_WINDOW_QUESTION(弹窗问题表 区分不同题型)】的数据库操作Mapper
* @createDate 2025-09-25 17:21:24
* @Entity generator.domain.SysGenpageWindowQuestion
*/
public interface SysGenpageWindowQuestionMapper extends BaseMapper<SysGenpageWindowQuestion> {
    List<SysGenpageWindowQuestion> selectByWindowId(Long windowId);
    void  deleteByWindowId(Long windowId);
}




