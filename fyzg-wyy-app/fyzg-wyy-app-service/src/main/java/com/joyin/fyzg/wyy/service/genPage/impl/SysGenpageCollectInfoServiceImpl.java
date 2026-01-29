package com.joyin.fyzg.wyy.service.genPage.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.config.exception.ChainExceptionUtils;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.utils.DynamicSqlExecutorUtils;
import com.joyin.fyzg.wyy.entity.genPage.SysGenpageCollectInfo;
import com.joyin.fyzg.wyy.mapper.genPage.SysGenpageCollectInfoMapper;
import com.joyin.fyzg.wyy.service.genPage.SysGenpageCollectInfoService;
import com.joyin.fyzg.wyy.utils.PlaceHolderUtils;
import com.joyin.fyzg.wyy.vo.genPage.SysGenpageCollectInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.joyin.fyzg.enums.BaseExceptionEnum.*;

/**
 * @author Administrator
 * @description 针对表【SYS_GENPAGE_COLLECT_INFO(内页配置汇总信息表)】的数据库操作Service实现
 * @createDate 2025-10-10 16:37:56
 */
@Service
@Slf4j
public class SysGenpageCollectInfoServiceImpl implements SysGenpageCollectInfoService {

    @Autowired
    SysGenpageCollectInfoMapper sysGenpageCollectInfoMapper;

    @Autowired
    DynamicSqlExecutorUtils dynamicSqlExecutorUtils;


    //传入id列表，查询结果
    @Override
    public List<SysGenpageCollectInfo> queryCollectInfoResult(String collectInfo) {
        if (StringUtils.isEmpty(collectInfo)) {
            return null;
        }
        String[] collectInfos = collectInfo.split(",");
        List<SysGenpageCollectInfo> sysGenpageCollectInfoList = sysGenpageCollectInfoMapper.selectBatchIds(Arrays.asList(collectInfos));
        if (CollectionUtils.isEmpty(sysGenpageCollectInfoList)) {
            return null;
        }

        for (SysGenpageCollectInfo sysGenpageCollectInfo : sysGenpageCollectInfoList) {
            String collectDesp = sysGenpageCollectInfo.getCollectDesp();
            String sql = sysGenpageCollectInfo.getCollectSql();
            try {
                String sqlResult = sysGenpageCollectInfoMapper.executeSql(sql);
                sysGenpageCollectInfo.setCollectSql(sqlResult);
            } catch (Exception e) {
                log.info("【内页配置】汇总信息描述{},sql{}执行错误", collectDesp, sql);
                sysGenpageCollectInfo.setCollectSql("配置sql执行错误");
            }
        }
        return sysGenpageCollectInfoList;
    }

    @Override
    public List<SysGenpageCollectInfoVO> queryCollectInfoResultByParam(String collectInfo, Map<String, Object> paramMap) {
        if (StringUtils.isEmpty(collectInfo)) {
            return null;
        }
        String[] collectInfos = collectInfo.split(",");
        List<SysGenpageCollectInfo> sysGenpageCollectInfoList = sysGenpageCollectInfoMapper.selectBatchIds(Arrays.asList(collectInfos));
        if (CollectionUtils.isEmpty(sysGenpageCollectInfoList)) {
            return null;
        }
        List<SysGenpageCollectInfoVO> sysGenpageCollectInfoVOList = Lists.newArrayList();
        for (SysGenpageCollectInfo sysGenpageCollectInfo : sysGenpageCollectInfoList) {
            SysGenpageCollectInfoVO sysGenpageCollectInfoVO = new SysGenpageCollectInfoVO();
            String collectDesp = sysGenpageCollectInfo.getCollectDesp();
            sysGenpageCollectInfoVO.setCollectDesp(collectDesp);
            String sql = sysGenpageCollectInfo.getCollectSql();
            try {
                Object sqlResult = this.queryResultBySql(sql, paramMap);
                if (sqlResult != null) {
                    sysGenpageCollectInfoVO.setCollectSql(sqlResult);
                } else {
                    log.info("【内页配置】汇总信息描述{},sql{}执行结果返回为空", collectDesp, sql);
                    sysGenpageCollectInfoVO.setCollectSql("null");
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.info("【内页配置】汇总信息描述{},sql{}执行错误", collectDesp, sql);
                sysGenpageCollectInfoVO.setCollectSql("配置sql执行错误");
            }
            sysGenpageCollectInfoVOList.add(sysGenpageCollectInfoVO);
        }
        return sysGenpageCollectInfoVOList;
    }

    private Object queryResultBySql(String sql, Map<String, Object> paramMap) {
        String userCode = (String) paramMap.get("userCode");
        PlaceHolderUtils.dealWithSession4Sql(userCode, paramMap);
        Object clientId = paramMap.get("clientId");
        if (clientId != null) {
            paramMap.put("APP-CLIENT_TYPE", clientId);
        }
        log.info("【内页配置】汇总信息调用common开始查询,sql{}，执行参数{}", sql, JSON.toJSON(paramMap));
        List<Map> listMapResult = dynamicSqlExecutorUtils.findListDataWithParse(sql, paramMap);
        log.info("【内页配置】汇总信息调用common结束查询,sql{}，查询结果{}", sql, JSON.toJSON(listMapResult));
        if (CollectionUtils.isEmpty(listMapResult)) {
            log.info("【内页配置】,sql{}查询结果为空", sql);
            return null;
        }
        Map mapResult = listMapResult.get(0);
        if (mapResult != null) {
            for (Object value : mapResult.values()) {
                return value;
            }
        }
        return null;
    }

    @Override
    public MethodResponse queryGenpageCollectInfo(String collectDesp) {
        try {
            List<SysGenpageCollectInfo> sysGenpageCollectInfoList = Lists.newArrayList();
            QueryWrapper<SysGenpageCollectInfo> queryWrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(collectDesp)) {
                queryWrapper.lambda().like(SysGenpageCollectInfo::getCollectDesp, collectDesp);
            }
            sysGenpageCollectInfoList = sysGenpageCollectInfoMapper.selectList(queryWrapper);
            return MethodResponse.success(sysGenpageCollectInfoList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("【内页配置】汇总信息获取sql配置信息出错", e);
            return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(SysGenpageCollectInfo.class, e));
        }
    }


