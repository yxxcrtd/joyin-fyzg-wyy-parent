package com.joyin.fyzg.wyy.entity.calendar;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
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
@KeySequence(value = "S_SYS_DESKTOP_CALENDAR")
@TableName("SYS_DESKTOP_CALENDAR")
public class CalendarDO {

    /** R_ID : 数据编号，自增长主键 */
    @TableId(value = "R_ID", type = IdType.INPUT)
    private Long rId;

    private String busCode;

    /** CAL_O_CODE : 对象代码 */
    private String calOCode;
    
    
    /** CAL_O_NAME : 对象名称 */
    private String calOName;


    private String comtCode;


    /** CAL_TYPE: 日历类型，标识是其他日历还是迷你日历，MINI代表迷你日历，空串或null代表其他日历你 */
    private String calType;


    private String comType;


    private String countSubQuerySql;


    private String countWithQuerySql;


    private Integer listSort;


    private String penetrate;


    private String rcolor;


    private String resume;


    private String tableDateSql;


    /** CFG_JSON: JSON配置 */
    private String cfgJson;


    private int dFlag;

    /**
     * DATA_SOURCE : 数据库链接
     */
    private String dataSource;



}
