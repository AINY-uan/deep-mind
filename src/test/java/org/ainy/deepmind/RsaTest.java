package org.ainy.deepmind;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.ainy.deepmind.util.RsaUtil;
import org.junit.Test;

/**
 * @author AINY_uan
 * @description RSA加密测试
 * @date 2020-03-15 14:35
 */
public class RsaTest {

    @Test
    public void ex1() throws Exception {

        String filepath = "E:/";

        //生成公钥和私钥文件
        RsaUtil.genKeyPair(filepath);

        System.out.println("--------------公钥加密私钥解密过程-------------------");
        String plainText = "postgres";
        //公钥加密过程
        byte[] cipherData = RsaUtil.encrypt(RsaUtil.loadPublicKeyByStr(RsaUtil.loadPublicKeyByFile(filepath)), plainText.getBytes());
        String cipher = Base64.encode(cipherData);
        //私钥解密过程
        byte[] res = RsaUtil.decrypt(RsaUtil.loadPrivateKeyByStr(RsaUtil.loadPrivateKeyByFile(filepath)), Base64.decode(cipher));
        assert res != null;
        String restr = new String(res);
        System.out.println("原文：" + plainText);
        System.out.println("加密：" + cipher);
        System.out.println("解密：" + restr);
        System.out.println();

        System.out.println("--------------私钥加密公钥解密过程-------------------");
        plainText = "postgres";
        //私钥加密过程
        cipherData = RsaUtil.encrypt(RsaUtil.loadPrivateKeyByStr(RsaUtil.loadPrivateKeyByFile(filepath)), plainText.getBytes());
        cipher = Base64.encode(cipherData);
        //公钥解密过程
        res = RsaUtil.decrypt(RsaUtil.loadPublicKeyByStr(RsaUtil.loadPublicKeyByFile(filepath)), Base64.decode(cipher));
        assert res != null;
        restr = new String(res);
        System.out.println("原文：" + plainText);
        System.out.println("加密：" + cipher);
        System.out.println("解密：" + restr);
        System.out.println();
    }
}
