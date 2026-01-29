package com.joyin.fyzg.wyy.utils;

import com.joyin.fyzg.constant.SessionConstant;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.utils.DateUtil8;
import com.joyin.fyzg.utils.JedisUtil;

import java.util.Map;

public class PlaceHolderUtils {

    public static final String SYS_DATE_CURRENT_DATE = "SYS_DATE-CURRENT_DATE";

    public static void dealWithSession4Sql(String userCode,Map<String, Object> paramMap){
        Map<String, String> sessions4User = JedisUtil.HASH.hgetAll(SessionConstant.SESSION_USER_INFO_PRE + userCode);
        if (sessions4User != null){
            paramMap.putAll(sessions4User);
            paramMap.put(PlaceHolderUtils.SYS_DATE_CURRENT_DATE,DateUtil8.getNowDate_EN());
        }
    }

}
