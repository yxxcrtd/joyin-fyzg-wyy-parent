package com.joyin.fyzg.wyy.service.genPage;


import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.vo.genPage.SysGenpageWindowAnswerVO;

/**
 * @author Administrator
 * @description 针对表【SYS_GENPAGE_WINDOW_ANSWER(弹窗答案结果表 答题后记录)】的数据库操作Service
 * @createDate 2025-09-25 17:21:32
 */
public interface SysGenpageWindowAnswerService {
    MethodResponse batchInsertGenpageWindowAnswer(SysGenpageWindowAnswerVO sysGenpageWindowAnswerVO);

    MethodResponse queryUserIsAnswer(String windowId, String userName);
}
