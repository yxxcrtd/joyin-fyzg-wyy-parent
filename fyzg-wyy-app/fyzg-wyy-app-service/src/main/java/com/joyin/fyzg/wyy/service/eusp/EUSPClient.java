package com.joyin.fyzg.wyy.service.eusp;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class EUSPClient {

    //需要提供url，用户名及密码，token自己生成
    private final String wsdlUrl;
    private final String username;
    private final String password;
    private String token;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public EUSPClient(String wsdlUrl, String username, String password) {
        this.wsdlUrl = wsdlUrl;
        this.username = username;
        this.password = password;
    }

    // ========== 1. 登录 ==========
    /*public boolean login() {
        try {
            URL url = new URL(wsdlUrl);
            //从axis生成的客户端代码获取的服务及端口
            EUSPService service = new EUSPService(url);
            EUSPPortType port = service.getEUSPPortType();

            String result = port.login(username, password, "EMSP");
            if (result != null && !result.isEmpty() && !result.startsWith("-1")) {
                this.token = result;
                System.out.println("登录成功，token: " + token);
                return true;
            } else {
                System.err.println("登录失败: " + result);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }*/

    // ========== 2. 调用 callService ==========
    /*public EUSPResponse callService(String methodName, Map<String, Object> params) {
        try {
            URL url = new URL(wsdlUrl);
            EUSPService service = new EUSPService(url);
            EUSPPortType port = service.getEUSPPortType();

            // 构造 parameters: [{ name: "requestStr", value: json }]
            EUSPParameter param = new EUSPParameter();
            param.setName("requestStr");
            param.setValue(objectMapper.writeValueAsString(params));

            ArrayOf_tns1_EUSPParameter paramsArray = new ArrayOf_tns1_EUSPParameter();
            paramsArray.getItem().add(param);

            // 调用
            String response = port.callService(token, "EMSP", "eapbm", methodName, paramsArray);

            // 解析返回的 JSON 字符串
            Map<String, Object> respMap = objectMapper.readValue(response, Map.class);
            return new EUSPResponse(
                    (Integer) respMap.getOrDefault("retCode", -1),
                    (String) respMap.getOrDefault("retDesc", "未知错误"),
                    (String) respMap.getOrDefault("retValue", null)
            );

        } catch (Exception e) {
            e.printStackTrace();
            return new EUSPResponse(-1, "调用异常: " + e.getMessage(), null);
        }
    }*/

    // ========== 3. 登出 ==========
    /*public void logout() {
        if (token != null) {
            try {
                URL url = new URL(wsdlUrl);
                EUSPService service = new EUSPService(url);
                EUSPPortType port = service.getEUSPPortType();
                port.logout(token, "EMSP");
                System.out.println("成功登出");
            } catch (Exception e) {
                System.err.println("登出失败: " + e.getMessage());
            }
        }
    }*/

    // ========== 4. 业务接口封装 ==========

    // 新增用户
    /*public EUSPResponse addUser(
            String manager, String userCode, String userName,
            String phoneNo, String certCode, String emailAddr,
            String userRoleList, String isAllFundAuth, List<UserAuthFund> authFunds) {

        Map<String, Object> params = new HashMap<>();
        params.put("MANAGER", manager);
        params.put("USER_CODE", userCode);
        params.put("USER_NAME", userName);
        params.put("PHONE_NO", phoneNo);
        params.put("CERT_CODE", certCode);
        params.put("EMAIL_ADDR", emailAddr);
        params.put("USER_ROLE_LIST", userRoleList);
        params.put("IS_ALL_FUND_AUTH", isAllFundAuth);

        if ("0".equals(isAllFundAuth) && authFunds != null) {
            List<Map<String, Object>> fundList = new ArrayList<>();
            for (UserAuthFund f : authFunds) {
                fundList.add(f.toMap());
            }
            params.put("USER_AUTH_FUND_LIST", fundList);
        }

        return callService("addUserByInt", params);
    }
*/
    // 更新用户
    /*public EUSPResponse updateUser(
            String userId, String userCode, String userName,
            String phoneNo, String certCode, String emailAddr,
            String userRoleList, String isAllFundAuth, List<UserAuthFund> authFunds) {

        Map<String, Object> params = new HashMap<>();
        params.put("USER_ID", userId);
        params.put("USER_CODE", userCode);
        params.put("USER_NAME", userName);
        params.put("PHONE_NO", phoneNo);
        params.put("CERT_CODE", certCode);
        params.put("EMAIL_ADDR", emailAddr);
        params.put("USER_ROLE_LIST", userRoleList);
        params.put("IS_ALL_FUND_AUTH", isAllFundAuth);

        if ("0".equals(isAllFundAuth) && authFunds != null) {
            List<Map<String, Object>> fundList = new ArrayList<>();
            for (UserAuthFund f : authFunds) {
                fundList.add(f.toMap());
            }
            params.put("USER_AUTH_FUND_LIST", fundList);
        }

        return callService("updateUserByInt", params);
    }*/

    // 更新用户状态
   /* public EUSPResponse updateUserStatus(String userId, String userStatus) {
        Map<String, Object> params = new HashMap<>();
        params.put("USER_ID", userId);
        params.put("USER_STATUS", userStatus); // 0:启用, 1:停用
        return callService("updateUserStatusByInt", params);
    }
*/
    // getter
    public String getToken() { return token; }
}
