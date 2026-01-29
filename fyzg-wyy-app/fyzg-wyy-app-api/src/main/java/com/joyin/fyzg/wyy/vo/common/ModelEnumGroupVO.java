package com.joyin.fyzg.wyy.vo.common;

import lombok.Data;

import java.util.List;

@Data
public class ModelEnumGroupVO {

    /** ENUM_CODE : 常量代码 */
    private String enumCode;


    /** ENUM_LABEL : 枚举显示值 */
    private List<ModelEnumItemVO> modelEnumItemDOList;

}
