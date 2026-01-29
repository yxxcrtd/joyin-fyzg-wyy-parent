package com.joyin.fyzg.wyy.vo.genPage;

import lombok.Data;

/**
 * 内页配置汇总信息
 */
@Data
public class SysGenpageCollectInfoVO {


    /**
     * 描述信息
     */
    private String collectDesp;

    /**
     * sql查询结果
     */
    private Object collectSql;

}