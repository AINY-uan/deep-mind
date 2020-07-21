package org.ainy.deepmind;

import org.ainy.deepmind.util.LocationUtil;
import org.junit.Test;

/**
 * @author 阿拉丁省油的灯
 * @description 经纬度距离计算工具测试类
 * @date 2020-07-11 23:36
 */
public class LocationUtilTest {

    @Test
    public void ex1() {

        double distance = LocationUtil.getDistance(121.814838, 31.158464,
                121.585447, 31.373789);
        System.out.println("距离" + distance + "米");
    }
}
