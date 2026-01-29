package com.joyin.fyzg.wyy.service.rbac.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.wyy.entity.financier.FinancierDO;
import com.joyin.fyzg.wyy.entity.rbac.RoleDO;
import com.joyin.fyzg.wyy.mapper.rbac.RoleMapper;
import com.joyin.fyzg.wyy.service.rbac.RoleService;
import com.joyin.fyzg.utils.DynamicSqlExecutorUtils;
import com.joyin.fyzg.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.joyin.fyzg.enums.BaseExceptionEnum.*;

@Service
@Slf4j
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleMapper roleMapper;
	@Autowired
	DynamicSqlExecutorUtils dynamicSqlExecutorUtils;

	@Override
	public MethodResponse insertRole(RoleDO roleDO) {
		try {
			if (StringUtils.isEmpty(roleDO.getJyInsertTime())){
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				String formattedDateTime = now.format(formatter);
				roleDO.setJyInsertTime(formattedDateTime);
			}
			roleMapper.insert(roleDO);
			return MethodResponse.success(roleDO.getRId());
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(INSERT_FAIL.formatEntity(RoleDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(RoleDO.class, e));
		}
	}

	@Override
	public MethodResponse updateRoleById(RoleDO roleDO) {
		try {
			if (StringUtils.isEmpty(roleDO.getJyUpdateTime())){
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				String formattedDateTime = now.format(formatter);
				roleDO.setJyUpdateTime(formattedDateTime);
			}
			roleMapper.updateById(roleDO);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(UPDATE_FAIL.formatEntity(RoleDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(UPDATE_FAIL.formatEntity(RoleDO.class, e));
		}
	}

	@Override
	public MethodResponse deleteRoleById(Long rId) {
		try {
			roleMapper.deleteById(rId);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(DELETE_FAIL.formatEntity(RoleDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(DELETE_FAIL.formatEntity(RoleDO.class, e));
		}
	}

	@Override
	public RoleDO getRoleById(Long rId) {
		return roleMapper.selectById(rId);
	}

	@Override
	public List<RoleDO> listRoleByCodeList(List<String> codeList) {
		QueryWrapper<RoleDO> query = new QueryWrapper<RoleDO>();
		if (CollectionUtils.isNotEmpty(codeList)) {
			query.lambda().in(RoleDO::getRoleOCode, codeList);
		}
		return roleMapper.selectList(query);
	}

	@Override
	public List<RoleDO> listAll() {
		return roleMapper.selectList();
	}

	@Override
	public Map<String, Object> listRolesByCondition(String roleOCode, String roleOName, PageWrapper pageWrapper) {
		Page<RoleDO> page = new Page<>(pageWrapper.getCurrentPage(),pageWrapper.getPageSize());
		List<Map<String, Object>> resultList = Lists.newArrayList();
		Map<String, Object> resultMap = new HashMap<>();
		IPage<RoleDO> resultPage = null;
		if (StringUtils.isEmpty(roleOCode) && StringUtils.isEmpty(roleOName)) {
			resultPage = roleMapper.selectPage(page,new QueryWrapper<>());
		}
		else {
			LambdaQueryWrapper<RoleDO> queryWrapper = new QueryWrapper<RoleDO>().lambda();
			if (StringUtils.isNotEmpty(roleOCode)) {
				queryWrapper.like(RoleDO::getRoleOCode, roleOCode);
			}
			if (StringUtils.isNotEmpty(roleOName)) {
				queryWrapper.like(RoleDO::getRoleOName, roleOName);
			}
			resultPage = roleMapper.selectPage(page,queryWrapper);
		}
		if (resultPage == null){
			return  resultMap;
		}

		resultPage.getRecords().forEach(role->{
			String roleJson = JsonUtils.obj2json(role);
			Map<String, Object> roleMap = JsonUtils.json2map(roleJson);
//			Map<String, Object> roleMap = BeanUtils.beanToMap(role);
//			String objrAu = role.getObjrAu();
//			String objrAuName = "";
//			if (StringUtils.isNotEmpty(objrAu) && !objrAu.equals("[]")){
//				objrAu = objrAu.replace("[","").replace("]","")
//				.replace("\"","'");
//				List<Map> stationData = dynamicSqlExecutorUtils.findListData("select station_name,r_id,station_code from SYS_OBJR_STATION where station_code in (" + objrAu + ") order by r_id", null);
//				objrAuName = stationData.stream()
//						.map(map -> map.get("STATION_NAME"))
//						.filter(Objects::nonNull)
//						.map(Object::toString)
//						.collect(Collectors.joining(","));
//
//			}
//			roleMap.put("objrAuName",objrAuName);
			resultList.add(roleMap);

		});

		resultMap.put("records",resultList);
		resultMap.put("current",resultPage.getCurrent());
		resultMap.put("size",resultPage.getSize());
		resultMap.put("totalPages",resultPage.getPages());
		resultMap.put("total",resultPage.getTotal());

		return resultMap;
	}

	@Override
	public List<Map> getJobPermissions() {
		List<Map> listData = dynamicSqlExecutorUtils.findListData("select a.station_code as enum_value,\n"
				+ "       a.station_name as enum_label\n"
				+ "from SYS_OBJR_STATION a\n"
				+ "where a.objr_o_code='PRD'\n"
				+ "      and a.station_name is not null", null);
		return listData;
	}

	@Override
	public List<Map> getDataPermissions() {
		return dynamicSqlExecutorUtils.findListData("select ENUM_LABEL,ENUM_VALUE from SYS_ML_MODEL_OPTION  where COL_CODE= 'DATA_AU' and TAB_CODE = 'M082001'",null);
	}
}
