package com.joyin.fyzg.wyy.service.rbac.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.common.RequestParamMap;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.utils.BeanUtils;
import com.joyin.fyzg.utils.DateUtil8;
import com.joyin.fyzg.utils.DynamicSqlExecutorUtils;
import com.joyin.fyzg.utils.JsonUtils;
import com.joyin.fyzg.wyy.common.enums.RbacUserEnable;
import com.joyin.fyzg.wyy.config.SessionUserConfig;
import com.joyin.fyzg.wyy.entity.financier.FinancierDO;
import com.joyin.fyzg.wyy.entity.rbac.AccountDO;
import com.joyin.fyzg.wyy.entity.rbac.UserDO;
import com.joyin.fyzg.wyy.entity.rbac.UserExtDO;
import com.joyin.fyzg.wyy.mapper.financier.FinancierMapper;
import com.joyin.fyzg.wyy.mapper.rbac.UserMapper;
import com.joyin.fyzg.wyy.service.common.CommonService;
import com.joyin.fyzg.wyy.service.rbac.AccountService;
import com.joyin.fyzg.wyy.service.rbac.ShineSyncService;
import com.joyin.fyzg.wyy.service.rbac.UserExtService;
import com.joyin.fyzg.wyy.service.rbac.UserService;
import com.joyin.fyzg.wyy.service.user.UserApplyService;
import com.joyin.fyzg.wyy.vo.rbac.UserVO;
import com.shine.eusp.iopara.EUSPOutput;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.joyin.fyzg.enums.BaseExceptionEnum.*;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;
	@Autowired
	UserApplyService userApplyService;
	@Autowired
	AccountService accountService;
	@Autowired
	UserExtService userExtService;
	@Autowired
	CommonService commonService;
	@Autowired
	private SessionUserConfig userConfig;
	@Autowired
	private DynamicSqlExecutorUtils dynamicSqlExecutorUtils;
	//默认密码
