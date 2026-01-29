package com.joyin.fyzg.wyy.service.rbac.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.joyin.fyzg.common.FeignResponse;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.client.ComponentClientService;
import com.joyin.fyzg.wyy.entity.rbac.ComponentDO;
import com.joyin.fyzg.wyy.mapper.rbac.ComponentMapper;
import com.joyin.fyzg.wyy.service.rbac.ComponentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.joyin.fyzg.enums.BaseExceptionEnum.*;

@Service
@Slf4j
public class ComponentServiceImpl implements ComponentService {

	@Autowired
	ComponentMapper componentMapper;

	@Autowired
	ComponentClientService componentClientService;

//	@Autowired
//	ConfigService configService;

	@Override
	public MethodResponse insertComponent(ComponentDO componentDO) {
		try {
			componentMapper.insert(componentDO);
			return MethodResponse.success(componentDO.getCode());
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(INSERT_FAIL.formatEntity(ComponentDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(ComponentDO.class, e));
		}
	}

	@Override
	public MethodResponse<Integer> updateComponentById(ComponentDO componentDO) {
		try {
			return MethodResponse.success(componentMapper.updateById(componentDO));
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(UPDATE_FAIL.formatEntity(ComponentDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(UPDATE_FAIL.formatEntity(ComponentDO.class, e));
		}
	}

	@Override
	public MethodResponse deleteComponentById(String rId) {
		try {
			componentMapper.deleteById(rId);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(DELETE_FAIL.formatEntity(ComponentDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(DELETE_FAIL.formatEntity(ComponentDO.class, e));
		}
	}

	@Override
	public ComponentDO getComponentById(String rId) {
		return componentMapper.selectById(rId);
	}

	@Override
	public MethodResponse saveComponent(ComponentDO componentDO) {
		LambdaUpdateWrapper<ComponentDO> updateWrapper = new UpdateWrapper<ComponentDO>()
				.lambda()
				.eq(ComponentDO::getCode, componentDO.getCode());
		if (null != componentDO.getPattern()) {
			updateWrapper.eq(ComponentDO::getPattern, componentDO.getPattern());
		}
		if (componentMapper.update(componentDO, updateWrapper) == 0) {
			this.insertComponent(componentDO);
		}
//		configService.brushDesktopCfgJson(componentDO);
		return MethodResponse.success();
	}

	@Override
	public MethodResponse clearComponent() {
//		componentMapper.clearComponent();
		return MethodResponse.success();
	}

	@Override
	public List<ComponentDO> listAll() {
		List<ComponentDO> componentDOS;
		componentDOS = componentMapper.selectList();
		//增加低码平台配置kanban的预设尺寸数据
		FeignResponse<List<ComponentDO>> listFeignResponse = componentClientService.listAll();
		List<ComponentDO> componentDOSKanban = listFeignResponse.getResult().stream().filter(
				componentDO -> componentDO.getCode().startsWith("kanban")
		).collect(Collectors.toList());
		componentDOS.addAll(componentDOSKanban);
		return componentDOS;
	}

	@Override
	public MethodResponse deleteComponentByCodeAndPattern(String code, String pattern) {
		try {
			QueryWrapper<ComponentDO> queryWrapper = new QueryWrapper();
			queryWrapper.lambda().eq(ComponentDO::getCode, code)
							.eq(ComponentDO::getPattern, pattern);
			componentMapper.delete(queryWrapper);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(DELETE_FAIL.formatEntity(ComponentDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(DELETE_FAIL.formatEntity(ComponentDO.class, e));
		}
	}
}
