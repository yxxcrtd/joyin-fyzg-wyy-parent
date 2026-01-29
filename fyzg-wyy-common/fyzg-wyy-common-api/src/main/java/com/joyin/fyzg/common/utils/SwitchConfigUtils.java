package com.joyin.fyzg.common.utils;

import com.joyin.fyzg.common.config.SwitchConfig;


public class SwitchConfigUtils {

    public static boolean isShowErrorMsg() {
        //return true;
        return SpringContextUtils.getBean(SwitchConfig.class).showErrorMsg;
    }
}