//	@Value("${user.pwdDefault}")
//	private String pwdDefault;

	@Autowired
	FinancierMapper financierMapper;

	private final String DESKTOP_PREFIX = "DESKTOP_";

	@Value("${user.password}")
	public String password;

	@Value("${user.password}")
	public String userDefaultPassword;

	@Autowired
	ShineSyncService shineSyncService;

	@Value("${user.password}")
	public String USERDEFAULTPASSWORD;

	@Override
	public MethodResponse insertUser(UserDO userDO) {
		try {
			if (StringUtils.equals(userDO.getUserType(),"0")) {
				//同一个管理人只有一个管理员
				QueryWrapper<UserDO> query = new QueryWrapper<UserDO>();
				query.lambda().eq(UserDO::getUserManager, userDO.getUserManager());
				query.lambda().eq(UserDO::getUserType, "0");
				List<Map<String, Object>> maps = userMapper.selectMaps(query);
				if (CollectionUtils.isNotEmpty(maps)) {
					throw new IllegalArgumentException("用户类型选择错误，当前管理人已经存在管理员！");
				}
			}
			userDO.setEnabled("1");
			userDO.setUserOCode(commonService.getOCodeNextVal());
			userDO.setJyInsertTime(DateUtil8.getNowTime_EN());
			userDO.setAccount(userDO.getMobile());
			if(StringUtils.isBlank(userDO.getUserOName())){
				userDO.setUserOName(userDO.getAccount());
			}
			//todo 同时新增调用新意接口新增用户
			EUSPOutput euspOutput = shineSyncService.insertUser(userDO);
			log.info("新增用户新意返回USER_ID={}",euspOutput.getRetValue());
			if (euspOutput.getRetCode() == 0) {
				userDO.setUserOCode(euspOutput.getRetValue());
				userMapper.insert(userDO);
				AccountDO accountByAccount = accountService.getAccountByAccount(userDO.getMobile());
				if (accountByAccount == null) {
					BCryptPasswordEncoder bcp = new BCryptPasswordEncoder();
					//TODO 临时密码设置为111111
					AccountDO accountDO = AccountDO.builder()
							.password(bcp.encode(StringUtils.defaultIfBlank(userDefaultPassword, "111111")))
							.build();
					accountDO.setAccount(userDO.getMobile());
					accountDO.setEnabled("1");
					accountService.insertAccount(accountDO);
				}
			}
			return MethodResponse.success(userDO.getRId());
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(INSERT_FAIL.formatEntity(UserDO.class, e).getErrorMsg(), e);
			throw new RuntimeException(e);
			//return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(UserDO.class, e));
		}
	}

	@Override
	public MethodResponse updateUserById(UserDO userDO) {
		try {
			/*if (StringUtils.equals(userDO.getUserType(),"0")) {
				//修改为管理员，同一个管理人只有一个管理员
				QueryWrapper<UserDO> query = new QueryWrapper<UserDO>();
				query.lambda().eq(UserDO::getFinancier, userDO.getFinancier());
				query.lambda().eq(UserDO::getUserType, "0");
				List<UserDO> userDOList = userMapper.selectList(query);
				if (CollectionUtils.isNotEmpty(userDOList)) {
					throw new RuntimeException("当前管理人已经存在管理员,不允许修改！");
				}
			}*/
			QueryWrapper<UserDO> query = new QueryWrapper<UserDO>();
			query.lambda().eq(UserDO::getMobile, userDO.getMobile());
			query.lambda().eq(UserDO::getEnabled, "1");
			List<UserDO> userDOList = userMapper.selectList(query);
			userDO.setJyUpdateTime(DateUtil8.getNowTime_EN());
			EUSPOutput euspOutput = shineSyncService.updateUserById(userDO);
			log.info("修改用户新意返回RetCode={}",euspOutput.getRetCode());
			if (euspOutput.getRetCode() == 0) {
				userMapper.updateById(userDO);
				if (CollectionUtils.isNotEmpty(userDOList) && userDOList.size() == 1 || StringUtils.equals(userDO.getEnabled(), "1")) {
					AccountDO accountByAccount = accountService.getAccountByAccount(userDO.getMobile());
					if (accountByAccount != null) {
						accountByAccount.setEnabled(userDO.getEnabled());
						accountService.updateAccountById(accountByAccount);
					}
				}
			}
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(UPDATE_FAIL.formatEntity(UserDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(UPDATE_FAIL.formatEntity(UserDO.class, e));
		}
	}

	@Override
	public MethodResponse deleteUserById(Long rId) {
		try {
			//todo 增加调用请求新意接口的更新情况
			UserDO userDO = getUserById(rId);
			if (userDO != null) {
				EUSPOutput euspOutput = shineSyncService.deleteUserById(userDO.getUserOCode());
				log.info("删除用户新意返回RetCode={}",euspOutput.getRetCode());
				if (euspOutput.getRetCode() == 0) {
					userMapper.deleteById(rId);
				}
			}
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(DELETE_FAIL.formatEntity(UserDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(DELETE_FAIL.formatEntity(UserDO.class, e));
		}
	}

	@Override
	public MethodResponse setUserCollect(UserDO userDO) {
		try {
			UserDO entity = UserDO.builder()
					.collectCfgJson(userDO.getCollectCfgJson())
					.build();
			LambdaUpdateWrapper<UserDO> updateWrapper = new UpdateWrapper<UserDO>()
					.lambda()
					.eq(UserDO::getUserOCode, userDO.getUserOCode());
			userMapper.update(entity, updateWrapper);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(UPDATE_FAIL.formatEntity(UserDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(UPDATE_FAIL.formatEntity(UserDO.class, e));
		}
	}

	@Override
	public MethodResponse setUserTheme(UserDO userDO) {
		try {
			UserDO entity = UserDO.builder()
					.themeCfgJson(userDO.getThemeCfgJson())
					.build();
			LambdaUpdateWrapper<UserDO> updateWrapper = new UpdateWrapper<UserDO>()
					.lambda()
					.eq(UserDO::getUserOCode, userDO.getUserOCode());
			userMapper.update(entity, updateWrapper);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(UPDATE_FAIL.formatEntity(UserDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(UPDATE_FAIL.formatEntity(UserDO.class, e));
		}
	}

	@Override
	public String getUserDesktop(String userCode, String productLine) {
		String cfgCode = DESKTOP_PREFIX + productLine;
		UserExtDO userExt = userExtService.getUserExtByCode(userCode, cfgCode);
		if (null!=userExt){
			return userExt.getCfgContent();
		} else {
			return "";
		}
	}

	@Override
	public MethodResponse setUserDesktop(RequestParamMap paramMap) {
		try {
            String userCode = paramMap.getStringValueOfNullable("userCode");
			String cfgCode = DESKTOP_PREFIX + paramMap.getStringValueOfNullable("productLine");
			String cfgContent = paramMap.getStringValueOfNullable("desktopCfgJson");
			UserExtDO userExtDO = UserExtDO.builder()
					.userOCode(userCode)
					.cfgCode(cfgCode)
					.cfgContent(cfgContent)
					.build();
			return userExtService.saveUserExt(userExtDO);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(UPDATE_FAIL.formatEntity(UserDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(UPDATE_FAIL.formatEntity(UserDO.class, e));
		}
	}

	@Override
	public MethodResponse updateUserLoginTime(String userCode) {
		try {
			UserDO entity = UserDO.builder()
					.loginTime( DateUtil8.getNowTime_EN())
					.build();
			LambdaUpdateWrapper<UserDO> updateWrapper = new UpdateWrapper<UserDO>()
					.lambda()
					.eq(UserDO::getUserOCode, userCode);
			userMapper.update(entity, updateWrapper);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(UPDATE_FAIL.formatEntity(UserDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(UPDATE_FAIL.formatEntity(UserDO.class, e));
		}
	}

	@Override
	public UserDO getUserById(Long rId) {
		return userMapper.selectById(rId);
	}

	@Override
	public UserDO getFirstUserByAccount(String account) {
		QueryWrapper<UserDO> query = new QueryWrapper<UserDO>();
		query.lambda().eq(UserDO::getAccount, account);
		List<UserDO> userDOList = userMapper.selectList(query);
		if (CollectionUtils.isEmpty(userDOList)){
			return null;
		}
		return userDOList.stream().sorted((user1, user2) -> {
			// 若loginTime值为空，赋值成2001-01-01 00:00:00再参与比较，按降序排序后取第一个
			String time1 = Optional.ofNullable(user1.getLoginTime()).orElse("2001-01-01 00:00:00");
			String time2 = Optional.ofNullable(user2.getLoginTime()).orElse("2001-01-01 00:00:00");
			return time2.compareTo(time1); // 降序排序
		}).findFirst().get();
	}

	@Override
	public UserDO getUserByUserCode(String userCode) {
		QueryWrapper<UserDO> query = new QueryWrapper<UserDO>();
		query.lambda().eq(UserDO::getUserOCode, userCode);
		return userMapper.selectOne(query);
	}

	@Override
	public String getUserCollectMenuByCode(String code) {
		QueryWrapper<UserDO> query = new QueryWrapper<UserDO>();
		query.lambda().eq(UserDO::getUserOCode, code);
		return userMapper.selectOne(query).getCollectCfgJson();
	}

	@Override
	public List<UserDO> listUserByCodeList(List<String> codeList) {
		QueryWrapper<UserDO> query = new QueryWrapper<UserDO>();
		if (CollectionUtils.isNotEmpty(codeList)) {
			query.lambda().in(UserDO::getUserOCode, codeList);
		}
//        query.lambda().eq(UserDO::getEnabled, RbacUserEnable.ENABLE.getValue());
		return userMapper.selectList(query);
	}

	@Override
	public List<String> listUserCodeByRoleCodeList(List<String> roleCodeList) {
		return userMapper.listUserCodeByRoleCodeList(roleCodeList);
	}

	@Override
	public List<UserDO> listAll() {
        QueryWrapper<UserDO> query = new QueryWrapper<UserDO>();
        query.lambda().in(UserDO::getEnabled, RbacUserEnable.ENABLE.getValue());
		return userMapper.selectList(query);
	}


	@Override
	public PageResponse<UserVO> listAllByPage(String userOName, String account, String roleCode, PageWrapper pageWrapper) {
		Page<UserVO> page = new Page<>(pageWrapper.getCurrentPage(), pageWrapper.getPageSize());
		IPage<UserVO> userDOPage = userMapper.selectAllByPage(page, account, userOName, roleCode);
		PageResponse<UserVO> pageResponse = new PageResponse<>(userDOPage);
		return pageResponse;
	}

	@Override
	public Map<String, Object> listByConditions(String userManager,String userOName,String roleName, PageWrapper pageWrapper) {
		Page<UserDO> page = new Page<>(pageWrapper.getCurrentPage(),pageWrapper.getPageSize());
		List<Map<String, Object>> resultList = Lists.newArrayList();
		List<String> codeList = Lists.newArrayList();
		Map<String, Object> resultMap = new HashMap<>();
		IPage<UserDO> resultPage = null;
		if (org.apache.commons.lang.StringUtils.isEmpty(userManager) && org.apache.commons.lang.StringUtils.isEmpty(userOName) && org.apache.commons.lang.StringUtils.isEmpty(roleName)) {
			resultPage = userMapper.selectPage(page, new QueryWrapper<>());
		} else if (org.apache.commons.lang.StringUtils.isEmpty(roleName)) {
			LambdaQueryWrapper<UserDO> queryWrapper = new QueryWrapper<UserDO>().lambda();
			if (StringUtils.isNotBlank(userManager)) {
				queryWrapper.eq(UserDO::getFinancier, userManager);
			}
			if (StringUtils.isNotBlank(userOName)) {
				queryWrapper.like(UserDO::getUserOName, userOName);
			}
			queryWrapper.eq(UserDO::getEnabled, RbacUserEnable.ENABLE.getValue());
			resultPage = userMapper.selectPage(page, queryWrapper);
		} else {
			resultPage = userMapper.selectAllByPageAndRoleName(page, userManager, userOName, roleName);
		}
		if (resultPage == null || CollectionUtils.isEmpty(resultPage.getRecords())) {
			return resultMap;
		}
		//增加管理人名称 和 角色名称
		//查询管理人名称
        List<UserDO> userDOs = resultPage.getRecords();
        List<String> financiers = userDOs.stream().map(e -> e.getFinancier()).collect(Collectors.toList());
        LambdaQueryWrapper<FinancierDO> queryWrapper = new QueryWrapper<FinancierDO>().lambda();
        if (!CollectionUtils.isEmpty(financiers)) {
            queryWrapper.in(FinancierDO::getCustOCode, financiers);
        }
		List<FinancierDO> financierList = financierMapper.selectList(queryWrapper);
		Map<String, String> financierMap = Maps.newHashMap();
		if (!CollectionUtils.isEmpty(financierList)) {
			financierMap = financierList.stream().collect(Collectors.toMap(e -> e.getCustOCode(), e -> e.getCustOName() == null ? "" : e.getCustOName(), (o1, o2) -> o2));
		}
		Map<String, String> financierMapNew = financierMap;
		//查询角色名称
		List<String> userCodes = userDOs.stream().map(e -> e.getUserOCode()).collect(Collectors.toList());
		List<UserVO> userRoleVOList = userMapper.selectUsersRolesByUserCodes(userCodes);
		Map<String, String> userRoleMap = Maps.newHashMap();
		if (!CollectionUtils.isEmpty(userRoleVOList)) {
			userRoleMap = userRoleVOList.stream().collect(Collectors.toMap(e -> e.getUserOCode(), e -> e.getRoleOName() == null ? "" : e.getRoleOName(), (o1, o2) -> o2));
		}
		Map<String, String> userRoleMapNew = userRoleMap;
		//List<Map<String,String>> userRoleList = selectUsersRolesByUserCodes(resultPage);
		resultPage.getRecords().forEach(UserDo -> {
			UserVO userVO = new UserVO();
			BeanUtils.copyProperties(UserDo, userVO);
			String financier = userVO.getFinancier();
			if (financier != null) {
				String financierName = financierMapNew.get(financier);
				userVO.setFinancierName(financierName);
			}
			String roleOName = userRoleMapNew.get(userVO.getUserOCode());
			userVO.setRoleOName(roleOName);
			String userJson = JsonUtils.obj2json(userVO);
			Map<String, Object> userMap = JsonUtils.json2map(userJson);
			resultList.add(userMap);
		});
		resultMap.put("records",resultList);
		resultMap.put("current",resultPage.getCurrent());
		resultMap.put("size",resultPage.getSize());
		resultMap.put("totalPages",resultPage.getPages());
		resultMap.put("total",resultPage.getTotal());

		return resultMap;
	}

	@Override
	public List<UserDO> listUserByAccount(String account) {
		QueryWrapper<UserDO> query = new QueryWrapper<UserDO>();
		query.lambda().eq(UserDO::getAccount, account);
		query.lambda().eq(UserDO::getEnabled, RbacUserEnable.ENABLE.getValue());
		return userMapper.selectList(query);
	}


	@Override
	public List<String> listUserOrg(String loginUserCode) {
		List<String> res = Lists.newArrayList();
		QueryWrapper<UserDO> query = new QueryWrapper<UserDO>();
		if (StringUtils.isNotBlank(loginUserCode)) {
			query.lambda().in(UserDO::getUserOCode, loginUserCode);
		}else {
			return res;
		}
		UserDO userDO = userMapper.selectOne(query);
		String org = userDO.getOrg();
		if (StringUtils.isNotBlank(org)){
			List<String> listOrg = JSON.parseArray(org, String.class);
			if (listOrg != null && listOrg.size() > 0){
				res = userMapper.selectOrgsByCodes(listOrg);
			}
		}
		return res;
	}

	@Override
	public Map getCurrentUserSession(String loginUserCode) {
		List<Map<String, Object>> sqlList = userConfig.getSqlList();
		List<Map<String, Object>> keyList = userConfig.getKeyList();
		Map res = new HashMap();
		Map data = new HashMap();
		res.put("keys",keyList);
		res.put("data",data);
		sqlList.stream().forEach(item -> {
			Object objSql = item.get("sql");
			Object objKey = item.get("key");
			if (objSql != null && StringUtils.isNotBlank(loginUserCode)){
				Map param = new HashMap();
				param.put("userCode",loginUserCode);
				log.info("====================================");
				log.info("执行sql条件："+ JSON.toJSONString(param));
				log.info("执行sql："+ JSON.toJSONString(objSql));
				Map oneData = dynamicSqlExecutorUtils.findOneData(objSql + "", param);
				log.info("执行结果："+ JSON.toJSONString(oneData));
				keyList.forEach( keyItem -> {
					Object key = keyItem.get("key");
					log.info("key："+ JSON.toJSONString(key));
					log.info("key2："+(key + "").replaceFirst(objKey + ":", "") );
					Object o = oneData.get( (key + "").replaceFirst(objKey + ":", "")
							.replaceFirst(objKey + "-", ""));
					log.info("value："+ JSON.toJSONString(o));
					if (o != null){
						data.put(key,o);
					}
				});
			}
		});
		return res;
	}

	@Override
	public List<Map<String, Object>> getCurrentUserSessionKeyList() {
		userConfig.getKeyList().stream().forEach(item -> {
			String s = (item.get("key") + "").replaceAll(":", "-");
			item.put("key" + "",s );
		});
		return userConfig.getKeyList();
	}

    @Override
    public List<UserDO> getAllEffUser() {
        QueryWrapper<UserDO> query = new QueryWrapper<UserDO>();
        query.lambda().select(UserDO::getUserOCode, UserDO::getUserOName, UserDO::getAccount);
        query.lambda().eq(UserDO::getEnabled,RbacUserEnable.ENABLE.getValue());
        return userMapper.selectList(query);
    }

	@Override
	public PageResponse<UserVO> listUsersByCondition(String account, String userOName, PageWrapper pageWrapper,String userOCode) {
		Page<UserVO> page = new Page<>(pageWrapper.getCurrentPage(),pageWrapper.getPageSize());

		//TODO 还需观察看下是否可行，通过账号获取
		/*UserDO currentUser = getFirstUserByAccount(currentUserAcc);
		if(currentUser == null){
			return new PageResponse<>();
		}*/
		//获取当前登录用户类型 内部or外部（通过userCode）
		boolean isInnerUser = isInnerUser(userOCode);
		/*LambdaQueryWrapper<UserDO> queryWrapper = new QueryWrapper<UserDO>().lambda();

		if (StringUtils.isNotEmpty(account)){
			queryWrapper.like(UserDO::getAccount, account);
		}
		if ((StringUtils.isNotEmpty(userOName))){
			queryWrapper.like(UserDO::getUserOName, userOName);
		}*/
		// 执行分页查询
		IPage<UserVO> resultPage;
		if(isInnerUser){
			//内部用户查询所有用户，包含流程信息
			resultPage = userMapper.selectPageInner(page,account,userOName);
		}else{
			//todo 外部用户只能查询本方机构信息且不能查看流程信息（外部用户流程信息是否展示，怎么展示）
			resultPage = userMapper.selectPageOut(page, account,userOName,userOCode);
		}


		PageResponse<UserVO> pageResponse = new PageResponse<>(resultPage);
		return pageResponse;
	}

	@Override
	public List<UserDO> listUsersByFinancier(String financier) {
		QueryWrapper<UserDO> query = new QueryWrapper<UserDO>();
		query.lambda().eq(UserDO::getFinancier,financier);
		return userMapper.selectList(query);

	}

	@Override
	public Map<String, Object> getUserDepartInformation(String loginUserCode) {
		return userMapper.getUserDepartInformation(loginUserCode);
	}

    @Override
    public Map getUserMapping() {
        QueryWrapper<UserDO> query = new QueryWrapper<UserDO>();
        query.lambda().select(UserDO::getUserOCode, UserDO::getUserOName);

        List<UserDO> userDOS = userMapper.selectList(query);

        Map<String, String> userMap = userDOS.stream()
                .collect(Collectors.toMap(UserDO::getUserOCode, UserDO::getUserOName));

        return userMap;
    }

	@Override
	public List<Map> getDepts() {
		List<Map> deptOriginalData = dynamicSqlExecutorUtils.findListData("SELECT * FROM SYS_RBAC_DEPART ", null);
		List<Map> resultList = Lists.newArrayList();
        //第一步，添加根节点数据
		resultList.addAll(deptOriginalData.stream()
				.filter(map -> map.get("DEPT_P_O_CODE") == null || map.get("DEPT_P_O_CODE").equals("0") || map.get("DEPT_P_O_CODE") == "")
				.collect(Collectors.toList()));

		deptOriginalData.removeIf(map -> map.get("DEPT_P_O_CODE") == null || map.get("DEPT_P_O_CODE").equals("0") || map.get("DEPT_P_O_CODE") == "");
		for (int i = 0; i < resultList.size(); i++) {
			deptOriginalData.forEach(deptOriginal-> deptOriginal.put("DEPT_O_NAME"," "+deptOriginal.get("DEPT_O_NAME")));
			Map node = resultList.get(i);
			List<Map> childNode = deptOriginalData.stream().filter(f -> node.get("DEPT_O_CODE").equals(f.get("DEPT_P_O_CODE")))
					.sorted(Comparator.comparing(f -> (String) f.get("DEPT_O_CODE")))
					.collect(Collectors.toList());
			if (CollectionUtils.isEmpty(childNode)){
				continue;
			}
			Collections.reverse(childNode);
			deptOriginalData.removeIf(j -> node.get("DEPT_O_CODE").equals(j.get("DEPT_P_O_CODE")));

			for (int j = 0; j < childNode.size(); j++) {
				resultList.add(i+1,childNode.get(j));
			}
		}


		return resultList;
	}

	private boolean isInnerUser(String userOCode){
		UserDO userByUserCode = getUserByUserCode(userOCode);
		boolean isInnerUserFlag = false;
		if("1".equals(userByUserCode.getIsInnerUser())){
			isInnerUserFlag = true;
		}
		return isInnerUserFlag;
	}


	public List<Map<String,String>> selectUsersRolesByUserCodes(IPage<UserDO> resultPage){
		List<String> codeList = Lists.newArrayList();
		resultPage.getRecords().forEach(userDO->{
			codeList.add(userDO.getUserOCode());
		});
		//List<UserWithRolesResultMap> userWithRolesResultMaps = userMapper.selectUsersRolesByUserCodes(codeList);
		return null;
	}

}
