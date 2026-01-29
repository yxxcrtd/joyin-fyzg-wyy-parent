package com.joyin.fyzg.wyy.service.rbac.impl;

import com.bes.gson.JsonObject;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.common.utils.BasicMD5;
import com.joyin.fyzg.wyy.entity.rbac.UserDO;
import com.joyin.fyzg.wyy.service.eusp.EUSPParam;
import com.joyin.fyzg.wyy.service.rbac.ShineSyncService;
import com.shine.eusp.iopara.EUSPOutput;
import com.shine.eusp.iopara.EUSPParameter;
import com.shine.eusp.ws.EUSP;
import com.shine.eusp.ws.EUSPService;
import com.shine.eusp.ws.EUSPServiceLocator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class ShineSyncServiceImpl implements ShineSyncService {

	@Value("${user.password}")
	public String password;

	/*@Value("${shine.endpoint}")*/
	public final String endpoint = "http:/10.46.48.140:8080/EUSP/EUSP.ws?wsdl";

	/*@Value("${shine.loginuser}")*/
	public final String SHINELOGINUSER = "test";

	/*@Value("${shine.user.passwrod}")*/
	public final String SHINEUSERPASSWORD = "1";

	@Override
	public EUSPOutput login() {
		try {
			EUSPService euspsl = new EUSPServiceLocator();
			EUSP ei = euspsl.getEUSPWs(new java.net.URL(endpoint));
			// 登录,密码是md5加密后的密码
			// 原始密码需要向服务提供方申请获取
			String password = StringUtils.defaultIfBlank(SHINEUSERPASSWORD,"1");
			EUSPOutput loginOutput = ei.login(StringUtils.defaultIfBlank(SHINELOGINUSER,"test"),
					BasicMD5.md5Basic(password));
			return loginOutput;
			/*if (loginOutput.getRetCode() == 0) {
				// 调用获取某个用户基本信息服务
				// 登录成功，取得令牌
				String token = loginOutput.getRetValue();
				// 产品名
				String pid = "EFRAME";
				// 服务名
				String serviceName = "esystem";
				// 方法名
				String methodName = "getUserInfoByUserCode";
				// 方法参数，只有一个参数
				EUSPParameter[] param = new EUSPParameter[1];
				param[0] = new EUSPParam();
				param[0].setName("USER_CODE");
				param[0].setValue("LAY");

				EUSPOutput csOutput = ei.callService(token, pid, serviceName,
						methodName, param);
				if (csOutput.getRetCode() == 0) {
					// 最多只有一个行结果集
					if (csOutput.getRowsetCount() == 1) {
						EUSPRowSet rs[] = csOutput.getRowsets();
						// 只有一个行结果集rs[0]
						String field[] = rs[0].getColumns();
						int flen = rs[0].getColumnCount();
						EUSPRecord recode[] = rs[0].getRecords();
						// 最多只有一笔记录
						if (rs[0].getRecordCount() == 1) {
							for (int i = 0; i < flen; i++) {
								// 只有一笔记录recode[0]
								System.out.println(field[i] + ":"
										+ recode[0].getDatas()[i]);
							}
						}
					}
				}

				// 调用获取所有用户基本信息服务
				// 方法参数，参数为空
				param = null;
				// 先获取记录总数
				// 方法名
				methodName = "getAllUserCount";
				int rowount = 0;
				csOutput = ei.callService(token, pid, serviceName, methodName,
						param);
				if (csOutput.getRetCode() == 0) {
					rowount = Integer.parseInt(csOutput.getRetValue());
				}
				// 循环调用方法名
				methodName = "getAllUserInfo";
				// 两个参数
				int recordNum = 2000;// 一次获取记录数
				param = new EUSPParameter[2];
				param[0] = new EUSPParam();
				param[0].setName("RECORD_NUM");// 一次获取记录数
				param[0].setValue("" + recordNum);
				param[1] = new EUSPParam();
				param[1].setName("BEGIN_NUM");// 起始记录号

				// 循环次数
				int forint = (rowount / recordNum) + 1;
				for (int i = 0; i < forint; i++) {
					param[1].setValue("" + i * recordNum);
					csOutput = ei.callService(token, pid, serviceName,
							methodName, param);
					if (csOutput.getRetCode() == 0) {
						// 最多只有一个行结果集
						if (csOutput.getRowsetCount() == 1) {
							EUSPRowSet rs[] = csOutput.getRowsets();
							// 只有一个行结果集rs[0]
							String field[] = rs[0].getColumns();
							int flen = rs[0].getColumnCount();
							EUSPRecord recode[] = rs[0].getRecords();
							// 多行
							int rlen = rs[0].getRecordCount();
							for (int j = 0; j < rlen; j++) {
								String data[] = recode[j].getDatas();
								System.out.println(field[i] + ":" + data[i]);
							}
						}
					}
				}
				// 不使用服务，登出注销
				ei.logout(token);
			}*/

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public EUSPOutput insertUser(UserDO userDO) {
		EUSPOutput csOutput = new EUSPOutput();
		csOutput.setRetCode(-1);
		try {
			EUSPService euspsl = new EUSPServiceLocator();
			EUSP ei = euspsl.getEUSPWs(new java.net.URL(endpoint));
			// 登录,密码是md5加密后的密码
			// 原始密码需要向服务提供方申请获取
			String password = StringUtils.defaultIfBlank(SHINEUSERPASSWORD,"1");
			log.info("新增用户:{}同步新意,登录用户名:{},密码:{},MD5密码:{}", userDO.getUserOName(),SHINELOGINUSER,password,BasicMD5.MD5(password));
			EUSPOutput loginOutput = ei.login(StringUtils.defaultIfBlank(SHINELOGINUSER,"test"),
					BasicMD5.MD5(password));
			if (loginOutput.getRetCode() == 0) {
				// 调用获取某个用户基本信息服务
				// 登录成功，取得令牌
				String token = loginOutput.getRetValue();
				// 产品名
				String pid = "EMSP";
				// 服务名
				String serviceName = "eapbm";
				// 方法名
				String methodName = "addUserByInt";
				JsonObject jsonParams = new JsonObject();
				jsonParams.addProperty("MANAGER",userDO.getFinancier());
				jsonParams.addProperty("USER_CODE",userDO.getMobile());
				jsonParams.addProperty("USER_NAME",userDO.getUserOName());
				jsonParams.addProperty("PHONE_NO",userDO.getMobile());
				jsonParams.addProperty("CERT_CODE",userDO.getCertNo());
				jsonParams.addProperty("EMAIL_ADDR",userDO.getEmail());
				/*jsonParams.addProperty("USER_ROLE_LIST","");
				jsonParams.addProperty("IS_ALL_FUND_AUTH","");
				jsonParams.addProperty("USER_AUTH_FUND_LIST","");*/
				// 方法参数，只有一个参数
				EUSPParameter[] param = new EUSPParameter[1];
				param[0] = new EUSPParam();
				param[0].setName("requestStr");
				param[0].setValue(jsonParams.toString());
				log.info("新增用户callservice,token={},pid={},serviceName={},methodName={},param={}",token,pid,serviceName,methodName,jsonParams.toString());
				csOutput = ei.callService(token, pid, serviceName,
						methodName, param);
				if (csOutput.getRetCode() == 0) {
					// 最多只有一个行结果集
					/*if (csOutput.getRowsetCount() == 1) {
						EUSPRowSet rs[] = csOutput.getRowsets();
						// 只有一个行结果集rs[0]
						String field[] = rs[0].getColumns();
						int flen = rs[0].getColumnCount();
						EUSPRecord recode[] = rs[0].getRecords();
						// 最多只有一笔记录
						if (rs[0].getRecordCount() == 1) {
							for (int i = 0; i < flen; i++) {
								// 只有一笔记录recode[0]
								System.out.println(field[i] + ":"
										+ recode[0].getDatas()[i]);
							}
						}
					}*/
				}else{
					log.info("新意新增用户失败,code:{},msg:{}", csOutput.getRetCode(), csOutput.getRetMessage());
				}
				log.info("新增用户新意返回USER_ID={}",csOutput.getRetValue());
				// 不使用服务，登出注销
				ei.logout(token);
			} else {
				log.info("新意登录失败,code:{},msg:{}", loginOutput.getRetCode(), loginOutput.getRetMessage());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return csOutput;
	}

	@Override
	public EUSPOutput updateUserById(UserDO userDO) {
		EUSPOutput csOutput = new EUSPOutput();
		csOutput.setRetCode(-1);
		try {
			// 初始化EUSPImpl
			EUSPService euspsl = new EUSPServiceLocator();
			EUSP ei = euspsl.getEUSPWs(new java.net.URL(endpoint));
			// 登录,密码是md5加密后的密码
			// 原始密码需要向服务提供方申请获取
			String password = StringUtils.defaultIfBlank(SHINEUSERPASSWORD,"1");
			log.info("修改用户:{}同步新意,登录用户:{},密码:{},MD5密码:{}", userDO.getUserOName(),SHINELOGINUSER,password,BasicMD5.MD5(password));
			EUSPOutput loginOutput = ei.login(StringUtils.defaultIfBlank(SHINELOGINUSER,"test"),
					BasicMD5.md5Basic(password));
			if (loginOutput.getRetCode() == 0) {
				// 调用获取某个用户基本信息服务
				// 登录成功，取得令牌
				String token = loginOutput.getRetValue();
				// 产品名
				String pid = "EMSP";
				// 服务名
				String serviceName = "eapbm";
				// 方法名
				String methodName = "updateUserByInt";
				JsonObject jsonParams = new JsonObject();
				jsonParams.addProperty("USER_ID",userDO.getUserOCode());
				jsonParams.addProperty("USER_CODE",userDO.getMobile());
				jsonParams.addProperty("USER_NAME",userDO.getUserOName());
				jsonParams.addProperty("PHONE_NO",userDO.getMobile());
				jsonParams.addProperty("CERT_CODE",userDO.getCertNo());
				jsonParams.addProperty("EMAIL_ADDR",userDO.getEmail());
				/*jsonParams.addProperty("USER_ROLE_LIST","");
				jsonParams.addProperty("IS_ALL_FUND_AUTH","");
				jsonParams.addProperty("USER_AUTH_FUND_LIST","");*/
				// 方法参数，只有一个参数
				EUSPParameter[] param = new EUSPParameter[1];
				param[0] = new EUSPParam();
				param[0].setName("requestStr");
				param[0].setValue(jsonParams.toString());
				log.info("修改用户callservice,token={},pid={},serviceName={},methodName={},param={}",token,pid,serviceName,methodName,param);
				csOutput = ei.callService(token, pid, serviceName,
						methodName, param);
				if (csOutput.getRetCode() == 0) {

				}else{
					log.info("新意修改用户失败,code:{},msg:{}", csOutput.getRetCode(), csOutput.getRetMessage());
				}
				// 不使用服务，登出注销
				ei.logout(token);
			} else {
				log.info("新意登录失败,code:{},msg:{}", loginOutput.getRetCode(), loginOutput.getRetMessage());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return csOutput;
	}

	@Override
	public EUSPOutput deleteUserById(String user_id) {
		EUSPOutput csOutput = new EUSPOutput();
		csOutput.setRetCode(-1);
		try {
			log.info("删除用户user_id:{}同步新意", user_id);
			EUSPService euspsl = new EUSPServiceLocator();
			EUSP ei = euspsl.getEUSPWs(new java.net.URL(endpoint));
			// 登录,密码是md5加密后的密码
			// 原始密码需要向服务提供方申请获取
			String password = StringUtils.defaultIfBlank(SHINEUSERPASSWORD,"1");
			log.info("删除用户user_id:{}同步新意,登录用户:{},密码:{},MD5密码:{}", user_id,SHINELOGINUSER,password,BasicMD5.MD5(password));
			EUSPOutput loginOutput = ei.login(StringUtils.defaultIfBlank(SHINELOGINUSER,"test"),
					BasicMD5.md5Basic(password));
			if (loginOutput.getRetCode() == 0) {
				// 调用获取某个用户基本信息服务
				// 登录成功，取得令牌
				String token = loginOutput.getRetValue();
				// 产品名
				String pid = "EMSP";
				// 服务名
				String serviceName = "eapbm";
				// 方法名
				String methodName = "updateUserStatusByInt";
				JsonObject jsonParams = new JsonObject();
				jsonParams.addProperty("USER_ID", user_id);
				jsonParams.addProperty("USER_STATUS","1");
				// 方法参数，只有一个参数
				EUSPParameter[] param = new EUSPParameter[1];
				param[0] = new EUSPParam();
				param[0].setName("requestStr");
				param[0].setValue(jsonParams.toString());
				log.info("删除用户callservice,token={},pid={},serviceName={},methodName={},param={}",token,pid,serviceName,methodName,param);
				csOutput = ei.callService(token, pid, serviceName,
						methodName, param);
				if (csOutput.getRetCode() == 0) {

				}else{
					log.info("新意删除用户失败,code:{},msg:{}", csOutput.getRetCode(), csOutput.getRetMessage());
				}
				// 不使用服务，登出注销
				ei.logout(token);
			} else {
				log.info("新意登录失败,code:{},msg:{}", loginOutput.getRetCode(), loginOutput.getRetMessage());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return csOutput;
	}

	@Override
	public EUSPOutput updateUser4ResetPwd(String user_id) {
		EUSPOutput csOutput = new EUSPOutput();
		csOutput.setRetCode(-1);
		try {
			log.info("用户重置密码user_id:{}同步新意", user_id);
			EUSPService euspsl = new EUSPServiceLocator();
			EUSP ei = euspsl.getEUSPWs(new java.net.URL(endpoint));
			// 登录,密码是md5加密后的密码
			// 原始密码需要向服务提供方申请获取
			String password = StringUtils.defaultIfBlank(SHINEUSERPASSWORD,"1");
			log.info("用户重置密码user_id:{}同步新意,登录用户:{},密码:{},MD5密码:{}", user_id,SHINELOGINUSER,password,BasicMD5.MD5(password));
			EUSPOutput loginOutput = ei.login(StringUtils.defaultIfBlank(SHINELOGINUSER,"test"),
					BasicMD5.md5Basic(password));
			if (loginOutput.getRetCode() == 0) {
				// 调用获取某个用户基本信息服务
				// 登录成功，取得令牌
				String token = loginOutput.getRetValue();
				// 产品名
				String pid = "EMSP";
				// 服务名
				String serviceName = "eapbm";
				// 方法名
				String methodName = "resetUserPwd";
				JsonObject jsonParams = new JsonObject();
				jsonParams.addProperty("USER_ID", user_id);
				// 方法参数，只有一个参数
				EUSPParameter[] param = new EUSPParameter[1];
				param[0] = new EUSPParam();
				param[0].setName("requestStr");
				param[0].setValue(jsonParams.toString());
				log.info("用户重置密码callservice,token={},pid={},serviceName={},methodName={},param={}",token,pid,serviceName,methodName,param);
				csOutput = ei.callService(token, pid, serviceName,
						methodName, param);
				if (csOutput.getRetCode() == 0) {

				}else{
					log.info("新意重置密码失败,code:{},msg:{}", csOutput.getRetCode(), csOutput.getRetMessage());
				}
				// 不使用服务，登出注销
				ei.logout(token);
			} else {
				log.info("新意登录失败,code:{},msg:{}", loginOutput.getRetCode(), loginOutput.getRetMessage());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return csOutput;
	}

}
