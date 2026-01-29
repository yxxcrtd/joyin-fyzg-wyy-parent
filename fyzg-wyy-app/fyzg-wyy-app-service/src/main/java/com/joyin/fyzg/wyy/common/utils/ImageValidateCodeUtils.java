package com.joyin.fyzg.wyy.common.utils;

import com.joyin.fyzg.utils.VerifyCodeUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * <br/>
 *
 * @author pidong
 * @date 2021/4/22 9:19
 */
public class ImageValidateCodeUtils {
    public static String getCode() {
        //随机生成4位验证码
        String code = VerifyCodeUtils.generateVerifyCode(4).toLowerCase();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Object obj = request.getSession().getAttribute("validateCode"); // 原始数据
        if (null != obj) {
            request.getSession().removeAttribute("validateCode");
        }
        request.getSession().setAttribute("validateCode", code);

        //输出到图片
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        try {
            VerifyCodeUtils.outputImage(100, 50, data, code);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //把图片加密返回
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data.toByteArray());
    }

    /**
     * 验证码验证
     * <br/>
     * @param verifyCode	
     * @return boolean
     * @author pidong
     * @date 2021/4/22 10:44
     */
    public static boolean chkVerifyCode(String verifyCode) {
        boolean rtFlag = false;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Object obj = request.getSession(false).getAttribute("validateCode"); // 原始数据
        if (null != obj) {
            rtFlag = obj.toString().toLowerCase().equals(verifyCode.toLowerCase());
            request.getSession(false).removeAttribute("validateCode");
        }
        return rtFlag;
    }
}
