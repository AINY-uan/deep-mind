package org.ainy.deepmind.util;

import ch.ethz.ssh2.*;
import lombok.Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author 阿拉丁省油的灯
 * @date 2019-04-14 12:22
 * @description 获取远程服务器文件
 */
@Data
public class SftpUtil {

    private static SftpUtil instance;

    /**
     * 远程主机IP
     */
    private String ip;
    /**
     * 远程主机登录端口
     */
    private int port;
    /**
     * 远程主机用户名
     */
    private String userName;
    /**
     * 远程主机登录密码
     */
    private String password;

    /**
     * 私有化默认构造函数 实例化对象只能通过getInstance
     */
    private SftpUtil() {

    }

    /**
     * @param ip       服务器IP地址
     * @param port     服务器端口
     * @param userName 用户名
     * @param password 密码
     */
    private SftpUtil(String ip, int port, String userName, String password) {
        this.ip = ip;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    /**
     * 单例模式 懒汉式 线程安全
     *
     * @return SCPUtil实例
     */
    public static SftpUtil getInstance() {

        if (null == instance) {
            synchronized (SftpUtil.class) {
                if (null == instance) {
                    instance = new SftpUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化远程服务器
     *
     * @param ip       服务器IP地址
     * @param port     服务器端口
     * @param userName 用户名
     * @param password 密码
     * @return SCPUtil实例
     */
    public static SftpUtil getInstance(String ip, int port, String userName, String password) {

        if (null == instance) {
            synchronized (SftpUtil.class) {
                if (null == instance) {
                    instance = new SftpUtil(ip, port, userName, password);
                }
            }
        }
        return instance;
    }

    /**
     * 下载远程服务器文件
     *
     * @param remotePath 远程服务器文件存放路径
     * @param localPath  本地保存路径
     */
    public void downloadRemoteFiles(String remotePath, String localPath) {

        Connection connection = new Connection(ip, port);

        try {
            connection.connect();
            boolean isAuthenticated = connection.authenticateWithPassword(userName, password);
            if (isAuthenticated) {
                SFTPv3Client sftp = new SFTPv3Client(connection);
                try {
                    List<SFTPv3DirectoryEntry> list = sftp.ls(remotePath);

                    for (SFTPv3DirectoryEntry sftPv3DirectoryEntry : list) {

                        if (".".equals(sftPv3DirectoryEntry.filename) || "..".equals(sftPv3DirectoryEntry.filename)) {
                            continue;
                        }

                        SCPClient scpClient = connection.createSCPClient();
                        SCPInputStream sis = scpClient.get(remotePath + sftPv3DirectoryEntry.filename);
                        File file = new File(localPath + sftPv3DirectoryEntry.filename);
                        FileOutputStream fos = new FileOutputStream(file);
                        byte[] b = new byte[4096];
                        int x;
                        while ((x = sis.read(b)) != -1) {
                            fos.write(b, 0, x);
                        }
                        fos.flush();
                        fos.close();
                        sis.close();
                    }
                    System.out.println("下载完成");
                } catch (Exception e) {
                    System.out.println("远程路径不存在");
                }
                sftp.close();
                connection.close();
            } else {
                System.out.println("用户名或密码错误，建立连接失败");
            }
        } catch (IOException e) {
            System.out.println("未找到对应主机");
        }
    }

    /**
     * @param fileName   文件名
     * @param remotePath 上传路径
     * @param mode       默认为null
     */
    public void uploadFileToRemote(String fileName, String remotePath, String mode) {

        Connection connection = new Connection(ip, port);
        try {
            File file = new File(fileName);
            if (file.exists()) {
                connection.connect();
                boolean isAuthenticated = connection.authenticateWithPassword(userName, password);
                if (isAuthenticated) {
                    SCPClient scpClient = new SCPClient(connection);
                    SCPOutputStream os = scpClient.put(file.getName(), file.length(), remotePath, mode);
                    byte[] b = new byte[4096];
                    FileInputStream fis = new FileInputStream(file);
                    int i;
                    while ((i = fis.read(b)) != -1) {
                        os.write(b, 0, i);
                    }
                    os.flush();
                    fis.close();
                    os.close();
                    connection.close();
                    System.out.println("上传完成");
                } else {
                    System.out.println("用户名或密码错误，建立连接失败");
                }
            } else {
                System.out.println("源文件不存在");
            }
        } catch (IOException e) {
            System.out.println("未找到对应主机");
        }
    }

    /**
     * @param remoteTargetDirectory 文件路径
     * @param remoteFile            文件名
     * @return SCPInputStream
     * @throws IOException IO异常
     */
    public SCPInputStream getStream(String remoteTargetDirectory, String remoteFile) throws IOException {
        Connection connection = new Connection(ip, port);
        connection.connect();
        boolean isAuthenticated = connection.authenticateWithPassword(userName, password);
        if (!isAuthenticated) {
            System.out.println("连接建立失败");
            return null;
        }
        SCPClient scpClient = connection.createSCPClient();
        return scpClient.get(remoteTargetDirectory + "/" + remoteFile);
    }
}
