package com.joyin.fyzg.wyy.entity.calendar;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 日历提醒表的实体类
 * @author 工具生成
 * @version 1.0
 * @since 
 */
@Data
@TableName("SYS_DESKTOP_CALENDAR_FINISHED")
public class CalendarFinishedDO {
    
    /** PRIVATE_ID : 主动提醒私有ID */
    @TableId(type = IdType.UUID)
    private String privateId;


    /** F_DATE : 用户点击确认的日期YYYY-MM-DD */
    private String fDate;


    /** F_TIME : 用户点击确认的时间HH24:MI:SS */
    private String fTime;


    /** F_USER : 哪个用户点击的USER_O_CODE */
    private String fUser;

    /** CAL_O_CODE : 日历规则code */
    private String calOCode;

    @TableField(exist = false)
    private Long cfgId;

}