    @Override
    public PageResponse<SysGenpageCollectInfo> queryGenpageCollectInfoByPage(String collectDesp, PageWrapper pageWrapper) {
        Page<SysGenpageCollectInfo> page = new Page<>(pageWrapper.getCurrentPage(), pageWrapper.getPageSize());
        QueryWrapper<SysGenpageCollectInfo> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(collectDesp)) {
            queryWrapper.lambda().eq(SysGenpageCollectInfo::getCollectDesp, collectDesp);
        }
        IPage<SysGenpageCollectInfo> confDefinePage = sysGenpageCollectInfoMapper.selectPage(page, queryWrapper);
        PageResponse<SysGenpageCollectInfo> pageResponse = new PageResponse<>(confDefinePage);
        return pageResponse;
    }

    @Override
    public MethodResponse batchInsertGenpageCollectInfo(List<SysGenpageCollectInfo> sysGenpageCollectInfoList) {
        try {
            Date date = new Date();
            sysGenpageCollectInfoList.stream().forEach(sysGenpageCollectInfo -> {
                sysGenpageCollectInfo.setCreateTime(date);
                sysGenpageCollectInfo.setUpdateTime(date);
            });
            sysGenpageCollectInfoList.forEach(sysGenpageCollectInfoMapper::insert);
            return MethodResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(BATCH_INSERT_FAIL.formatEntity(SysGenpageCollectInfo.class, e).getErrorMsg(), e);
            return ChainExceptionUtils.throwExceptionWithReturn(BATCH_INSERT_FAIL.format(e.getMessage()), e);
        }
    }

    @Override
    public MethodResponse updateGenpageCollectInfo(SysGenpageCollectInfo sysGenpageCollectInfo) {
        try {
            Date date = new Date();
            sysGenpageCollectInfo.setUpdateTime(date);
            sysGenpageCollectInfoMapper.updateById(sysGenpageCollectInfo);
            return MethodResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(BATCH_UPDATE_FAIL.formatEntity(SysGenpageCollectInfo.class, e).getErrorMsg(), e);
            return ChainExceptionUtils.throwExceptionWithReturn(BATCH_UPDATE_FAIL.format(e.getMessage()), e);
        }
    }

    @Override
    public MethodResponse batchDeleteGenpageCollectInfo(List<String> idList) {
        try {
            sysGenpageCollectInfoMapper.deleteBatchIds(idList);
            return MethodResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(BATCH_DELETE_FAIL.formatEntity(SysGenpageCollectInfo.class, e).getErrorMsg(), e);
            return ChainExceptionUtils.throwExceptionWithReturn(BATCH_DELETE_FAIL.format(e.getMessage()), e);
        }
    }
}




