package com.joyin.fyzg.wyy.entity.microApplication;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 表SYS_MICRO_APPLICATION的实体类
 * @author 工具生成
 * @version 1.0
 * @since 
 */
@Data
@TableName("SYS_MICRO_APPLICATION")
public class MicroApplicationDO {
    
    /** R_ID : 数据编号，自增长主键 */
    @TableId(value = "R_ID", type = IdType.UUID)
    private String rId;
    
    
    /** NAME : 子应用名称 */
    private String name;
    
    
    /** CODE : 子应用代码 */
    private String code;
    
    
    /** URL : 子应用地址 */
    private String url;
    
    
    /** ENABLED : 是否开启，1-是；0-否 */
    private String enabled;


    /** PATTERN : 模式，保活模式 alive 生命周期模式 lifeCycle 重建模式 rebuild */
    private String pattern;
    
    
    /** REMARK : 备注 */
    private String remark;
    
	
}
