package org.ainy.deepmind.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author 阿拉丁费油的灯
 * @description AES加解密工具
 * @date 2020-05-21 16:47
 */
@Slf4j
public class AesUtil {

    static byte[] key = "w@#$4@#$s^&3*&^4".getBytes();
    final static String ALGORITHM = "AES";

    /**
     * 加密
     *
     * @param data 待加密的数据
     * @return 加密后的字符串
     */
    public static String encrypt(String data) {

        byte[] dataToSend = data.getBytes();
        Cipher c = null;
        try {
            c = Cipher.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            log.error("加密出现错误", e);
        }
        SecretKeySpec k = new SecretKeySpec(key, ALGORITHM);
        try {
            assert c != null;
            c.init(Cipher.ENCRYPT_MODE, k);
        } catch (InvalidKeyException e) {
            log.error("加密出现错误", e);
        }
        byte[] encryptedData = "".getBytes();
        try {
            encryptedData = c.doFinal(dataToSend);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            log.error("加密出现错误", e);
        }
        byte[] encryptedByteValue = Base64.getEncoder().encode(encryptedData);
        return new String(encryptedByteValue);
    }

    public static String decrypt(String data) {

        byte[] encryptedData = Base64.getDecoder().decode(data);
        Cipher c = null;
        try {
            c = Cipher.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            log.error("解密出现错误", e);
        }
        SecretKeySpec k =
                new SecretKeySpec(key, ALGORITHM);
        try {
            assert c != null;
            c.init(Cipher.DECRYPT_MODE, k);
        } catch (InvalidKeyException e) {
            log.error("解密出现错误", e);
        }
        byte[] decrypted = null;
        try {
            decrypted = c.doFinal(encryptedData);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            log.error("解密出现错误", e);
        }
        assert decrypted != null;
        return new String(decrypted);
    }
}
