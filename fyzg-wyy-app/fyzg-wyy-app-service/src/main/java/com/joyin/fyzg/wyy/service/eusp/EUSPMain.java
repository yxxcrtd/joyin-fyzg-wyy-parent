/*
package com.joyin.fyzg.wyy.service.eusp;



import java.util.Arrays;
import java.util.List;

public class EUSPMain {
    public static void main(String[] args) {
        // 配置信息
        String wsdlUrl = "http://127.0.0.1:18090/EUSP/EUSP.ws?wsdl"; // 替换为真实地址
        String username = "your_username";
        String password = "your_password";

        EUSPClient client = new EUSPClient(wsdlUrl, username, password);

        // 1. 登录
        if (!client.login()) {
            System.err.println("登录失败，程序退出");
            return;
        }

        // 2. 新增用户
        List<UserAuthFund> funds = Arrays.asList(
                new UserAuthFund("F001", "0101,0102"),
                new UserAuthFund("F002", "0000")
        );

        EUSPResponse addResp = client.addUser(
                "ORG001", "U001", "测试用户", "18612345678", "123456789012345678",
                "test@example.com", "2,3", "0", funds
        );
        System.out.println("【新增用户】" + addResp);

        if (addResp.getRetCode() != 0) {
            client.logout();
            return;
        }

        String userId = addResp.getRetValue(); // 获取用户ID

        // 3. 更新用户
        EUSPResponse updateResp = client.updateUser(
                userId, "U001", "测试用户更新", "18612345678", "123456789012345678",
                "new@example.com", "2", "1", null
        );
        System.out.println("【更新用户】" + updateResp);

        // 4. 停用用户
        EUSPResponse statusResp = client.updateUserStatus(userId, "1");
        System.out.println("【停用用户】" + statusResp);

        // 5. 登出
        client.logout();
    }
}
*/
