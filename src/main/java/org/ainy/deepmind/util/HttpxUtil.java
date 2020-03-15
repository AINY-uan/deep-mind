package org.ainy.deepmind.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author 阿拉丁省油的灯
 * @date 2019-11-17 22:10
 * @description Http请求工具
 */
@Slf4j
public class HttpxUtil {

    /**
     * Http Post请求
     *
     * @param url         请求地址
     * @param params      请求参数
     * @param contentType 内容类型
     * @return 请求成功时返回，其他情况返回null
     */
    public static String sendPostX(String url, String params, String contentType) {

        CloseableHttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(url);
        try {
            if (StringUtils.isNoneEmpty(params)) {
                StringEntity s = new StringEntity(params, "utf-8");
                s.setContentEncoding("UTF-8");
                s.setContentType(contentType);
                httpPost.setEntity(s);
            }
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK && response.getEntity() != null) {
                    return EntityUtils.toString(response.getEntity(), "UTF-8");
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                closeHttpClient(httpClient);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return null;
    }

    /**
     * Http Get 请求
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 请求成功时返回，其他情况返回null
     */
    public static String sendGetX(String url, String params) {

        CloseableHttpClient httpClient = getHttpClient();
        HttpGet httpGet = new HttpGet(url + params);
        try {
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK && response.getEntity() != null) {
                    return EntityUtils.toString(response.getEntity(), "UTF-8");
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                closeHttpClient(httpClient);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return null;
    }

    private static CloseableHttpClient getHttpClient() {

        RequestConfig config = RequestConfig.custom().setConnectTimeout(8000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(8000).build();
        return HttpClients.custom().setDefaultRequestConfig(config).build();
    }

    private static void closeHttpClient(CloseableHttpClient client) throws IOException {

        if (client != null) {
            client.close();
        }
    }
}
