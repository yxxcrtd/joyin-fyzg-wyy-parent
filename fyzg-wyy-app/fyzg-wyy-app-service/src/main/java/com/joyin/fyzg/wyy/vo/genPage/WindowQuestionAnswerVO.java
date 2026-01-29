package com.joyin.fyzg.wyy.vo.genPage;

import lombok.Builder;
import lombok.Data;

/**
 * 题库答案选项VO类
 */
@Data
public class WindowQuestionAnswerVO {

    /**
     * 答案编号 后台生成
     */
    private Long answerSeq;

    /**
     * 答案文本
     */
    private String answer;

    /**
     * 是否正确答案 Y 是， N 否， 空 没有正确答案
     */
    private String answerResult;




}
