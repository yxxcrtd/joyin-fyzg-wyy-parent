package com.joyin.fyzg.common;

import com.joyin.fyzg.common.exception.CommonException;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 包装的请求参数Map
 * <br/>
 *
 * @author pengzhen
 * @date 2019/11/8 0008 上午 9:28
 */
public class RequestParamMap extends HashMap {

    public Long getLongValueOfNullable(String paramCode) {
        Long longValue = null;
        if (this.get(paramCode) != null) {
            longValue = Long.valueOf(this.get(paramCode).toString());
        }
        return longValue;
    }

    public Long getLongValueOf(String paramCode) {
        return Long.valueOf(this.getParamValueOf(paramCode));
    }

    public Integer getIntValueOfNullable(String paramCode) {
        Integer longValue = null;
        if (this.get(paramCode) != null) {
            longValue = Integer.valueOf(this.get(paramCode).toString());
        }
        return longValue;
    }

    public Integer getIntValueOf(String paramCode) {
        return Integer.valueOf(this.getParamValueOf(paramCode));
    }

    public Boolean getBooleanValueOfNullable(String paramCode) {
        Boolean booleanValue = null;
        if (this.get(paramCode) != null) {
            booleanValue = Boolean.valueOf(this.get(paramCode).toString());
        }
        return booleanValue;
    }

    public Boolean getBooleanValueOf(String paramCode) {
        return Boolean.valueOf(this.getParamValueOf(paramCode));
    }

    public String getStringValueOf(String paramCode) {
        return this.getParamValueOf(paramCode);
    }


    public String getStringValueOf(String paramCode, String info) {
        return this.getParamValueOf(paramCode, info);
    }

    public String getStringValueOfNullable(String paramCode) {
        return this.getParamValueOfNullable(paramCode);
    }

    public String getStringValueOfNullDefault(String paramCode, String nullDefault) {
        if (nullDefault == null) {
            return getStringValueOfNullable(paramCode);
        }

        return Optional.ofNullable(this.get(paramCode))
                .orElse(nullDefault)
                .toString();
    }

    private String getParamValueOf(String paramCode) {
        return Optional.ofNullable(this.get(paramCode))
                .orElseThrow(() -> new CommonException(MessageFormat.format("参数{0}缺失", paramCode)))
                .toString();
    }

    private String getParamValueOf(String paramCode, String message) {
        return Optional.ofNullable(this.get(paramCode))
                .orElseThrow(() -> new CommonException(message))
                .toString();
    }

    private String getParamValueOfNullable(String paramCode) {
        return Optional.ofNullable(this.get(paramCode))
                .orElse("")
                .toString();
    }

    public <T> T getObject(String paramCode, Class<T> clazz) {
        return (T) this.get(paramCode);
    }

    public <T> List<T> getObjectList(String paramCode, Class<T> clazz) {
        return (List<T>) this.get(paramCode);
    }

    public static RequestParamMap wrap(Map map) {
        RequestParamMap requestParamMap = new RequestParamMap();
        requestParamMap.putAll(map);
        return requestParamMap;
    }
}
