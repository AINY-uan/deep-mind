package org.ainy.deepmind;

import org.ainy.deepmind.util.AesUtil;
import org.junit.Test;

/**
 * @author 阿拉丁省油的灯
 * @description AES加解密工具类测试
 * @date 2020-07-21 15:32
 */
public class AesUtilTest {

    /**
     * 加密测试
     */
    @Test
    public void ex1() {

        System.out.println(AesUtil.encrypt("my name is null"));
    }

    /**
     * 加密测试
     */
    @Test
    public void ex2() {

        System.out.println(AesUtil.decrypt("kXqzh8xSb2vcY0wF9cObNg=="));
    }
}
