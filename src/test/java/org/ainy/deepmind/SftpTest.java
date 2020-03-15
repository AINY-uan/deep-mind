package org.ainy.deepmind;

import org.ainy.deepmind.util.SftpUtil;
import org.junit.Test;

/**
 * @author AINY_uan
 * @description Sftp测试
 * @date 2020-03-15 19:53
 */
public class SftpTest {

    /**
     * 下载远程Linux主机的文件
     */
    @Test
    public void downloadRemoteFiles() {

        SftpUtil sftp = SftpUtil.getInstance("47.101.69.50", 22, "root", "PalmapHisTest");

        sftp.downloadRemoteFiles("/opt/HisTest/0A8M01_MGSRMYY/v1.2/his-ai/conf/", "e:/");
    }

    /**
     * 上传文件至远程Linux服务器
     */
    @Test
    public void uploadFileToRemote() {

        SftpUtil sftp = SftpUtil.getInstance("47.101.69.50", 22, "root", "PalmapHisTest");

        sftp.uploadFileToRemote("e:/20200314183430.jpg", "/opt/AINY_uan", null);
    }
}
