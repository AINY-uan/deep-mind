package org.ainy.deepmind;

import org.ainy.deepmind.util.CompressUtil;
import org.junit.Test;

import java.io.IOException;

/**
 * @author AINY_uan
 * @description 压缩测试
 * @date 2020-03-15 19:14
 */
public class CompressTest {

    @Test
    public void ex1() throws IOException {

        CompressUtil.compress("E:/Test", "E:/XXX/");
    }
}
