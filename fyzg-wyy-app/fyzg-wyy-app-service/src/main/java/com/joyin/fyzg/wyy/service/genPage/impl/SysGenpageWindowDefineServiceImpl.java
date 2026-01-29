package com.joyin.fyzg.wyy.service.genPage.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.config.exception.ChainExceptionUtils;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.wyy.entity.genPage.SysGenpageWindowDefine;
import com.joyin.fyzg.wyy.entity.genPage.SysGenpageWindowQuestion;
import com.joyin.fyzg.wyy.mapper.genPage.SysGenpageWindowDefineMapper;
import com.joyin.fyzg.wyy.service.genPage.SysGenpageWindowDefineService;
import com.joyin.fyzg.wyy.vo.genPage.SysGenpageWindowDefineVO;
import com.joyin.fyzg.wyy.vo.genPage.SysGenpageWindowQuestionVO;
import com.joyin.fyzg.wyy.vo.genPage.WindowQuestionAnswerVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

import static com.joyin.fyzg.enums.BaseExceptionEnum.*;

/**
 * @author Administrator
 * @description 针对表【SYS_GENPAGE_WINDOW_DEFINE(题库定义表 区分不同的题库)】的数据库操作Service实现
 * @createDate 2025-09-25 17:21:11
 */
@Service
@Slf4j
public class SysGenpageWindowDefineServiceImpl implements SysGenpageWindowDefineService {
    @Autowired
    SysGenpageWindowDefineMapper sysGenpageWindowDefineMapper;

    @Autowired
    SysGenpageWindowQuestionServiceImpl sysGenpageWindowQuestionServiceImpl;

