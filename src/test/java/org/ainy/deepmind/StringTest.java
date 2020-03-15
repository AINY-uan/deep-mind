package org.ainy.deepmind;

import org.junit.Test;

import static org.ainy.deepmind.util.StringUtil.*;

/**
 * @author AINY_uan
 * @description 字符串测试
 * @date 2020-03-15 14:17
 */
public class StringTest {

    @Test
    public void ex1() {

        System.out.println(str2HexStr("MIG6MGECADAAMFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAE6QvJGX3Z/dHPuYudXdsT5wAmsdDAmIswUHe9ZyvV+ujobuIn9DUSZrUtc2MQ+m+8aaHeomWYS2hlBVm88SM9gKAAMAoGCCqBHM9VAYN1A0kAMEYCIQDEAUDzLJ7UrSCsKBlIpcCeoON9Onsv6mE/353/7St1jQIhAKsQJRVsWYhIBZr3lTpFaQfo4HoU2fFtmYrPXfzx4pdj="));
        System.out.println(hexStr2Str("3130"));
        System.out.println(binStr2HexStr("0111000000100000000001001000000000000000110000000000000000010111"));
    }
}
