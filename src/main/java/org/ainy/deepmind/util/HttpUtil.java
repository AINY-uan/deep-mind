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
public class HttpUtil {

    /**
     * Http Post请求
     *
     * @param url         请求地址
     * @param params      请求参数
     * @param contentType 内容类型
     * @return 请求成功时返回，其他情况返回null
     */
    public static String doPost(String url, String params, String contentType) {

        CloseableHttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(url);
        try {
            if (StringUtils.isNotEmpty(params)) {
                StringEntity requestEntity = new StringEntity(params, "UTF-8");
                requestEntity.setContentEncoding("UTF-8");
                requestEntity.setContentType(contentType);
                httpPost.setEntity(requestEntity);
            }
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                if (response == null) {
                    log.error("http response is null");
                } else {
                    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        if (response.getEntity() == null) {
                            log.error("http response code is 200, but response entity is null");
                        } else {
                            return EntityUtils.toString(response.getEntity(), "UTF-8");
                        }
                    } else {
                        log.error("http request status code is {}", response.getStatusLine().getStatusCode());
                    }
                }
            }
        } catch (Exception e) {
            log.error("http request error");
        } finally {
            try {
                closeHttpClient(httpClient);
            } catch (IOException e) {
                log.error("close client error");
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
    public static String doGet(String url, String params) {

        CloseableHttpClient httpClient = getHttpClient();
        HttpGet httpGet = new HttpGet(url.concat("?").concat(params));
        try {
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                if (response == null) {
                    log.error("http response is null");
                } else {
                    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        if (response.getEntity() == null) {
                            log.error("http response code is 200, but response entity is null");
                        } else {
                            return EntityUtils.toString(response.getEntity(), "UTF-8");
                        }
                    } else {
                        log.error("http request status code is {}", response.getStatusLine().getStatusCode());
                    }
                }
            }
        } catch (Exception e) {
            log.error("http request error");
        } finally {
            try {
                closeHttpClient(httpClient);
            } catch (IOException e) {
                log.error("close client error");
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
