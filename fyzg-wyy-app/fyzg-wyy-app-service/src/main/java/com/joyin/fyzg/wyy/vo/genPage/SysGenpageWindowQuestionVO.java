package com.joyin.fyzg.wyy.vo.genPage;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 题库问题VO类
 */
@Data
public class SysGenpageWindowQuestionVO {

    /**
     * 题库定义表id
     */
    private Long windowId;

    /**
     * 问题编号 后台生成
     */
    private Long questionSeq;

    /**
     * 问题类型：0单选，1多选，3文本
     */
    private String questionType;

    /**
     * 问题文本
     */
    private String question;

    /**
     * 问题答案
     */
    List<WindowQuestionAnswerVO> windowQuestionAnswerVOList;


}
