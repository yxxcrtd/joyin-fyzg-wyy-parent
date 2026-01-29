package com.joyin.fyzg.config.logback.encoder;


import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import com.joyin.fyzg.config.logback.PolicyEnum;

import java.text.MessageFormat;

/**
 * 日志规范
 * <br/>
 *
 * @author pidong
 * @date 2022/3/10 14:54
 */
public class CommonPatternLayoutEncoder extends PatternLayoutEncoder {
    //0:message最大长度（超出则截取），1:正则表达式，2:policy，3:查找深度（超过深度后停止正则匹配）
    protected String PATTERN_MSG = " %m'{'{0},{1},{2},{3}'}'%n";

    protected String regex = "-";//匹配的正则表达式，如果此值为null或者"-",那么policy、deep参数都将无效

    protected int maxLength = 2048;//单条消息的最大长度，主要是message

    protected String policy = "replace";//如果匹配成功，字符串的策略。

    protected int depth = 128;

//    protected static final String PHONE_REGEX = "'1[34578]\\d{9}$?'";//手机号，11位数字，并且前后位不再是数字。
    //手机号|ip|邮箱+网址
    protected static final String PHONE_REGEX = "'((1[3-9]\\d{9}))|(([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})|((([A-Za-z]{3,9}:(?:\\/\\/)?)(?:[-;:&=\\+\\$,\\w]+@)?[A-Za-z0-9.-]+(:[0-9]+)?|(?:www.|[-;:&=\\+\\$,\\w]+@)[A-Za-z0-9.-]+)((?:\\/[\\+~%\\/.\\w-_]*)?\\??(?:[-\\+=&;%@.\\w_]*)#?(?:[\\w]*))?)'";//手机号，11位数字，并且前后位不再是数字。

    @Override
    public void start() {
        regex = PHONE_REGEX;
        String pattern = getPattern();
        if (pattern != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(pattern);
            if (PolicyEnum.codeOf(policy) == null) {
                policy = "-";
            }

            if (maxLength < 0 || maxLength > 10240) {
                maxLength = 2048;
            }



            sb.append(MessageFormat.format(PATTERN_MSG, String.valueOf(maxLength), PHONE_REGEX, policy, String.valueOf(depth)));
            setPattern(sb.toString());
        }
        super.start();
    }


    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }


    @Override
    public String getPattern() {
        return super.getPattern();
    }

    @Override
    public void setPattern(String pattern) {
        super.setPattern(pattern);
    }


}
