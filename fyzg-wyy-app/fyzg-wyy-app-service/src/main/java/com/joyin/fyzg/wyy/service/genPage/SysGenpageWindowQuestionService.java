package com.joyin.fyzg.wyy.service.genPage;


import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.entity.genPage.SysGenpageWindowQuestion;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【SYS_GENPAGE_WINDOW_QUESTION(弹窗问题表 区分不同题型)】的数据库操作Service
 * @createDate 2025-09-25 17:21:24
 */
public interface SysGenpageWindowQuestionService {
    MethodResponse queryGenpageWindowQuestion(String windowId);

    MethodResponse batchInsertGenpageWindowQuestion(List<SysGenpageWindowQuestion> sysGenpageWindowQuestionList);

    MethodResponse updateGenpageWindowQuestion(SysGenpageWindowQuestion sysGenpageWindowQuestion);

    MethodResponse batchDeleteGenpageWindowQuestion(List<String> idList);
}
