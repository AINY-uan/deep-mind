package org.ainy.deepmind.util;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author 阿拉丁省油的灯
 * @date 2018-12-12 12:12
 * @description 远程执行Linux命令
 */
public class RemoteExecuteCommand {

    private static final Logger logger = LoggerFactory.getLogger(RemoteExecuteCommand.class);

    private static final String DEFAULTCHART = "UTF-8";

    /**
     * @param ip       主机IP地址
     * @param port     主机端口
     * @param userName 用户名
     * @param userPwd  用户密码
     * @return 验证成功返回连接信息，否则返回null
     */
    public static Connection login(String ip, int port, String userName, String userPwd) {

        Connection conn;
        try {
            conn = new Connection(ip, port);
            conn.connect();// 连接
            // 认证
            boolean flag = conn.authenticateWithPassword(userName, userPwd);
            if (flag) {
                logger.info("********* 验证成功 *********");
                return conn;
            } else {
                logger.info("********* 验证失败 *********");
            }
        } catch (IOException e) {
            logger.error("********* 验证出现错误 ********" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param conn 连接信息
     * @param cmd  待执行的Linux命令
     * @return 执行命令后的输出结果
     */
    public static String execute(Connection conn, String cmd) {

        String result = "";
        try {
            if (conn != null) {
                // 打开一个会话
                Session session = conn.openSession();
                // 执行命令
                session.execCommand(cmd);
                result = processStdout(session.getStdout());
                if (StringUtils.isBlank(result)) {
                    logger.info("得到标准输出为空, " + "执行的命令: " + cmd);
                    result = processStdout(session.getStderr());
                } else {
                    logger.info("执行命令成功, " + "执行的命令: " + cmd);
                }
                conn.close();
                session.close();
            }
        } catch (IOException e) {
            logger.info("执行命令失败, " + "执行的命令: " + cmd + "\n" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解析脚本执行返回的结果集
     *
     * @param in 输入流对象
     * @return 以纯文本的格式返回
     */
    private static String processStdout(InputStream in) {

        InputStream stdout = new StreamGobbler(in);
        StringBuilder buffer = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout, RemoteExecuteCommand.DEFAULTCHART));
            String line;
            while ((line = br.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            br.close();
        } catch (IOException e) {
            logger.error("解析脚本出错：" + e.getMessage());
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
