package com.joyin.fyzg.wyy.entity.messageNotice;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表SYS_NOTICETEMPLATE的实体类
 * @author 工具生成
 * @version 1.0
 * @since 
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("SYS_NOTICETEMPLATE")
public class NoticeTemplateDO {
    
    /** R_ID : 数据编号，自动生成 */
    @TableId(value = "R_ID", type = IdType.UUID)
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


    /** 附件url */
    private String urlId;

    /*CREATE_TIME:创建时间*/
    private String createTime;

    /*UPDATE_TIME:更新时间*/
    private String updateTime;

}
