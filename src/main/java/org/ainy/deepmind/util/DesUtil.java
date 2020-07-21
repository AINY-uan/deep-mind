package org.ainy.deepmind.util;

import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * @author YUANDONG
 * @description Des加密工具
 * @date 2019-12-04 14:20
 */
public class DesUtil {

    private static BASE64Encoder base64en = new BASE64Encoder();
    private static BASE64Decoder base64de = new BASE64Decoder();

    public static String enCrypto(String data, String key) throws Exception {

        if (StringUtils.isBlank(data)) {
            return null;
        }
        //DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 从原始密匙创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);
        //Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");
        //用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
        //执行数据并加密
        return base64en.encode(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
    }

    public static String deCrypto(String data, String key) throws Exception {
        if (StringUtils.isBlank(data)) {
            return null;
        }
        //DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 从原始密匙创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);
        //Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");
        //用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        //执行数据并解密
        return new String(cipher.doFinal(base64de.decodeBuffer(data)), StandardCharsets.UTF_8);
    }
}
