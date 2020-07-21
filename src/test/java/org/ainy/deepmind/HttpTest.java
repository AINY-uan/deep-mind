package org.ainy.deepmind;

import org.ainy.deepmind.util.HttpUtil;
import org.junit.Test;

/**
 * @author AINY_uan
 * @description Http测试
 * @date 2020-04-17 23:21
 */
public class HttpTest {

    @Test
    public void ex1() throws InterruptedException {

        for (int i = 0; i < 10; i++) {

            System.out.println(HttpUtil.doGet("http://192.168.1.110:8888/demo/user/getInfo?", "id=1"));

            Thread.sleep(5000);
        }
    }
}
