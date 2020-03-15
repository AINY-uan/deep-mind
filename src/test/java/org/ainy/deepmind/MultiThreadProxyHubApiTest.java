package org.ainy.deepmind;


import org.ainy.deepmind.util.MultiThreadProxyHubApi;
import org.junit.Test;

/**
 * 压力测试
 *
 * @author YUAN DONG
 */
public class MultiThreadProxyHubApiTest {

    @Test
    public void ex1() {

        /*
         * for (int j = 1; j < 6; j++) {
         *
         * for (int i = 5; i < 15; i++) { new MultiThreadProxyHubApiTest(i, j).run(); }
         * }
         */

        // new MultiThreadProxyHubApiTest(5, 5).run();
        new MultiThreadProxyHubApi(500, 5000).run();
        System.out.println("finished !");
    }
}