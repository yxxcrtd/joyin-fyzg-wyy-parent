package com.joyin.fyzg.wyy.service.genPage.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.config.exception.ChainExceptionUtils;
import com.joyin.fyzg.wyy.entity.genPage.SysGenpageWindowAnswer;
import com.joyin.fyzg.wyy.mapper.genPage.SysGenpageWindowAnswerMapper;
import com.joyin.fyzg.wyy.service.genPage.SysGenpageWindowAnswerService;
import com.joyin.fyzg.wyy.vo.genPage.SysGenpageWindowAnswerVO;
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
 * @description 针对表【SYS_GENPAGE_WINDOW_ANSWER(弹窗答案结果表 答题后记录)】的数据库操作Service实现
 * @createDate 2025-09-25 17:21:32
 */
@Service
@Slf4j
public class SysGenpageWindowAnswerServiceImpl implements SysGenpageWindowAnswerService {

    @Autowired
    SysGenpageWindowAnswerMapper sysGenpageWindowAnswerMapper;


    public MethodResponse queryGenpageWindowAnswer(String windowId, String userName) {
        try {
            List<SysGenpageWindowAnswer> sysGenpageWindowAnswerList = Lists.newArrayList();
            QueryWrapper<SysGenpageWindowAnswer> queryWrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(windowId)) {
                queryWrapper.lambda().eq(SysGenpageWindowAnswer::getWindowId, windowId);
                queryWrapper.lambda().eq(SysGenpageWindowAnswer::getUserName, userName);
            }
            sysGenpageWindowAnswerList = sysGenpageWindowAnswerMapper.selectList(queryWrapper);
            return MethodResponse.success(sysGenpageWindowAnswerList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("【题库弹窗】获取答案出错", e);
            return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(SysGenpageWindowAnswer.class, e));
        }
    }


    @Override
    public MethodResponse batchInsertGenpageWindowAnswer(SysGenpageWindowAnswerVO sysGenpageWindowAnswerVO) {
        List<SysGenpageWindowAnswer> sysGenpageWindowAnswerList = sysGenpageWindowAnswerVO.getSysGenpageWindowAnswerList();
        Long windowID = sysGenpageWindowAnswerVO.getWindowId();
        String userName = sysGenpageWindowAnswerVO.getUserName();
        try {
            Date date = new Date();
            sysGenpageWindowAnswerList.stream().forEach(sysGenpageWindowAnswer -> {
                sysGenpageWindowAnswer.setWindowId(windowID);
                sysGenpageWindowAnswer.setUserName(userName);
                sysGenpageWindowAnswer.setCreateTime(date);
                sysGenpageWindowAnswer.setUpdateTime(date);
            });
            sysGenpageWindowAnswerList.forEach(sysGenpageWindowAnswerMapper::insert);
            return MethodResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(BATCH_INSERT_FAIL.formatEntity(SysGenpageWindowAnswer.class, e).getErrorMsg(), e);
            return ChainExceptionUtils.throwExceptionWithReturn(BATCH_INSERT_FAIL.format(e.getMessage()), e);
        }
    }


    public MethodResponse updateGenpageWindowAnswer(SysGenpageWindowAnswer sysGenpageWindowAnswer) {
        try {
            sysGenpageWindowAnswerMapper.updateById(sysGenpageWindowAnswer);
            return MethodResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(BATCH_UPDATE_FAIL.formatEntity(SysGenpageWindowAnswer.class, e).getErrorMsg(), e);
            return ChainExceptionUtils.throwExceptionWithReturn(BATCH_UPDATE_FAIL.format(e.getMessage()), e);
        }
    }


    public MethodResponse batchDeleteGenpageWindowAnswer(List<String> idList) {
        try {
            sysGenpageWindowAnswerMapper.deleteBatchIds(idList);
            return MethodResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(BATCH_DELETE_FAIL.formatEntity(SysGenpageWindowAnswer.class, e).getErrorMsg(), e);
            return ChainExceptionUtils.throwExceptionWithReturn(BATCH_DELETE_FAIL.format(e.getMessage()), e);
        }
    }

    @Override
    public MethodResponse queryUserIsAnswer(String windowId, String userName) {
        try {
            Boolean result = false;
            List<SysGenpageWindowAnswer> sysGenpageWindowAnswerList = Lists.newArrayList();
            QueryWrapper<SysGenpageWindowAnswer> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(SysGenpageWindowAnswer::getWindowId, windowId);
            queryWrapper.lambda().eq(SysGenpageWindowAnswer::getUserName, userName);
            sysGenpageWindowAnswerList = sysGenpageWindowAnswerMapper.selectList(queryWrapper);
            if (!CollectionUtils.isEmpty(sysGenpageWindowAnswerList)) {
                result = true;
            }
            return MethodResponse.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("【题库弹窗】校验用户是否已经答题", e);
            return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(SysGenpageWindowAnswer.class, e));
        }
    }

}




