package org.ainy.deepmind;

import org.ainy.deepmind.util.MapUtil;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;

import java.util.HashMap;

/**
 * @author 阿拉丁省油的灯
 * @description Map工具类测试
 * @date 2020-07-21 12:31
 */
public class MapUtilTest {

    @Test
    public void ex1() throws Exception {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        HashMap<String, String> map = new HashMap<>(16);
        map.put("name", "ainyuan");
        map.put("age", "11");
        map.put("sex", "男");

        stopWatch.stop();

        System.out.println(MapUtil.sortMapByKey(map));

        System.out.println(stopWatch.getTime());

        System.out.println(MapUtil.sortByKey(map, true));

        System.out.println(MapUtil.sortByValue(map, true));
    }
}
