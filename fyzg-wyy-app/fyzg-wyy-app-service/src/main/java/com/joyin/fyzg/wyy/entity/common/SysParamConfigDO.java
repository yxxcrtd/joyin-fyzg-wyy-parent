package com.joyin.fyzg.wyy.entity.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("SYS_PARAM_CONFIG")
public class SysParamConfigDO {
    @TableId(value = "R_ID", type = IdType.UUID)
    private Long rId;
    private String paramKey;
    private String paramName;
    private String paramValue;
    private String type;
    private String description;
    private String status;
    private Date createTime;
    private Date updateTime;
}
