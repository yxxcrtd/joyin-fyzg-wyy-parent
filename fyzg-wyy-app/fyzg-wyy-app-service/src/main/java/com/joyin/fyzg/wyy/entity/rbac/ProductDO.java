package com.joyin.fyzg.wyy.entity.rbac;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表SYS_CONST_DESKTOP_COMPONENT的实体类
 * @author 工具生成
 * @version 1.0
 * @since 
 */
@Data
@TableName("SYS_CONST_PRODUCT")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDO {

    /** R_ID : 数据编号，自增长主键 */
    @TableId(value = "R_ID", type = IdType.INPUT)
    private Long rId;


    /** CODE : 业务线代码 */
    private String code;


    /** NAME : 业务线名称 */
    private String name;


    /** DESKTOP_COMPONENT_SQL :  桌面组件sql*/
    private String desktopComponentSql;


    /** ANNEX_SUFFIX : 文件后缀名 */
    private String annexSuffix;


    /** MD_ANNEX_SUFFIX : 移动端文件后缀名 */
    private String mdAnnexSuffix;


    /** DEFAULT_DESKTOP_CFG_USER : 默认桌面配置的用户 */
    private String defaultDesktopCfgUser;


    /** ENABLED : 状态：0禁用；1:启用 */
    private String enabled;


    /** DESKTOP_TAB_CC_ENABLED : 我的抄送页签开关：0禁用；1:启用 */
    private String desktopTabCcEnabled;


    /** DESKTOP_TAB_CC_ENABLED2 : 我的抄送(最新布局)页签开关：0禁用；1:启用 */
    private String desktopTabCcEnabled2;


    /** DESKTOP_TAB_HASDO_ENABLED : 已办未完结页签开关：0禁用；1:启用 */
    private String desktopTabHasdoEnabled;


    /** DESKTOP_TAB_HASDOSTRAT_ENABLED : 已办未完结（我发起的）页签开关：0禁用；1:启用 */
    private String desktopTabHasdostratEnabled;


    /** DESKTOP_TAB_HASDOJOIN_ENABLED : 已办未完结（我参与的）页签开关：0禁用；1:启用 */
    private String desktopTabHasdojoinEnabled;


    /** DESKTOP_TAB_MYSTART_ENABLED : 我的发起页签开关：0禁用；1:启用  */
    private String desktopTabMystartEnabled;


    /** TODO_LABLE_NAME : 我的待办标签名称 */
    private String todoLableName;

    /**BUTTON_NAME: 按钮名称*/
    private String buttonName;

    /** BTN_BACK_SET : 退回方式：0:逐级+越级 1:逐级 2:越级 */
    private String btnBackSet;

    /** CARD_TAB_STYLE  系统桌面卡片样式   theme:跟随主题 white:白色 */
    private String cardTabStyle ;

    /** BELL_NOTICE_ENABLED  铃铛开关：0禁用；1:启用 */
    private String bellNoticeEnabled ;

    /** WF_HIS_TYPE 流程历史样式：1=用户角色表格;2=详情卡片;3=用户部门表格*/
    private String wfHisType;

    /** OPBTN_TYPE 操作按钮样式:1=图标;2=文字*/
    private String opbtnType;

    /** CA_PAGINATION 文档插件是否分页0是分页，1不分页*/
    private String caPagination;

    /** MYPENDING_ENABLED 我的待发页签开关：0禁用；1:启用*/
    private String mypendingEnabled;

    /** MYREMIND_ENABLED 我的提醒页签开关：0禁用；1:启用*/
    private String myremindEnabled;

    /** IS_DEFAULT 是否为默认值: 0:否,1:是*/
    private String isDefault;

    /** TRANSFER_SELECTION 转办选择所有人开关: 0:禁用,1:启用*/
    private String transferSelection;

}
