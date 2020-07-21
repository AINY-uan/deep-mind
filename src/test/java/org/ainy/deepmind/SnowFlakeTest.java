package org.ainy.deepmind;

import org.ainy.deepmind.util.SnowFlake;
import org.junit.Test;

/**
 * @author 阿拉丁省油的灯
 * @description 雪花算法测试
 * @date 2020-07-11 22:37
 */
public class SnowFlakeTest {

    @Test
    public void ex1() {

        SnowFlake snowFlake = new SnowFlake(2, 3);
//        for (int i = 0; i < (1 << 12); i++) {
        System.out.println(snowFlake.nextId());
//        }
    }
}
