package com.joyin.fyzg.wyy.service.rbac;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.entity.rbac.AccountDO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface AccountService {

    MethodResponse insertAccount(AccountDO accountDO);

    MethodResponse updateAccountById(AccountDO accountDO);

	AccountDO getAccountByAccount(String account);

}
