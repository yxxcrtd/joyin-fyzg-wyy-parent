package com.joyin.fyzg.wyy.service.genPage.impl;

import com.google.common.collect.Lists;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.config.exception.ChainExceptionUtils;
import com.joyin.fyzg.wyy.entity.genPage.SysGenpageWindowQuestion;
import com.joyin.fyzg.wyy.mapper.genPage.SysGenpageWindowQuestionMapper;
import com.joyin.fyzg.wyy.service.genPage.SysGenpageWindowQuestionService;
import com.joyin.fyzg.wyy.vo.genPage.SysGenpageWindowQuestionVO;
import com.joyin.fyzg.wyy.vo.genPage.WindowQuestionAnswerVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.joyin.fyzg.enums.BaseExceptionEnum.*;

/**
 * @author Administrator
 * @description 针对表【SYS_GENPAGE_WINDOW_QUESTION(弹窗问题表 区分不同题型)】的数据库操作Service实现
 * @createDate 2025-09-25 17:21:24
 */
@Service
@Slf4j
public class SysGenpageWindowQuestionServiceImpl implements SysGenpageWindowQuestionService {

    @Autowired
    SysGenpageWindowQuestionMapper sysGenpageWindowQuestionMapper;


    @Override
    public MethodResponse queryGenpageWindowQuestion(String windowId) {
        try {
            List<SysGenpageWindowQuestion> sysGenpageWindowQuestionList = sysGenpageWindowQuestionMapper.selectByWindowId(Long.parseLong(windowId));
            List<Long> questionSeqList = sysGenpageWindowQuestionList.stream().map(e -> e.getQuestionSeq()).distinct().collect(Collectors.toList());
            Map<Long, List<SysGenpageWindowQuestion>> sysGenpageWindowQuestionMap = sysGenpageWindowQuestionList.stream().collect(Collectors.groupingBy(e -> e.getQuestionSeq()));
            List<SysGenpageWindowQuestionVO> windowQuestionVOs = Lists.newArrayList();
            for (Long questionSeq : questionSeqList) {
                SysGenpageWindowQuestionVO sysGenpageWindowQuestionVO = new SysGenpageWindowQuestionVO();
                List<SysGenpageWindowQuestion> windowQuestionList = sysGenpageWindowQuestionMap.get(questionSeq);
                SysGenpageWindowQuestion windowQuestion = windowQuestionList.get(0);
                sysGenpageWindowQuestionVO.setWindowId(windowQuestion.getWindowId());
                sysGenpageWindowQuestionVO.setQuestionSeq(windowQuestion.getQuestionSeq());
                sysGenpageWindowQuestionVO.setQuestion(windowQuestion.getQuestion());
                sysGenpageWindowQuestionVO.setQuestionType(windowQuestion.getQuestionType());
                List<WindowQuestionAnswerVO> windowQuestionAnswerVOList = Lists.newArrayList();
                windowQuestionList.stream().forEach(windowQuestione -> {
                    WindowQuestionAnswerVO windowQuestionAnswerVO = new WindowQuestionAnswerVO();
                    windowQuestionAnswerVO.setAnswerSeq(windowQuestione.getAnswerSeq());
                    windowQuestionAnswerVO.setAnswer(windowQuestione.getAnswer());
                    windowQuestionAnswerVOList.add(windowQuestionAnswerVO);
                });
                sysGenpageWindowQuestionVO.setWindowQuestionAnswerVOList(windowQuestionAnswerVOList);
                windowQuestionVOs.add(sysGenpageWindowQuestionVO);
            }
            return MethodResponse.success(windowQuestionVOs);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("【【题库弹窗】获取题库题目出错", e);
            return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(SysGenpageWindowQuestion.class, e));
        }
    }


    @Override
    public MethodResponse batchInsertGenpageWindowQuestion(List<SysGenpageWindowQuestion> sysGenpageWindowQuestionList) {
        try {
            sysGenpageWindowQuestionList.forEach(sysGenpageWindowQuestionMapper::insert);
            return MethodResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(BATCH_INSERT_FAIL.formatEntity(SysGenpageWindowQuestion.class, e).getErrorMsg(), e);
            return ChainExceptionUtils.throwExceptionWithReturn(BATCH_INSERT_FAIL.format(e.getMessage()), e);
        }
    }

    @Override
    public MethodResponse updateGenpageWindowQuestion(SysGenpageWindowQuestion sysGenpageWindowQuestion) {
        try {
            sysGenpageWindowQuestionMapper.updateById(sysGenpageWindowQuestion);
            return MethodResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(BATCH_UPDATE_FAIL.formatEntity(SysGenpageWindowQuestion.class, e).getErrorMsg(), e);
            return ChainExceptionUtils.throwExceptionWithReturn(BATCH_UPDATE_FAIL.format(e.getMessage()), e);
        }
    }

    @Override
    public MethodResponse batchDeleteGenpageWindowQuestion(List<String> idList) {
        try {
            sysGenpageWindowQuestionMapper.deleteBatchIds(idList);
            return MethodResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(BATCH_DELETE_FAIL.formatEntity(SysGenpageWindowQuestion.class, e).getErrorMsg(), e);
            return ChainExceptionUtils.throwExceptionWithReturn(BATCH_DELETE_FAIL.format(e.getMessage()), e);
        }
    }


    public MethodResponse deleteByWindowId(Long windowId) {
        try {
            sysGenpageWindowQuestionMapper.deleteByWindowId(windowId);
            return MethodResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(BATCH_DELETE_FAIL.formatEntity(SysGenpageWindowQuestion.class, e).getErrorMsg(), e);
            return ChainExceptionUtils.throwExceptionWithReturn(BATCH_DELETE_FAIL.format(e.getMessage()), e);
        }
    }

}




