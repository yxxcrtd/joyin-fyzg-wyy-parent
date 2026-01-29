package com.joyin.fyzg.wyy.entity.user;


import lombok.Data;

import java.util.Date;

@Data
public class WyyUserBaseInfoDO {
    private Long userId;
    private Integer userTypeId;
    private String userTypeName;
    private String manager;
    private String managerName;
    private String userCode;
    private String userName;
    private String certType;
    private String certTypeName;
    private String certCode;
    private String phoneNo;
    private String emailAddr;
    private String pwd;
    private String userStatus;
    private Date createTime;
    private Date updateTime;

}
