package com.joyin.fyzg.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <br/>
 *
 * @author Administrator
 * @date 2020/12/4 0004 下午 6:15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatTopicVO {
    private String destination;
    private String content;
}
