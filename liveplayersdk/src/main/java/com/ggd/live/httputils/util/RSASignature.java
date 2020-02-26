package com.ggd.live.httputils.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA签名
 */
public class RSASignature {

    /**
     * 签名算法
     */
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    public static String encryptBASE64(byte[] key) {
        return Base64.encode(key);
    }

    /**
     * RSA签名
     *
     * @param content    待签名数据
     * @param privateKey 商户私钥
     * @param encode     字符集编码
     * @return 签名值
     */
    public static String sign(String content, String privateKey, String encode) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));

            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update(content.getBytes(encode));

            byte[] signed = signature.sign();

            return Base64.encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String sign(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content.getBytes());
            byte[] signed = signature.sign();
            return Base64.encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA验签名检查
     *
     * @param content   待签名数据
     * @param sign      签名值
     * @param publicKey 分配给开发商公钥
     * @param encode    字符集编码
     * @return 布尔值
     */
    public static boolean doCheck(String content, String sign, String publicKey, String encode) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decode(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));


            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);

            signature.initVerify(pubKey);
            signature.update(content.getBytes(encode));

            boolean bverify = signature.verify(Base64.decode(sign));
            return bverify;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean doCheck(String content, String sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decode(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);

            signature.initVerify(pubKey);
            signature.update(content.getBytes());

            boolean bverify = signature.verify(Base64.decode(sign));
            return bverify;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Key> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        Map<String, Key> keyMap = new HashMap(2);
        keyMap.put(PUBLIC_KEY, keyPair.getPublic());// 公钥
        keyMap.put(PRIVATE_KEY, keyPair.getPrivate());// 私钥
        return keyMap;
    }

    /**
     * 取得私钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Key> keyMap) throws Exception {
        Key key = keyMap.get(PRIVATE_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 取得公钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Key> keyMap) throws Exception {
        Key key = keyMap.get(PUBLIC_KEY);
        return encryptBASE64(key.getEncoded());
    }

}
