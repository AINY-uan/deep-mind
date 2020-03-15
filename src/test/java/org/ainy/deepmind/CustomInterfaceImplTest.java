package org.ainy.deepmind;

import org.ainy.deepmind.other.CustomInterface;
import org.junit.Test;

/**
 * @author AINY_uan
 * @description JDK8新特性实现类
 * @date 2020-03-15 00:49
 */
public class CustomInterfaceImplTest implements CustomInterface {

    @Override
    public void test() {

    }

    @Test
    public void ex1() {

        CustomInterfaceImplTest ex = new CustomInterfaceImplTest();
        ex.xxx();
    }
}
