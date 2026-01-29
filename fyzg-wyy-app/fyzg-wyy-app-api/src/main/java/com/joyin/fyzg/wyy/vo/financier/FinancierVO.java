package com.joyin.fyzg.wyy.vo.financier;

import com.google.common.collect.Lists;
import com.joyin.fyzg.wyy.vo.rbac.MenuPageComponentVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 表RBAC_MENU_PAGE的VO类
 * @author 工具生成
 * @version 1.0
 * @since
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancierVO {

    /** R_ID : 数据编号，自增长主键 */
    private Long rId;


    /** CUST_O_CODE : 管理人代码 */
    private String custOCode;


    /** CUST_O_NAME : 管理人名称 */
    private String custOName;


    /** JY_INSERT_TIME : 创建时间 */
    private String jyInsertTime;


    /** JY_UPDATE_TIME : 修改时间 */
    private String jyUpdateTime;



    private List<FinancierMpVO> financierMpList = Lists.newArrayList();

}
