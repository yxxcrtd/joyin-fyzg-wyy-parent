package com.joyin.fyzg.wyy.vo.MessageNotice;

import lombok.Data;

//消息页面显示
@Data
public class MessageNoticeVO {
    
    /** R_ID : 数据编号，自动生成 */
    private String rId;

    /** 1：消息；2：提醒 */
    private String type;

    /** 是否已读  0：未读；1：已读 */
    private String readFlag;
    
    
    /** 发送方 */
    private String sender;

    /** 用户所属机构 */
    private String org;

    /** 通知方式 */
    private String noticeWay;

    /** 短信发送内容 */
    private String smsContent;


    /** sql配置 */
    private String sql;

    /** 是否需要反馈 */
    private String feedbackFlag;


    /** 反馈结果 */
    private String feedbackResult;

    /** 首页展示类型 */
    private String homepageType;
    
    
    /**标题 */
    private String title;
    
    
    /** 内容 */
    private String content;


    /** 附件信息 */
    private String fileInfo;

    /** 文件名称 */
    private String fileName;

    /** 文件地址 */
    private String url;

    /*CREATE_TIME:创建时间*/
    private String createTime;

    /*UPDATE_TIME:更新时间*/
    private String updateTime;


    /** USER_O_NAME : 用户名称 */
    private String userOName;

    /**financierName 用户所属的管理人名称*/
    private String financierName;

}
