package com.joyin.fyzg.wyy.vo.rbac;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class UserAccountVO {

    public UserAccountVO(String userCode, String userName) {
        this.userCode = userCode;
        this.userName = userName;
    }

    public UserAccountVO(String userCode, String userName, String financier, String custOName) {
        this.userCode = userCode;
        this.userName = userName;
        this.financier = financier;
        this.custOName = custOName;
    }

    /**
     * 用户代码
     */
    private String userCode;


    /**
     * 用户名称
     */
    private String userName;

    /**
     * FINANCIER :用户所属的管理人
     */
    private String financier;

    /* 用户机构名称 */
    private String custOName;
}
