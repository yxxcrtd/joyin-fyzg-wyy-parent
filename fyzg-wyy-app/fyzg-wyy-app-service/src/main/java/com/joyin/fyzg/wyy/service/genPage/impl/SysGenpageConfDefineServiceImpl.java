package com.joyin.fyzg.wyy.service.genPage.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.config.exception.ChainExceptionUtils;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.wyy.entity.genPage.SysGenpageConfDefine;
import com.joyin.fyzg.wyy.mapper.genPage.SysGenpageConfDefineMapper;
import com.joyin.fyzg.wyy.service.genPage.SysGenpageConfDefineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.joyin.fyzg.enums.BaseExceptionEnum.*;

/**
 * @author Administrator
 * @description 针对表【SYS_GENPAGE_CONF_DEFINE(内页配置信息定义表)】的数据库操作Service实现
 * @createDate 2025-09-25 17:21:37
 */
@Service
@Slf4j
public class SysGenpageConfDefineServiceImpl implements SysGenpageConfDefineService {

    @Autowired
    SysGenpageConfDefineMapper sysGenpageConfDefineMapper;

    @Override
    public MethodResponse queryGenpageConfDefine(String pageId) {
        try {
            List<SysGenpageConfDefine> sysGenpageConfDefineList = Lists.newArrayList();
            QueryWrapper<SysGenpageConfDefine> queryWrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(pageId)) {
                queryWrapper.lambda().eq(SysGenpageConfDefine::getPageId, pageId);
            }
            sysGenpageConfDefineList = sysGenpageConfDefineMapper.selectList(queryWrapper);
            return MethodResponse.success(sysGenpageConfDefineList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("【公共页面】获取页面配置信息出错", e);
            return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(SysGenpageConfDefine.class, e));
        }
    }


    @Override
    public PageResponse<SysGenpageConfDefine> queryGenpageConfDefineByPage(String pageId, PageWrapper pageWrapper) {
        Page<SysGenpageConfDefine> page = new Page<>(pageWrapper.getCurrentPage(), pageWrapper.getPageSize());
        QueryWrapper<SysGenpageConfDefine> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(pageId)) {
            queryWrapper.lambda().eq(SysGenpageConfDefine::getPageId, pageId);
        }
        IPage<SysGenpageConfDefine> confDefinePage = sysGenpageConfDefineMapper.selectPage(page, queryWrapper);
        PageResponse<SysGenpageConfDefine> pageResponse = new PageResponse<>(confDefinePage);
        return pageResponse;
    }

    @Override
    public MethodResponse batchInsertGenpageConfDefine(List<SysGenpageConfDefine> sysGenpageConfDefineList) {
        try {
            Date date = new Date();
            sysGenpageConfDefineList.stream().forEach(sysGenpageConfDefine -> {
                sysGenpageConfDefine.setCreateTime(date);
                sysGenpageConfDefine.setUpdateTime(date);
            });
            sysGenpageConfDefineList.forEach(sysGenpageConfDefineMapper::insert);
            return MethodResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(BATCH_INSERT_FAIL.formatEntity(SysGenpageConfDefine.class, e).getErrorMsg(), e);
            return ChainExceptionUtils.throwExceptionWithReturn(BATCH_INSERT_FAIL.format(e.getMessage()), e);
        }
    }

    @Override
    public MethodResponse updateGenpageConfDefine(SysGenpageConfDefine sysGenpageConfDefine) {
        try {
            Date date = new Date();
            sysGenpageConfDefine.setUpdateTime(date);
            sysGenpageConfDefineMapper.updateById(sysGenpageConfDefine);
            return MethodResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(BATCH_UPDATE_FAIL.formatEntity(SysGenpageConfDefine.class, e).getErrorMsg(), e);
            return ChainExceptionUtils.throwExceptionWithReturn(BATCH_UPDATE_FAIL.format(e.getMessage()), e);
        }
    }

    @Override
    public MethodResponse batchDeleteGenpageConfDefine(List<String> idList) {
        try {
            sysGenpageConfDefineMapper.deleteBatchIds(idList);
            return MethodResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(BATCH_DELETE_FAIL.formatEntity(SysGenpageConfDefine.class, e).getErrorMsg(), e);
            return ChainExceptionUtils.throwExceptionWithReturn(BATCH_DELETE_FAIL.format(e.getMessage()), e);
        }
    }


}




