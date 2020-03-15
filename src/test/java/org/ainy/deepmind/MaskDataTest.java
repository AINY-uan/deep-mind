package org.ainy.deepmind;

import org.ainy.deepmind.util.MaskUtil;
import org.junit.Test;

/**
 * @author AINY_uan
 * @description 脱敏测试
 * @date 2020-03-15 14:45
 */
public class MaskDataTest {

    @Test
    public void ex1() {

        System.out.println(MaskUtil.chineseName("张三"));

        System.out.println(MaskUtil.idCardNum("360981199504224156"));

        System.out.println(MaskUtil.fixedPhone("18114043944"));

        System.out.println(MaskUtil.mobilePhone("18114043944"));

        System.out.println(MaskUtil.address("上海市松江区九里亭街道九杜路55弄33号楼403", 10));

        System.out.println(MaskUtil.email("1819341878@qq.com"));

        System.out.println(MaskUtil.bankCard("6212261508002010970"));

        System.out.println(MaskUtil.cnapsCode("12345678912345"));
    }
}
