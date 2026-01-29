package com.joyin.fyzg.common.exception;

import com.joyin.fyzg.common.WithCodeMsgEnum;
import com.joyin.fyzg.common.WithCodeMsgFormatEnum;

import java.util.Optional;

/**
 * SQL 执行异常类
 * <br/>
 *
 * @author pengzhen
 * @date 2019/7/18 0018 下午 3:45
 */
public class SqlExecutionException extends RuntimeException {

    private final WithCodeMsgEnum type;

    public WithCodeMsgEnum getType() {
        return this.type;
    }

    public SqlExecutionException(String msg) {
        this(msg, null);
    }

    public SqlExecutionException(String msg, Throwable e) {
        this(new WithCodeMsgEnum() {
            @Override
            public String getCode() {
                return "";
            }

            @Override
            public String getMsg() {
                return msg;
            }
        }, e);
    }

    public SqlExecutionException(WithCodeMsgEnum type, Throwable e) {
        super(type.getErrorMsg(), e);
        this.type = type;
        //这里打印异常堆栈消息
        Optional.ofNullable(e).ifPresent(i -> i.printStackTrace());
    }

    public SqlExecutionException(WithCodeMsgFormatEnum type, Object... arguments) {
        this(type.format(arguments), null);
    }

    public SqlExecutionException(WithCodeMsgFormatEnum type, Throwable e, Object... arguments) {
        this(type.format(arguments), e);
    }
}
