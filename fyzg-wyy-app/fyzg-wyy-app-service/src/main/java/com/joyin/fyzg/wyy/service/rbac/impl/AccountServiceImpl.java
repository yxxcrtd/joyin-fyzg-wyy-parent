package com.joyin.fyzg.wyy.service.rbac.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.entity.rbac.AccountDO;
import com.joyin.fyzg.wyy.mapper.rbac.AccountMapper;
import com.joyin.fyzg.wyy.service.rbac.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.joyin.fyzg.enums.BaseExceptionEnum.INSERT_FAIL;
import static com.joyin.fyzg.enums.BaseExceptionEnum.UPDATE_FAIL;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountMapper accountMapper;

	@Override
	public MethodResponse insertAccount(AccountDO accountDO) {
		try {
			accountMapper.insert(accountDO);
			return MethodResponse.success(accountDO.getRId());
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(INSERT_FAIL.formatEntity(AccountDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(AccountDO.class, e));
		}
	}

	@Override
	public MethodResponse updateAccountById(AccountDO accountDO) {
		try {
			accountMapper.updateById(accountDO);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(UPDATE_FAIL.formatEntity(AccountDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(UPDATE_FAIL.formatEntity(AccountDO.class, e));
		}
	}

	@Override
	public AccountDO getAccountByAccount(String account) {
		QueryWrapper<AccountDO> query = new QueryWrapper<>();
		query.lambda().eq(AccountDO::getAccount, account);
		return accountMapper.selectOne(query);
	}

}
