package com.joyin.fyzg.wyy.vo.MessageNotice;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 用户分组信息表excel导入vo
 */
@Data
public class MessageFinancierGroupInfoImportVO {
    /**
     * 分组名称
     */
    @NotNull(message = "分组名称不能为空")
    @ExcelProperty(index = 0, value = "分组名称")
    private String groupName;

    /**
     * 系统用户代码
     */
    @NotNull(message = "管理人代码不能为空")
    @ExcelProperty(index = 1, value = "管理人代码")
    private String custOCode;

    /**
     * 系统用户名称
     */
    @ExcelProperty(index = 2, value = "管理人名称")
    private String custOName;

}