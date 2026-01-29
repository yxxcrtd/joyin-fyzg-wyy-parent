package com.joyin.fyzg.wyy.vo.genPage;

import com.joyin.fyzg.wyy.entity.genPage.SysGenpageWindowAnswer;
import lombok.Data;

import java.util.List;

/**
 * 答题结果VO类
 */
@Data
public class SysGenpageWindowAnswerVO {

    /**
     * 题库定义表id
     */
    private Long windowId;
    /**
     * 用户id
     */
    private String  userName;

    /**
     * 问题答案
     */
    List<SysGenpageWindowAnswer> sysGenpageWindowAnswerList;


}
