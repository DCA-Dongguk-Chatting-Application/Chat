package com.dongguk.chat.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Slf4j
@Configuration
@EnableElasticsearchRepositories("com.dongguk.chat.domain.message.infra")
public class ElasticSearchConfig extends ElasticsearchConfiguration {
    private static String host = "localhost:9200";
    private static String username = "elastic";
    private static String password = "rhdoddldml!1423";

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(host)
                .withBasicAuth(username, password)
                .build();
    }

//    public static SSLContext disableSslVerification() {
//        try {
//            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
//                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                    return null;
//                }
//
//                public void checkClientTrusted(X509Certificate[] certs, String authType) {
//                }
//
//                public void checkServerTrusted(X509Certificate[] certs, String authType) {
//                }
//            }};
//
//            SSLContext sc = SSLContext.getInstance("SSL");
//            sc.init(null, trustAllCerts, new java.security.SecureRandom());
//            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//
//            return sc;
//        } catch (NoSuchAlgorithmException | KeyManagementException e) {
//            log.warn(e.getMessage());
//        }
//        return null;
//    }
//
//    public static HostnameVerifier allHostsValid() {
//        // host name verifier 생성(호스트 네임 체크안함)
//        HostnameVerifier allHostsValid = (hostname, session) -> true;
//
//        // host name verifier 설치
//        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
//
//        return allHostsValid;
//    }
}
