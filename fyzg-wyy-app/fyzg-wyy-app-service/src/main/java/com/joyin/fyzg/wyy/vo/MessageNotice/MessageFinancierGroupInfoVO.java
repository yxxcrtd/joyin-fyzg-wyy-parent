package com.joyin.fyzg.wyy.vo.MessageNotice;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;


@Data
public class MessageFinancierGroupInfoVO {
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
     * 管理人代码，名称
     */
    private List<CustFinancierVO> custFinancierVOs;

    /**
     * 系统分组管理人下的所有用户代码
     */
    private List<String> userOCodeList;


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