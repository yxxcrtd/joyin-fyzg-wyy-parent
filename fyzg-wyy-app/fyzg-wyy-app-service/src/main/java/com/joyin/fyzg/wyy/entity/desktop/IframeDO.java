package com.joyin.fyzg.wyy.entity.desktop;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表SYS_DESKTOP_IFRAME的实体类
 * @author 工具生成
 * @version 1.0
 * @since 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("SYS_DESKTOP_IFRAME")
public class IframeDO {
    
    /** R_ID : 数据编号，自增长主键 */
    @TableId(value = "R_ID", type = IdType.UUID)
    private String rId;
    
    
    /** NAME : 容器名称 */
    private String name;
    
    
    /** URL : 容器地址 */
    private String url;
	
}
