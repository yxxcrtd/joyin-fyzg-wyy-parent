package com.joyin.fyzg.wyy.service.financier.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.utils.DateUtil8;
import com.joyin.fyzg.wyy.entity.financier.FinancierDO;
import com.joyin.fyzg.wyy.entity.financier.FinancierMpDO;
import com.joyin.fyzg.wyy.mapper.financier.FinancierDfMapper;
import com.joyin.fyzg.wyy.mapper.financier.FinancierMapper;
import com.joyin.fyzg.wyy.service.common.CommonService;
import com.joyin.fyzg.wyy.service.financier.FinancierMpService;
import com.joyin.fyzg.wyy.service.financier.FinancierService;
import com.joyin.fyzg.wyy.vo.financier.FinancierMpVO;
import com.joyin.fyzg.wyy.vo.financier.FinancierVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.joyin.fyzg.enums.BaseExceptionEnum.*;

@Service
@Slf4j
public class FinancierServiceImpl implements FinancierService {

	@Autowired
	ModelMapper modelMapper;
	@Autowired
	FinancierMapper financierMapper;
	@Autowired
	FinancierDfMapper financierDfMapper;
	@Autowired
	FinancierMpService financierMpService;
	@Autowired
	CommonService commonService;

	@Override
	public MethodResponse insertFinancier(FinancierVO financierVO) {
		try {
			FinancierDO financierDO = modelMapper.map(financierVO, FinancierDO.class);
			financierDO.setJyInsertTime(DateUtil8.getNowTime_EN());
			financierDO.setCustOCode(commonService.getOCodeNextVal());
			financierMapper.insert(financierDO);
			List<FinancierMpDO> financierMpList = this.buildFinancierMpDOList(financierVO.getFinancierMpList(), financierDO.getCustOCode());
			financierMpService.batchInsertFinancierMp(financierMpList);
			return MethodResponse.success(financierVO.getRId());
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(INSERT_FAIL.formatEntity(FinancierDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(FinancierDO.class, e));
		}
	}

	private List<FinancierMpDO> buildFinancierMpDOList(List<FinancierMpVO> financierMpList, String custOCode) {
		List<FinancierMpDO> list = Lists.newArrayList();
		Optional.ofNullable(financierMpList).ifPresent(i -> i.forEach(component -> {
			FinancierMpDO financierMp = modelMapper.map(component, FinancierMpDO.class);
			financierMp.setCustOCode(custOCode);
			list.add(financierMp);
		}));
		return list;
	}

	@Override
	public MethodResponse updateFinancierById(FinancierVO financierVO) {
		try {
			FinancierDO financierDO = modelMapper.map(financierVO, FinancierDO.class);
			financierDO.setJyUpdateTime(DateUtil8.getNowTime_EN());
			financierMapper.updateById(financierDO);
			List<FinancierMpDO> financierMpList = this.buildFinancierMpDOList(financierVO.getFinancierMpList(), financierDO.getCustOCode());
			financierMpService.batchSaveFinancierMp(financierMpList, financierVO.getCustOCode());
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(UPDATE_FAIL.formatEntity(FinancierDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(UPDATE_FAIL.formatEntity(FinancierDO.class, e));
		}
	}

	@Override
	public MethodResponse deleteFinancierById(String rId) {
		try {
			financierMapper.deleteById(rId);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(DELETE_FAIL.formatEntity(FinancierDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(DELETE_FAIL.formatEntity(FinancierDO.class, e));
		}
	}

	@Override
	public MethodResponse deleteFinancierByCustOCode(String custOCode) {
		try {
			financierMapper.delete(new QueryWrapper<FinancierDO>()
					.lambda()
					.in(FinancierDO::getCustOCode, custOCode));
			financierMpService.deleteFinancierMpByCustOCode(custOCode);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(DELETE_FAIL.formatEntity(FinancierDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(DELETE_FAIL.formatEntity(FinancierDO.class, e));
		}
	}

	@Override
	public FinancierDO getFinancierById(String rId) {
		return financierMapper.selectById(rId);
	}


	@Override
	public FinancierDO getFinancierByCustOCode(String custOCode) {
		QueryWrapper<FinancierDO> query = new QueryWrapper<FinancierDO>();
		query.lambda().eq(FinancierDO::getCustOCode, custOCode);
		return financierMapper.selectOne(query);
	}

	@Override
	public List<FinancierDO> listAll() {
		return financierMapper.selectList();
	}

	@Override
	public List<FinancierDO> listFinancierByCustOName(String custOName) {
		Page<FinancierDO> page = new Page<>(1, 10);
		IPage<FinancierDO> iPage = null;
		if (StringUtils.isNotEmpty(custOName)) {
			QueryWrapper<FinancierDO> query = new QueryWrapper<FinancierDO>();
			query.lambda().like(FinancierDO::getCustOName, custOName);
			iPage = financierMapper.selectPage(page, query);
		}else {
			iPage = financierMapper.selectPage(page, null);
		}
		return iPage.getRecords();
	}

	@Override
	public List<FinancierDO> listFinancierByCodeList(List<String> codeList) {
		QueryWrapper<FinancierDO> query = new QueryWrapper<FinancierDO>();
		if (CollectionUtils.isNotEmpty(codeList)) {
			query.lambda().in(FinancierDO::getCustOCode, codeList);
			return financierMapper.selectList(query);
		}else {
			return Lists.newArrayList();
		}
	}

	@Override
	public List<FinancierDO> listFinancierByCustOCode(String custOCode) {
		if (StringUtils.isNotEmpty(custOCode)) {
			return financierDfMapper.listFinancierByCustOCode(custOCode);
		}else {
			return Lists.newArrayList();
		}
	}

	@Override
	public PageResponse<FinancierDO> listFinanciersByCondition(String custOName, PageWrapper pageWrapper) {
		Page<FinancierDO> page = new Page<>(pageWrapper.getCurrentPage(),pageWrapper.getPageSize());
		LambdaQueryWrapper<FinancierDO> queryWrapper = new QueryWrapper<FinancierDO>().lambda();

		if (StringUtils.isNotEmpty(custOName)){
			queryWrapper.like(FinancierDO::getCustOName, custOName);
		}
		// 执行分页查询
		IPage<FinancierDO> resultPage = financierMapper.selectPage(page, queryWrapper);

		PageResponse<FinancierDO> pageResponse = new PageResponse<>(resultPage);
		return pageResponse;
	}

}
