package com.joyin.fyzg.wyy.vo.genPage;

import lombok.Data;

import java.util.List;

/**
 * 题库定义VO类
 */
@Data
public class SysGenpageWindowDefineVO {

    /**
     * 题库定义表id 修改填写，新增不填写
     */
    private Long id;

    /**
     * 题库标题
     */
    private String title;

    List<SysGenpageWindowQuestionVO> sysGenpageWindowQuestionVOList;

}
