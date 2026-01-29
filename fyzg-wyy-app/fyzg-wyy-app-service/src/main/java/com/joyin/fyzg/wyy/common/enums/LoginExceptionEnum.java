package com.joyin.fyzg.wyy.common.enums;

import com.baomidou.mybatisplus.annotation.TableName;
import com.joyin.fyzg.common.ResponseCode;
import com.joyin.fyzg.common.WithCodeMsgEnum;
import com.joyin.fyzg.common.WithCodeMsgFormatEnum;
import com.joyin.fyzg.common.utils.SpringContextUtils;
import com.joyin.fyzg.config.mybatisplus.TableNameConfig;

import java.text.MessageFormat;
import java.util.Optional;

/**
 * <br/>
 *
 * @author pidong
 * @date 2023/11/17 16:28
 */
public enum LoginExceptionEnum implements WithCodeMsgFormatEnum {

    LOGIN_SAVE_LOG(ResponseCode.ERROR.code, "登录日志记录失败！错误消息如下：【{0}】"){
        @Override
        public String getMsg() {
            return "登录日志记录失败！";
        }
    },
    LOGOUT_SAVE_LOG(ResponseCode.ERROR.code, "登出日志记录失败！错误消息如下：【{0}】"){
        @Override
        public String getMsg() {
            return "登出日志记录失败！";
        }
    },
    ;

    public final String code;
    public final String format;
    public String errorMsg;

    @Override
    public String getFormat() {
        return this.format;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.errorMsg;
    }

    private LoginExceptionEnum(String code, String format) {
        this.code = code;
        this.format = format;
    }

    public <T> WithCodeMsgEnum formatEntity(Class<T> entityClazz, Exception e) {
        String enName = entityClazz.getAnnotation(TableName.class).value();
        String allName = SpringContextUtils.getBean(TableNameConfig.class).getFullNameByEnName(enName);
        String cnName = SpringContextUtils.getBean(TableNameConfig.class).getCnNameByEnName(enName);
        LoginExceptionEnum that = this;
        return new WithCodeMsgEnum() {
            @Override
            public String getCode() {
                return that.getCode();
            }

            @Override
            public String getMsg() {
                return Optional.ofNullable(that.getMsg()).map(msg -> MessageFormat.format(msg, cnName)).orElse(this.getErrorMsg());
            }

            @Override
            public String getErrorMsg() {
                return MessageFormat.format(that.getFormat(), allName, e);
            }
        };
    }
}