    @Override
    public MethodResponse queryGenpageWindowDefine(String title) {
        try {
            List<SysGenpageWindowDefine> sysGenpageWindowDefineList = Lists.newArrayList();
            QueryWrapper<SysGenpageWindowDefine> queryWrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(title)) {
                queryWrapper.lambda().like(SysGenpageWindowDefine::getTitle, title);
            }
            sysGenpageWindowDefineList = sysGenpageWindowDefineMapper.selectList(queryWrapper);
            return MethodResponse.success(sysGenpageWindowDefineList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("【题库弹窗】获取题库标题出错", e);
            return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(SysGenpageWindowDefine.class, e));
        }
    }

    @Override
    public PageResponse<SysGenpageWindowDefine> queryGenpageWindowDefineByPage(String title, PageWrapper pageWrapper) {
        Page<SysGenpageWindowDefine> page = new Page<>(pageWrapper.getCurrentPage(), pageWrapper.getPageSize());
        QueryWrapper<SysGenpageWindowDefine> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(title)) {
            queryWrapper.lambda().eq(SysGenpageWindowDefine::getTitle, title);
        }
        IPage<SysGenpageWindowDefine> WindowDefinePage = sysGenpageWindowDefineMapper.selectPage(page, queryWrapper);
        PageResponse<SysGenpageWindowDefine> pageResponse = new PageResponse<>(WindowDefinePage);
        return pageResponse;
    }


    @Override
    public MethodResponse insertGenpageWindowDefine(SysGenpageWindowDefineVO SysGenpageWindowConfDefineVO) {
        try {

            //保存题库标题
            String title = SysGenpageWindowConfDefineVO.getTitle();
            //title验重
            SysGenpageWindowDefine sysGenpageWindowDefineExist = getWindowDefineByTitle(title);
            if (sysGenpageWindowDefineExist != null) {
                return ChainExceptionUtils.throwExceptionWithReturn(BATCH_INSERT_FAIL.format("题库标题重复，保存失败"), null);
            }
            SysGenpageWindowDefine sysGenpageWindowDefine = new SysGenpageWindowDefine();
            sysGenpageWindowDefine.setTitle(title);
            Date date = new Date();
            sysGenpageWindowDefine.setCreateTime(date);
            sysGenpageWindowDefine.setUpdateTime(date);
            sysGenpageWindowDefineMapper.insert(sysGenpageWindowDefine);
            SysGenpageWindowDefine sysGenpageWindowDefineNew = getWindowDefineByTitle(title);
            //保存问题答案
            Long newId = sysGenpageWindowDefineNew.getId();
            List<SysGenpageWindowQuestionVO> sysGenpageWindowQuestionVOList = SysGenpageWindowConfDefineVO.getSysGenpageWindowQuestionVOList();
            this.saveWindowQuestion(sysGenpageWindowQuestionVOList, newId);
            return MethodResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(BATCH_INSERT_FAIL.formatEntity(SysGenpageWindowDefine.class, e).getErrorMsg(), e);
            return ChainExceptionUtils.throwExceptionWithReturn(BATCH_INSERT_FAIL.format(e.getMessage()), e);
        }
    }

    private SysGenpageWindowDefine getWindowDefineByTitle(String title) {
        QueryWrapper<SysGenpageWindowDefine> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(title)) {
            queryWrapper.lambda().eq(SysGenpageWindowDefine::getTitle, title);
        }
        SysGenpageWindowDefine sysGenpageWindowDefineNew = sysGenpageWindowDefineMapper.selectOne(queryWrapper);
        return sysGenpageWindowDefineNew;
    }

    public void saveWindowQuestion(List<SysGenpageWindowQuestionVO> sysGenpageWindowQuestionVOList, Long windowId) {
        List<SysGenpageWindowQuestion> sysGenpageWindowQuestionList = Lists.newArrayList();
        Date date = new Date();
        Long questionSeq = 1L;
        if (!CollectionUtils.isEmpty(sysGenpageWindowQuestionVOList)) {
            for (SysGenpageWindowQuestionVO sysGenpageWindowQuestionVO : sysGenpageWindowQuestionVOList) {
                List<WindowQuestionAnswerVO> windowQuestionAnswerVOList = sysGenpageWindowQuestionVO.getWindowQuestionAnswerVOList();
                Long questionAnswerSeq = 1L;
                if (!CollectionUtils.isEmpty(windowQuestionAnswerVOList)) {
                    for (WindowQuestionAnswerVO windowQuestionAnswerVO : windowQuestionAnswerVOList) {
                        SysGenpageWindowQuestion sysGenpageWindowQuestion = new SysGenpageWindowQuestion();
                        sysGenpageWindowQuestion.setWindowId(windowId);
                        sysGenpageWindowQuestion.setQuestionSeq(questionSeq);
                        sysGenpageWindowQuestion.setQuestionType(sysGenpageWindowQuestionVO.getQuestionType());
                        sysGenpageWindowQuestion.setQuestion(sysGenpageWindowQuestionVO.getQuestion());
                        sysGenpageWindowQuestion.setAnswerSeq(questionAnswerSeq);
                        sysGenpageWindowQuestion.setAnswer(windowQuestionAnswerVO.getAnswer());
                        sysGenpageWindowQuestion.setCreateTime(date);
                        sysGenpageWindowQuestion.setUpdateTime(date);
                        sysGenpageWindowQuestionList.add(sysGenpageWindowQuestion);
                        questionAnswerSeq++;
                    }
                } else {
                    SysGenpageWindowQuestion sysGenpageWindowQuestion = new SysGenpageWindowQuestion();
                    sysGenpageWindowQuestion.setWindowId(windowId);
                    sysGenpageWindowQuestion.setQuestionSeq(questionSeq);
                    sysGenpageWindowQuestion.setQuestionType(sysGenpageWindowQuestionVO.getQuestionType());
                    sysGenpageWindowQuestion.setQuestion(sysGenpageWindowQuestionVO.getQuestion());
                    sysGenpageWindowQuestion.setCreateTime(date);
                    sysGenpageWindowQuestion.setUpdateTime(date);
                    sysGenpageWindowQuestionList.add(sysGenpageWindowQuestion);
                    questionAnswerSeq++;
                }
                questionSeq++;
            }

        }
        sysGenpageWindowQuestionServiceImpl.batchInsertGenpageWindowQuestion(sysGenpageWindowQuestionList);
    }


    @Override
    public MethodResponse updateGenpageWindowDefine(SysGenpageWindowDefineVO sysGenpageWindowConfDefineVO) {
        try {
            Long windowId = sysGenpageWindowConfDefineVO.getId();
            SysGenpageWindowDefine sysGenpageWindowDefine = new SysGenpageWindowDefine();
            sysGenpageWindowDefine.setId(windowId);
            sysGenpageWindowDefine.setTitle(sysGenpageWindowConfDefineVO.getTitle());
            sysGenpageWindowDefineMapper.updateById(sysGenpageWindowDefine);
            //更新问题和答案
            sysGenpageWindowQuestionServiceImpl.deleteByWindowId(windowId);
            List<SysGenpageWindowQuestionVO> sysGenpageWindowQuestionVOList = sysGenpageWindowConfDefineVO.getSysGenpageWindowQuestionVOList();
            this.saveWindowQuestion(sysGenpageWindowQuestionVOList, windowId);
            return MethodResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(BATCH_UPDATE_FAIL.formatEntity(SysGenpageWindowDefine.class, e).getErrorMsg(), e);
            return ChainExceptionUtils.throwExceptionWithReturn(BATCH_UPDATE_FAIL.format(e.getMessage()), e);
        }
    }

    @Override
    public MethodResponse batchDeleteGenpageWindowDefine(List<Long> idList) {
        try {
            sysGenpageWindowDefineMapper.deleteBatchIds(idList);
            for (Long windowId : idList) {
                sysGenpageWindowQuestionServiceImpl.deleteByWindowId(windowId);
            }
            return MethodResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(BATCH_DELETE_FAIL.formatEntity(SysGenpageWindowDefine.class, e).getErrorMsg(), e);
            return ChainExceptionUtils.throwExceptionWithReturn(BATCH_DELETE_FAIL.format(e.getMessage()), e);
        }
    }
}




