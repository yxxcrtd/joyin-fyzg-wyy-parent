/*
package com.joyin.fyzg.wyy.service.eusp;

import com.shine.eusp.iopara.EUSPOutput;
import com.shine.eusp.iopara.*;
//根据wsdl url 生成的package及相关类
import _1._0._0._127.EUSP.EUSP_ws.EUSP;
import _1._0._0._127.EUSP.EUSP_ws.EUSPService;
import _1._0._0._127.EUSP.EUSP_ws.EUSPServiceLocator;
import _1._0._0._127.EUSP.EUSP_ws.EUSPWsSoapBindingStub;

public class WSClientTest {
    public static void main(String strs[]) {
        try {
            // 初始化EUSPImpl
            String endpoint = "http://127.0.0.1:8080/EUSP/EUSP.jws?wsdl ";
            EUSPService euspsl = new EUSPServiceLocator();
            EUSP ei = euspsl.getEUSPWs(new java.net.URL(endpoint));
            // 登录,密码是md5加密后的密码
            // 原始密码需要向服务提供方申请获取
            EUSPOutput loginOutput = ei.login("test",
                    "098f6bcd4621d373cade4e832627b4f6");
            if (loginOutput.getRetCode() == 0) {
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
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
*/
