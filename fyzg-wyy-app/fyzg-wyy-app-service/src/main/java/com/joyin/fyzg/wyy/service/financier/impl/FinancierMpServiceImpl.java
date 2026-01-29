package com.joyin.fyzg.wyy.service.financier.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.config.exception.ChainExceptionUtils;
import com.joyin.fyzg.wyy.entity.financier.FinancierMpBO;
import com.joyin.fyzg.wyy.entity.financier.FinancierMpDO;
import com.joyin.fyzg.wyy.mapper.financier.FinancierDfMapper;
import com.joyin.fyzg.wyy.mapper.financier.FinancierMpMapper;
import com.joyin.fyzg.wyy.service.financier.FinancierMpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.joyin.fyzg.enums.BaseExceptionEnum.*;

@Service
@Slf4j
public class FinancierMpServiceImpl implements FinancierMpService {

	@Autowired
	FinancierMpMapper financierMpMapper;
	@Autowired
	FinancierDfMapper financierDfMapper;

	@Override
	public MethodResponse insertFinancierMp(FinancierMpDO financierMpDO) {
		try {
			financierMpMapper.insert(financierMpDO);
			return MethodResponse.success(financierMpDO.getRId());
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(INSERT_FAIL.formatEntity(FinancierMpDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(FinancierMpDO.class, e));
		}
	}

	@Override
	public MethodResponse batchInsertFinancierMp(List<FinancierMpDO> financierMpDOList) {
		try {
			financierMpDOList.forEach(financierMpMapper::insert);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(BATCH_INSERT_FAIL.formatEntity(FinancierMpDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(BATCH_INSERT_FAIL.format(e.getMessage()), e);
		}
	}

	@Override
	public MethodResponse updateFinancierMpById(FinancierMpDO financierMpDO) {
		try {
			financierMpMapper.updateById(financierMpDO);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(UPDATE_FAIL.formatEntity(FinancierMpDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(UPDATE_FAIL.formatEntity(FinancierMpDO.class, e));
		}
	}

	private MethodResponse batchUpdateFinancierMp(List<FinancierMpDO> financierMpDOList) {
		try {
			financierMpDOList.forEach(financierMpMapper::updateById);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(BATCH_UPDATE_FAIL.formatEntity(FinancierMpDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(BATCH_UPDATE_FAIL.format(e.getMessage()), e);
		}
	}

	private MethodResponse batchDeleteFinancierMp(List<Long> idList) {
		try {
			if (CollectionUtils.isNotEmpty(idList)){
				financierMpMapper.delete(new QueryWrapper<FinancierMpDO>()
						.lambda()
						.in(FinancierMpDO::getRId, idList));
			}
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(BATCH_DELETE_FAIL.formatEntity(FinancierMpDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(BATCH_DELETE_FAIL.format(e.getMessage()), e);
		}
	}

	@Override
	public MethodResponse deleteFinancierMpByCustOCode(String custOCode) {
		try {
			financierMpMapper.delete(new QueryWrapper<FinancierMpDO>()
					.lambda()
					.eq(FinancierMpDO::getCustOCode, custOCode));
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(BATCH_DELETE_FAIL.formatEntity(FinancierMpDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(BATCH_DELETE_FAIL.formatEntity(FinancierMpDO.class, e), null);
		}
	}

	@Override
	public MethodResponse deleteFinancierMpById(String rId) {
		try {
			financierMpMapper.deleteById(rId);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(DELETE_FAIL.formatEntity(FinancierMpDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(DELETE_FAIL.formatEntity(FinancierMpDO.class, e));
		}
	}

	@Override
	public FinancierMpDO getFinancierMpById(String rId) {
		return financierMpMapper.selectById(rId);
	}

	@Override
	public List<FinancierMpDO> listAll() {
		return financierMpMapper.selectList();
	}

	@Override
	public List<FinancierMpDO> listFinancierMpByCustOCode(String custOCode) {
		QueryWrapper<FinancierMpDO> query = new QueryWrapper<FinancierMpDO>();
		query.lambda().eq(FinancierMpDO::getCustOCode, custOCode);
		return financierMpMapper.selectList(query);
	}

	@Override
	public List<FinancierMpBO> listFinancierMpBOByCustOCode(String custOCode) {
		if (StringUtils.isNotEmpty(custOCode)) {
			return financierDfMapper.listFinancierMpBOByCustOCode(custOCode);
		}else {
			return Lists.newArrayList();
		}
	}

	@Override
	public MethodResponse batchSaveFinancierMp(List<FinancierMpDO> financierMpDOList, String custOCode) {
		try {
			List<FinancierMpDO> insertList = financierMpDOList.stream().filter(i -> i.getRId() == null).collect(Collectors.toList());
			List<FinancierMpDO> updateList = financierMpDOList.stream().filter(i -> i.getRId() != null).collect(Collectors.toList());
			List<Long> existsList = updateList.stream().map(i -> i.getRId()).collect(Collectors.toList());
			List<FinancierMpDO> financierMpDOList1 = this.listFinancierMpByCustOCode(custOCode);
			List<Long> deleteList = financierMpDOList1.stream().filter(i -> !existsList.contains(i.getRId())).map(i -> i.getRId()).collect(Collectors.toList());

			this.batchInsertFinancierMp(insertList);
			this.batchUpdateFinancierMp(updateList);
			this.batchDeleteFinancierMp(deleteList);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(BATCH_SAVE_FAIL.formatEntity(FinancierMpDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(BATCH_SAVE_FAIL.formatEntity(FinancierMpDO.class, e), null);
		}
	}
}
