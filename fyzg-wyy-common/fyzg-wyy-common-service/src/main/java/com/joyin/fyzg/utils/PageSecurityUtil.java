package com.joyin.fyzg.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Base64;

@Slf4j
public class PageSecurityUtil {
    public static final String SECURITY_COL = "securityKey";
    public static final String SECRET_KEY_STR = "AB1234cd";

    /**
     * 生成页面安全参数
     * @param plaintext
     * @return
     */
    public static String genePageToken(String plaintext) {
        log.info("plaintext=" + plaintext);

        //生成密文
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodeStr = bcryptPasswordEncoder.encode(plaintext + SECRET_KEY_STR);
        String encode2Str = Base64.getUrlEncoder().encodeToString(encodeStr.getBytes());
        log.info("encodeStr=" + encodeStr);
        log.info("encode2Str=" + encode2Str);

        return encode2Str;
    }

    /**
     * 校验页面安全参数
     * @param plaintext
     * @param pageTokenStr
     * @return
     */
    public static Boolean checkPageToken(String plaintext, String pageTokenStr) {
        boolean matches = false;

        try {
            log.info("plaintext=" + plaintext);

            //验证
            BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
            pageTokenStr = new String(Base64.getUrlDecoder().decode(pageTokenStr));
            matches = bcryptPasswordEncoder.matches(plaintext + SECRET_KEY_STR, pageTokenStr);
            log.info("解密matches=" + matches);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("checkPageToken failed!!! <br/>" + e.getMessage());
        }

        return matches;
    }

}
