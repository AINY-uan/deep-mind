package org.ainy.deepmind.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author 阿拉丁省油的灯
 * @description 获取本机IP地址，兼容Windows和Linux
 * @date 2018-07-12 15:45
 */
public class GetIpAddress {

    private static final String IS_WINDOWS = "windows";

    /**
     * 获取本机IP地址
     *
     * @return 本机IP地址
     */
    public static String getInetAddress() {

        InetAddress inetAddress = null;
        try {
            // 如果是windows操作系统
            if (isWindows()) {
                inetAddress = InetAddress.getLocalHost();
            } else {
                boolean bFindIp = false;
                // 定义一个内容都是NetworkInterface的枚举对象
                Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
                // 如果枚举对象里面还有内容(NetworkInterface)
                while (netInterfaces.hasMoreElements()) {
                    if (bFindIp) {
                        break;
                    }
                    // 获取下一个内容(NetworkInterface)
                    NetworkInterface ni = netInterfaces.nextElement();
                    // ----------特定情况，可以考虑用ni.getName判断
                    // 遍历所有IP
                    Enumeration<InetAddress> ips = ni.getInetAddresses();
                    while (ips.hasMoreElements()) {
                        inetAddress = ips.nextElement();
                        // 属于本地地址
                        if (inetAddress.isSiteLocalAddress()
                                // 不是回环地址
                                && !inetAddress.isLoopbackAddress()
                                // 地址里面没有:号
                                && !inetAddress.getHostAddress().contains(":")) {
                            // 找到了地址
                            bFindIp = true;
                            // 退出循环
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return inetAddress == null ? null : inetAddress.getHostAddress();
    }

    /**
     * 判断操作系统是否是Windows
     *
     * @return true: Windows；false：other
     */
    private static boolean isWindows() {

        boolean isWindows = false;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains(IS_WINDOWS)) {
            isWindows = true;
        }
        return isWindows;
    }
}
