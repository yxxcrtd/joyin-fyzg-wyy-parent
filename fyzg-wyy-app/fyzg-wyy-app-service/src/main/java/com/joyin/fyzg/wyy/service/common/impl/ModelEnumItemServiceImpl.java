package com.joyin.fyzg.wyy.service.common.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.entity.common.ModelEnumItemBO;
import com.joyin.fyzg.wyy.entity.common.ModelEnumItemDO;
import com.joyin.fyzg.wyy.mapper.common.ModelEnumItemMapper;
import com.joyin.fyzg.wyy.service.common.ModelEnumItemService;
import com.joyin.fyzg.wyy.vo.common.ModelEnumGroupVO;
import com.joyin.fyzg.wyy.vo.common.ModelEnumItemVO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.joyin.fyzg.enums.BaseExceptionEnum.*;
import static com.joyin.fyzg.wyy.common.exception.MldfExceptionEnum.MODEL_CONSTANT_ENUM_GROUP_BATCH_SAVE_FAIL;

@Service
@Slf4j
@Transactional
public class ModelEnumItemServiceImpl implements ModelEnumItemService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ModelEnumItemMapper modelEnumItemMapper;

    @Override
    public List<ModelEnumItemDO> listEnumItemByCode(String enumCode) {
        return modelEnumItemMapper.selectList(new QueryWrapper<ModelEnumItemDO>()
                .lambda().eq(ModelEnumItemDO::getEnumCode, enumCode)
                .orderByAsc(ModelEnumItemDO::getEnumSort));
    }

    @Override
    public List<ModelEnumItemBO> listAllEnumItem() {
        return modelEnumItemMapper.listAllEnumItem();
    }

    @Override
    public ModelEnumItemDO getEnumItemById(String rId) {
        return modelEnumItemMapper.selectById(rId);
    }

    @Override
    public MethodResponse insertEnumItem(ModelEnumItemDO modelEnumItemDO) {
        try {
            modelEnumItemMapper.insert(modelEnumItemDO);
            return MethodResponse.success();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(INSERT_FAIL.formatEntity(ModelEnumItemDO.class, e).getErrorMsg(), e);
            return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(ModelEnumItemDO.class, e));
        }
    }

    @Override
    public MethodResponse updateEnumItemById(ModelEnumItemDO modelEnumItemDO) {
        try {
            modelEnumItemMapper.updateById(modelEnumItemDO);
            return MethodResponse.success();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(UPDATE_FAIL.formatEntity(ModelEnumItemDO.class, e).getErrorMsg(), e);
            return MethodResponse.transWithCodeMsgEnum(UPDATE_FAIL.formatEntity(ModelEnumItemDO.class, e));
        }
    }

    @Override
    public MethodResponse deleteEnumItemById(String rId) {
        try {
            modelEnumItemMapper.deleteById(rId);
            return MethodResponse.success();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(DELETE_FAIL.formatEntity(ModelEnumItemDO.class, e).getErrorMsg(), e);
            return MethodResponse.transWithCodeMsgEnum(DELETE_FAIL.formatEntity(ModelEnumItemDO.class, e));
        }
    }

    @Override
    public MethodResponse deleteEnumItemByCode(String enumCode) {
        try {
            modelEnumItemMapper.delete(new QueryWrapper<ModelEnumItemDO>()
                    .lambda()
                    .eq(ModelEnumItemDO::getEnumCode, enumCode));
            return MethodResponse.success();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(BATCH_DELETE_FAIL.formatEntity(ModelEnumItemDO.class, e).getErrorMsg(), e);
            return MethodResponse.transWithCodeMsgEnum(BATCH_DELETE_FAIL.formatEntity(ModelEnumItemDO.class, e));
        }
    }

    @Override
    public MethodResponse batchSaveGroupList(ModelEnumGroupVO modelEnumGroupVO) {
        try {
            QueryWrapper<ModelEnumItemDO> query = new QueryWrapper<ModelEnumItemDO>();
            query.lambda().eq(ModelEnumItemDO::getEnumCode, modelEnumGroupVO.getEnumCode());
            List<ModelEnumItemVO> modelEnumItemVOList = modelEnumGroupVO.getModelEnumItemDOList();
            List<ModelEnumItemDO> modelEnumItemDOList =modelEnumItemVOList.stream().map(i->modelMapper.map(i,ModelEnumItemDO.class)).collect(Collectors.toList());
            List<String> rIdList = modelEnumItemDOList.stream().filter(item -> item.getRId() != null).map(i -> i.getRId()).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(rIdList)){
                query.lambda().notIn(ModelEnumItemDO::getRId, rIdList);
            }
            modelEnumItemMapper.delete(query);
            modelEnumItemDOList.stream().forEach(item -> {
                if (item.getRId() == null) {
                    this.insertEnumItem(item);
                }
                else {
                    this.updateEnumItemById(item);
                }
            });
            return MethodResponse.success(this.listEnumItemByCode(modelEnumGroupVO.getEnumCode()));
        } catch (Exception e) {
            e.printStackTrace();
            log.error(MODEL_CONSTANT_ENUM_GROUP_BATCH_SAVE_FAIL.format(e).getErrorMsg(), e);
            return MethodResponse.transWithCodeMsgFormatEnum(MODEL_CONSTANT_ENUM_GROUP_BATCH_SAVE_FAIL, e);
        }
    }

}

