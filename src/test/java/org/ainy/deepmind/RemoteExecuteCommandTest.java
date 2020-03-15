package org.ainy.deepmind;

import ch.ethz.ssh2.Connection;
import org.ainy.deepmind.util.RemoteExecuteCommand;
import org.junit.Test;

/**
 * @author AINY_uan
 * @description 远程执行Linux命令测试类
 * @date 2020-03-15 14:57
 */
public class RemoteExecuteCommandTest {

    @Test
    public void ex1() {

		Connection conn = RemoteExecuteCommand.login("192.168.3.27", 22, "demo", "demo");
		System.out.println(RemoteExecuteCommand.execute(conn, "tail -100f /home/demo/Test/json.properties"));
    }
}
