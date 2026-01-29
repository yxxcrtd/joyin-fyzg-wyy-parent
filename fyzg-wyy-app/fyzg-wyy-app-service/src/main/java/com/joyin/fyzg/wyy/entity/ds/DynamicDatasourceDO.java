package com.joyin.fyzg.wyy.entity.ds;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 表SYS_EVENT_CHANNEL_RDB的实体类
 * @author 工具生成
 * @version 1.0
 * @since 
 */
@Data
@TableName("SYS_DYNAMIC_DATASOURCE")
public class DynamicDatasourceDO {

    /** R_ID : 数据链接ID(内部) */
    @TableId(value = "R_ID", type = IdType.UUID)
    private String rId;
    
    
    /** CHANNEL_NAME : 数据库名称 */
    private String channelName;
    
    
    /** INSERT_TIME : 新增时间 */
    private String insertTime;
    
    
    /** INSERT_USER : 新增用户 */
    private String insertUser;
    
    
    /** DRIVER_CLASS : 驱动类 */
    private String driverClass;
    
    
    /** USERNAME : 访问用户 */
    private String username;
    
    
    /** PASSWORD : 访问密码 */
    private String password;
    
    
    /** TYPE : 数据库类型：ORACLE，MYSQL，DM-达梦(DaMeng)，VASTBASE-海量(VastBase)，GAUSS-华为(Gasuss)，PG-其它(PG) */
    private String type;
    
    
    /** URL : 服务器地址 */
    private String url;
    
	
}
