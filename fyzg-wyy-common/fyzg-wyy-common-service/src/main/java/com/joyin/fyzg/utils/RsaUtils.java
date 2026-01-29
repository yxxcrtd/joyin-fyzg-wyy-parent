package com.joyin.fyzg.utils;

import com.joyin.fyzg.utils.rsaencrypt.RSAEncrypt;
import com.joyin.fyzg.utils.rsaencrypt.RSAKeyGenerator;
import com.joyin.fyzg.utils.rsaencrypt.RSAValidator;
import com.joyin.fyzg.vo.DecryptVO;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RSA加解密Utils
 * <br/>
 *
 * @author pidong
 * @date 2021/4/20 10:18
 */
public class RsaUtils {
    private final static String PRE_FIX = "SECRETKEY:";

    public final static String RSA_REDIS_KEY = "WFW_RSA_KEY";

    /**
     * 生成公钥,私钥,模，并放入session中
     * @author pidong
     * @since 2019年3月15日, 下午3:54:17
     */
    public static String geneEncryptInfo() {
        RSAKeyGenerator generator = new RSAKeyGenerator();
        byte[] privateKeyEncoded = generator.getPrivateKeyEncoded();
        byte[] publicKeyEncoded = generator.getPublicKeyEncoded();

        /*KeyManager keyMan = new RSAValidator();
        RSAPublicKey rsaPubKey = (RSAPublicKey) keyMan.restorePublicKey(Base64.decodeBase64(publicKeyEncoded));
        BigInteger publicExponent = rsaPubKey.getPublicExponent();
        BigInteger modulus = rsaPubKey.getModulus();*/

        Map<String, String> keyPair = new HashMap<String, String>();
        //公钥 私钥
        String pubKeyStr = "", priKeyStr = "";
        try {
            pubKeyStr = new String(publicKeyEncoded, "utf-8");
            priKeyStr = new String(privateKeyEncoded, "utf-8");

            keyPair.put(pubKeyStr, priKeyStr);
            /*request.setAttribute("pubKey", pubKeyStr);
            request.setAttribute("modulus", modulus.toString(16));
            request.setAttribute("pubExep", publicExponent.toString(16));*/
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String key = PRE_FIX + pubKeyStr;
        System.out.println(JedisUtil.HASH.hdel(key));
        JedisUtil.HASH.hmset(key, keyPair);
        JedisUtil.KEYS.expired(key,60*60*2);

        return pubKeyStr;
    }

    public static void main(String[] args) {
        RSAKeyGenerator generator = new RSAKeyGenerator();
        byte[] privateKeyEncoded = generator.getPrivateKeyEncoded();
        System.out.println("====================> private" + new String(privateKeyEncoded));
        byte[] publicKeyEncoded = generator.getPublicKeyEncoded();
        System.out.println("====================> public" + new String(publicKeyEncoded));
    }

    /**
     * 数据解密
     * <br/>
     * @param ciphertext	
     * @param pubKey
     * @return com.joyin.fyzg.vo.ResultEntity
     * @author pidong
     * @date 2021/4/20 10:42
     */
    public static DecryptVO decryptData(String ciphertext, String pubKey) {
        DecryptVO result = new DecryptVO();
        String key = PRE_FIX + pubKey;
        List<String> keyInfos = JedisUtil.HASH.hmget(key, pubKey);
        if (keyInfos.size() == 0) {
            result.setStatus(false);
        } else {
            try {
                String priKey = keyInfos.get(0);
                if(priKey == null){
                    result.setStatus(false);
                    result.setReason("登录页面已失效，请刷新页面");
                    return result;
                }
                RSAEncrypt validator = new RSAValidator();
                byte[] dencrypt = validator.dencrypt(Base64.decodeBase64(priKey), Base64.decodeBase64(ciphertext));
                result.setRPassw(new String(dencrypt, "utf-8"));
                result.setStatus(true);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 数据解密
     * <br/>
     * @param ciphertext
     * @param pubKey
     * @return com.joyin.fyzg.vo.ResultEntity
     * @author pidong
     * @date 2021/4/20 10:42
     */
    public static String decryptDataStr(String ciphertext, String pubKey) {
        String key = PRE_FIX + pubKey;
        List<String> keyInfos = JedisUtil.HASH.hmget(key,pubKey);
        if(keyInfos.size() == 0){
            return "";
        }

        String plaintext = "";
        try {
            String priKey = keyInfos.get(0);
            RSAEncrypt validator = new RSAValidator();
            byte[] dencrypt = validator.dencrypt(Base64.decodeBase64(priKey), Base64.decodeBase64(ciphertext));
            plaintext = new String(dencrypt, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            plaintext = "";
        }

        return plaintext;
    }
}
