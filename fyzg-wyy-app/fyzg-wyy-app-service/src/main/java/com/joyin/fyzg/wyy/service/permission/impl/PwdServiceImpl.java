package com.joyin.fyzg.wyy.service.permission.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.config.user.UserProperties;
import com.joyin.fyzg.utils.DateUtil8;
import com.joyin.fyzg.wyy.entity.rbac.AccountDO;
import com.joyin.fyzg.wyy.entity.rbac.UserDO;
import com.joyin.fyzg.wyy.mapper.rbac.AccountMapper;
import com.joyin.fyzg.wyy.mapper.rbac.UserMapper;
import com.joyin.fyzg.wyy.service.permission.PwdService;
import com.joyin.fyzg.wyy.service.rbac.AccountService;
import com.joyin.fyzg.wyy.service.rbac.ShineSyncService;
import com.joyin.fyzg.wyy.service.rbac.UserService;
import com.shine.eusp.iopara.EUSPOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

import static com.joyin.fyzg.enums.BaseExceptionEnum.UPDATE_FAIL;

@Service
@Slf4j
@Transactional
public class PwdServiceImpl implements PwdService {
	@Value("${user.password}")
	public String password;

	@Autowired
	private UserProperties userProperties ;

	@Autowired
	UserMapper userMapper;

	@Autowired
	AccountMapper accountMapper;

	@Autowired
	AccountService accountService;

	@Autowired
	UserService userService;

	@Autowired
	ShineSyncService shineSyncService;

	@Override
	public MethodResponse updateUser4ResetPwd(Long rId) {
		try {
			UserDO userDO = userService.getUserById(rId);
			//同步新意
			if (userDO != null) {
				EUSPOutput euspOutput = shineSyncService.updateUser4ResetPwd(userDO.getUserOCode());
				log.info("用户重置密码新意返回RetCode={}",euspOutput.getRetCode());
				if (euspOutput.getRetCode() == 0){

					AccountDO entity = AccountDO.builder()
							.password(password)
							.build();

					setPwdExpiratioField(entity);
					LambdaUpdateWrapper<AccountDO> updateWrapper = new UpdateWrapper<AccountDO>()
							.lambda()
							.eq(AccountDO::getAccount, userDO.getAccount());
					accountMapper.update(entity, updateWrapper);
				}
			}

			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(UPDATE_FAIL.formatEntity(AccountDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(UPDATE_FAIL.formatEntity(AccountDO.class, e));
		}
	}

	/***
	 * 设置密码刷新时间和失效时间
	 * <br/>
	 * @param entity
	 * @return void
	 * @author jinyuan.lin
	 * @date 2023/10/23 17:17
	 */
	public void setPwdExpiratioField(AccountDO entity){
		Integer expirationMonth = userProperties.getExpirationMonth();
		int index = expirationMonth != null ? expirationMonth : 1 ;
		entity.setPwFlushDate(DateUtil8.getNowDate_EN());
		entity.setValidDate(DateUtil8.getAfterOrPreMonthDate(index));
	}

	@Override
	public MethodResponse updateUser4Pwd(String oldPwd, String pwd, String account, String userCode, boolean firstModifyPwd) {
		try {
			BCryptPasswordEncoder bcp = new BCryptPasswordEncoder();
			AccountDO accountDO = accountService.getAccountByAccount(account);
			boolean matches = bcp.matches(oldPwd, accountDO.getPassword());
			if (!matches) {
				return MethodResponse.error("密码输入不正确！");
			}
			AccountDO entity = AccountDO.builder()
					.password(bcp.encode(pwd))
					.build();
			setPwdExpiratioField(entity);
			if(firstModifyPwd){
				entity.setLoginTime(DateUtil8.getNowTime_EN());
				UserDO userDO = userService.getUserByUserCode(userCode);
				userDO.setLoginTime(DateUtil8.getNowTime_EN());
				LambdaUpdateWrapper<UserDO> updateUserWrapper = new UpdateWrapper<UserDO>()
						.lambda()
						.eq(UserDO::getUserOCode, userCode);
				userMapper.update(userDO, updateUserWrapper);
			}
			LambdaUpdateWrapper<AccountDO> updateWrapper = new UpdateWrapper<AccountDO>()
					.lambda()
					.eq(AccountDO::getAccount, account);
			accountMapper.update(entity, updateWrapper);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(UPDATE_FAIL.formatEntity(AccountDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(UPDATE_FAIL.formatEntity(AccountDO.class, e));
		}
	}

	/***
	 * 密码过期时间
	 * <br/>
	 * @param loginUserCode
	 * @return java.lang.String
	 * @author jinyuan.lin
	 * @date 2023/10/24 15:34
	 */
	@Override
	public String getPwdValidDate(String loginUserCode) {

		QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(UserDO::getUserOCode, loginUserCode);
		UserDO userDO = userMapper.selectOne(queryWrapper);
		AccountDO accountDO = accountService.getAccountByAccount(userDO.getAccount());
		if(accountDO.getValidDate() == null){
			return null ;
		}
		long betWeenDays = DateUtil8.getBetWeenDays(DateUtil8.getNowDate_EN(), accountDO.getValidDate());
		if(betWeenDays < 0){
			return null ;
		}
		if(betWeenDays >= userProperties.getExpirationRemindDays()){
			return null ;
		}
		if(betWeenDays == 0){
			return "用户密码即将过期，请及时修改密码。" ;
		}
		return MessageFormat.format("用户密码还有{0}天过期，请及时修改密码", betWeenDays);
	}

	private String buildPwdDefault() {
		BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
		return bcryptPasswordEncoder.encode(userProperties.getPwdDefault());
	}



}

