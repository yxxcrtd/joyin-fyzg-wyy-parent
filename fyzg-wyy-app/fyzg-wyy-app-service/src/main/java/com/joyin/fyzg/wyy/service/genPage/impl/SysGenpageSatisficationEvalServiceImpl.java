package com.joyin.fyzg.wyy.service.genPage.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.config.exception.ChainExceptionUtils;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.wyy.entity.genPage.SysGenpageSatisficationEval;
import com.joyin.fyzg.wyy.mapper.genPage.SysGenpageSatisficationEvalMapper;
import com.joyin.fyzg.wyy.service.genPage.SysGenpageSatisficationEvalService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.joyin.fyzg.enums.BaseExceptionEnum.*;

/**
 * @author Administrator
 * @description 针对表【SYS_GENPAGE_SATISFICATION_EVAL(内页配置满意度评价表)】的数据库操作Service实现
 * @createDate 2025-10-11 14:08:36
 */
@Service
@Slf4j
public class SysGenpageSatisficationEvalServiceImpl implements SysGenpageSatisficationEvalService {

    @Autowired
    SysGenpageSatisficationEvalMapper sysGenpageSatisficationEvalMapper;

    @Override
    public MethodResponse queryGenpageSatisficationEval(String pageName) {
        try {
            List<SysGenpageSatisficationEval> sysGenpageSatisficationEvalList = Lists.newArrayList();
            QueryWrapper<SysGenpageSatisficationEval> queryWrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(pageName)) {
                queryWrapper.lambda().like(SysGenpageSatisficationEval::getPageName, pageName);
            }
            sysGenpageSatisficationEvalList = sysGenpageSatisficationEvalMapper.selectList(queryWrapper);
            return MethodResponse.success(sysGenpageSatisficationEvalList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("【内页配置】满意度评价查询出错", e);
            return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(SysGenpageSatisficationEval.class, e));
        }
    }


    @Override
    public PageResponse<SysGenpageSatisficationEval> queryGenpageSatisficationEvalByPage(String pageName, PageWrapper pageWrapper) {
        Page<SysGenpageSatisficationEval> page = new Page<>(pageWrapper.getCurrentPage(), pageWrapper.getPageSize());
        QueryWrapper<SysGenpageSatisficationEval> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(pageName)) {
            queryWrapper.lambda().eq(SysGenpageSatisficationEval::getPageName, pageName);
        }
        IPage<SysGenpageSatisficationEval> confDefinePage = sysGenpageSatisficationEvalMapper.selectPage(page, queryWrapper);
        PageResponse<SysGenpageSatisficationEval> pageResponse = new PageResponse<>(confDefinePage);
        return pageResponse;
    }

    @Override
    public MethodResponse insertGenpageSatisficationEval(SysGenpageSatisficationEval sysGenpageSatisficationEval) {
        try {
            Date date = new Date();
            sysGenpageSatisficationEval.setCreateTime(date);
            sysGenpageSatisficationEval.setUpdateTime(date);
            sysGenpageSatisficationEvalMapper.insert(sysGenpageSatisficationEval);
            return MethodResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(BATCH_INSERT_FAIL.formatEntity(SysGenpageSatisficationEval.class, e).getErrorMsg(), e);
            return ChainExceptionUtils.throwExceptionWithReturn(BATCH_INSERT_FAIL.format(e.getMessage()), e);
        }
    }
}
