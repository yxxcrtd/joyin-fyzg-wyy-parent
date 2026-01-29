package com.joyin.fyzg.wyy.common.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用户创建申请 - 请求参数 DTO
 */
@Data
public class UserApplyDTO {

    private String userOCode;
    private String userOName;
    private String account;
    private String org;
    private String financier;
    private String enabled;
    private String tel;
    private String mobile;
    private String email;
    private String themeCfgJson;
    private String collectCfgJson;
    private String remark;

    // 前端上传后返回的 attachmentId 列表
    private String attachmentId;

    //当前人申请人usercode
    private String applicant;

    private String userType;

    private String userManager; //用户管理人

    private String certType; //证件类型

    private String certNo; //证件号码

    private String modiAdmin;//1:调整管理员
}
