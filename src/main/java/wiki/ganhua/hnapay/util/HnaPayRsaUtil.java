package wiki.ganhua.hnapay.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import wiki.ganhua.hnapay.config.HnaPayConfig;
import wiki.ganhua.hnapay.exception.HnaPayCryptoException;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

/**
 * @author ganhua
 */
public class HnaPayRsaUtil {

    /**
     * 数字签名，密钥算法
     */
    public static final String RSA_ALGORITHM = "RSA";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * 定义签名算法
     */
    private final static String KEY_RSA_SIGNATURE = HnaPayConfig.SignatureAlgorithm.SHA512withRSA.name();

    public static final String RSA_PADDING = "RSA/ECB/PKCS1Padding";

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       加密数据
     * @param privateKey 私钥
     */
    public static String sign(byte[] data, String privateKey) {
        return signature(data, privateKey, KEY_RSA_SIGNATURE);
    }

    public static String sign(byte[] data, String privateKey, String signatureKey) {
        return signature(data, privateKey, signatureKey);
    }

    /**
     * 校验数字签名
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return 校验成功返回true，失败返回false
     */
    public static boolean verify(byte[] data, String publicKey, String sign) {
        return verifyData(data, publicKey, sign, KEY_RSA_SIGNATURE);
    }

    public static boolean verify(byte[] data, String publicKey, String sign, String signatureKey) {
        return verifyData(data, publicKey, sign, signatureKey);
    }

    private static String signature(byte[] data, String privateKey, String signatureKey) {
        String str = "";
        try {
            PrivateKey key = getPrivateKey(privateKey);
            // 用私钥对信息生成数字签名
            Signature signature = Signature.getInstance(signatureKey);
            signature.initSign(key);
            signature.update(data);
            str = Base64.encode(signature.sign());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    private static boolean verifyData(byte[] data, String publicKey, String sign, String signatureKey) {
        boolean flag;
        try {
            PublicKey key = getPublicKey(publicKey);
            // 用公钥验证数字签名
            Signature signature = Signature.getInstance(signatureKey);
            signature.initVerify(key);
            signature.update(data);
            flag = signature.verify(Base64.decode(sign));
        } catch (Exception e) {
            return false;
        }
        return flag;
    }

    /**
     * 公钥分段加密
     *
     * @param base64PublicKey base64 公钥
     * @param data            待加密的内容
     * @return 加密后的内容
     */
    public static String encryptSliceToBase64(String base64PublicKey, String data) {
        if (StrUtil.isBlank(data)) {
            return null;
        }
        try {
            PublicKey publicKey = getPublicKey(base64PublicKey);
            byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
            Cipher cipher = Cipher.getInstance(RSA_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            int inputLen = bytes.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(bytes, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(bytes, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return Base64.encode(decryptedData);
        } catch (Exception e) {
            throw new HnaPayCryptoException();
        }
    }

    /**
     * 得到公钥
     *
     * @param base64PubKey 密钥字符串（经过base64编码）
     * @return PublicKey
     */
    public static PublicKey getPublicKey(String base64PubKey) {
        Objects.requireNonNull(base64PubKey, "base64 public key is null.");
        byte[] keyBytes = Base64.decode(base64PubKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new HnaPayCryptoException();
        }
    }

    /**
     * 得到私钥
     *
     * @param base64PriKey 密钥字符串（经过base64编码）
     * @return PrivateKey
     */
    public static PrivateKey getPrivateKey(String base64PriKey) {
        Objects.requireNonNull(base64PriKey, "base64 private key is null.");
        byte[] keyBytes = Base64.decode(base64PriKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new HnaPayCryptoException();
        }
    }

}
