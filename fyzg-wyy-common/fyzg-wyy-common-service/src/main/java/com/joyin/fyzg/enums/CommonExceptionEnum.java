package com.joyin.fyzg.enums;

import com.joyin.fyzg.common.ResponseCode;
import com.joyin.fyzg.common.WithCodeMsgFormatEnum;

/**
 * 功能列表解析器服务的消息定义
 * <br/>
 *
 * @author pengzhen
 * @date 2019/7/24 0024 上午 9:17
 */
public enum CommonExceptionEnum implements WithCodeMsgFormatEnum {
    JSON_OBJ2JSON(ResponseCode.ERROR.code, "Json序列化异常，OBJ2JSON, 异常的对象为:【{0}】, 异常的堆栈为:【{1}】") {
        @Override
        public String getMsg() {
            return "Json序列化异常!";
        }
    },
    JSON_JSON2POJO(ResponseCode.ERROR.code, "Json反序列化异常，JSON2POJO, 异常的Json为:【{0}】, 异常的堆栈为:【{1}】") {
        @Override
        public String getMsg() {
            return "Json反序列化异常!";
        }
    },
    JSON_JSON2MAP(ResponseCode.ERROR.code, "Json反序列化异常，JSON2MAP, 异常的Json为:【{0}】, 异常的堆栈为:【{1}】") {
        @Override
        public String getMsg() {
            return "Json反序列化异常!";
        }
    },
    JSON_JSON2LIST(ResponseCode.ERROR.code, "Json反序列化异常，JSON2LIST, 异常的Json为:【{0}】, 异常的堆栈为:【{1}】") {
        @Override
        public String getMsg() {
            return "Json反序列化异常!";
        }
    },
    SQL_EXECUTE_FAIL(ResponseCode.ERROR.code, "SQL执行出错, 出错的SQL为:【{0}】, 异常的堆栈为:【{1}】") {
        @Override
        public String getMsg() {
            return "SQL执行出错!";
        }
    },
    SQL_UPDATE_CAS_FAIL(ResponseCode.ERROR.code, "修改数据异常， 数据版本号不一致， 出错SQL为:【{0}】") {
        @Override
        public String getMsg() {
            return "对不起，数据不能提交。因为原数据变化，请刷新页面后重新操作并提交!";
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

    private CommonExceptionEnum(String code, String format) {
        this.code = code;
        this.format = format;
    }
}
