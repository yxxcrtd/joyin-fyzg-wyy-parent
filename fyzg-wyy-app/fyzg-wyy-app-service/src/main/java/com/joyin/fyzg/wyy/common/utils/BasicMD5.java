package com.joyin.fyzg.wyy.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 加密基础实现
 */
public class BasicMD5 {


    /**
     * MD5加密
     *
     * @param pass
     * @return
     */
    public static String MD5(String pass) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };

        try {
            byte[] strTemp = pass.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return pass;
        }

    }

    /**
     * 基础 MD5 加密方法
     */
    public static String md5Basic(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            return bytesToHex(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 算法不可用", e);
        }
    }

    /**
     * 字节数组转十六进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * 带字符集支持的 MD5
     */
    public static String md5WithCharset(String input, String charset) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes(charset));
            return bytesToHex(messageDigest);
        } catch (Exception e) {
            throw new RuntimeException("MD5 加密失败", e);
        }
    }

    public static void main(String[] args) {
        String testString = "Hello World";

        System.out.println("=== 基础 MD5 加密示例 ===");
        System.out.println("原始字符串: " + testString);
        System.out.println("MD5 加密结果: " + md5Basic(testString));
        System.out.println("UTF-8 MD5: " + md5WithCharset(testString, "UTF-8"));
        System.out.println("GBK MD5: " + md5WithCharset(testString, "GBK"));

        // 测试空字符串和 null
        System.out.println("空字符串 MD5: " + md5Basic(""));
        System.out.println("null MD5: " + md5Basic(null));
    }
}
