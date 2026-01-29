package com.joyin.fyzg.wyy.entity.messageNotice;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 用户分组信息表
 * @TableName SYS_MESSAGE_FINANCIER_GROUP_INFO
 */
@TableName(value ="SYS_MESSAGE_FINANCIER_GROUP_INFO")
@Data
public class MessageFinancierGroupInfoDO {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long rId;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 系统用户代码
     */
    private String groupCode;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updateTime;


}