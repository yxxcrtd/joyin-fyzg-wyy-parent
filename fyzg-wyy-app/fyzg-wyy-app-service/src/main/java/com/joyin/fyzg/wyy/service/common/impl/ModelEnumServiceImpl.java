package com.joyin.fyzg.wyy.service.common.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.entity.common.ModelEnumDO;
import com.joyin.fyzg.wyy.mapper.common.ModelEnumMapper;
import com.joyin.fyzg.wyy.service.common.ModelEnumItemService;
import com.joyin.fyzg.wyy.service.common.ModelEnumService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.joyin.fyzg.enums.BaseExceptionEnum.*;


@Service
@Slf4j
@Transactional
public class ModelEnumServiceImpl implements ModelEnumService {

    @Autowired
    ModelEnumMapper modelEnumMapper;

    @Autowired
    ModelEnumItemService modelEnumItemService;

    @Override
    public MethodResponse insertEnum(ModelEnumDO modelEnumDO) {
        try {
            modelEnumMapper.insert(modelEnumDO);
            return MethodResponse.success();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(INSERT_FAIL.formatEntity(ModelEnumDO.class, e).getErrorMsg(), e);
            return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(ModelEnumDO.class, e));
        }
    }

    @Override
    public MethodResponse updateEnumById(ModelEnumDO modelEnumDO) {
        try {
            modelEnumMapper.updateById(modelEnumDO);
            return MethodResponse.success();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(UPDATE_FAIL.formatEntity(ModelEnumDO.class, e).getErrorMsg(), e);
            return MethodResponse.transWithCodeMsgEnum(UPDATE_FAIL.formatEntity(ModelEnumDO.class, e));
        }
    }

    @Override
    public MethodResponse deleteEnumById(String enumCode) {
        try {
            modelEnumItemService.deleteEnumItemByCode(enumCode);
            modelEnumMapper.deleteById(enumCode);
            return MethodResponse.success();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(DELETE_FAIL.formatEntity(ModelEnumDO.class, e).getErrorMsg(), e);
            return MethodResponse.transWithCodeMsgEnum(DELETE_FAIL.formatEntity(ModelEnumDO.class, e));
        }
    }

    @Override
    public List<ModelEnumDO> listAll() {

        QueryWrapper<ModelEnumDO> query = new QueryWrapper<ModelEnumDO>();
        //        0表示开发在固定模块使用
        query.lambda().eq(ModelEnumDO::getEnumVisible, "1");
        return modelEnumMapper.selectList(query);
    }

    @Override
    public List<ModelEnumDO> listAllByType(String oType) {
        QueryWrapper<ModelEnumDO> query = new QueryWrapper<>();
        query.lambda().eq(ModelEnumDO::getEnumVisible, "1");
        query.lambda().eq(ModelEnumDO::getEnumOtype, "0");
        if(StringUtils.isNotEmpty(oType)){
            query.or().apply("instr( ',' || enum_otype || ','  , '," + oType + ",' ) != 0");
        }
        return modelEnumMapper.selectList(query);
    }

    @Override
    public List<ModelEnumDO> listEnumByEnumName(String enumName) {
        QueryWrapper<ModelEnumDO> query = new QueryWrapper<ModelEnumDO>();
        if(StringUtils.isNotEmpty(enumName)){
            query.lambda().like(ModelEnumDO::getEnumName, enumName);
            query.lambda().eq(ModelEnumDO::getEnumVisible, "1");
        }
        return modelEnumMapper.selectList(query);
    }
}

