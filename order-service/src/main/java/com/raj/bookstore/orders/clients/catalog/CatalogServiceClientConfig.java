package com.raj.bookstore.orders.clients.catalog;

import com.raj.bookstore.orders.ApplicationProperties;
import java.time.Duration;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
//import org.springframework.boot.web.client.ClientHttpRequestFactories;
//import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
class CatalogServiceClientConfig {
    @Bean //RestClient.Builder builder
    RestClient restClient(RestClient.Builder builder, ApplicationProperties properties) {

        //Without Timeout Seconds

        /*return RestClient.builder()
                .baseUrl(properties.catalogServiceUrl())
                .build();*/

        //With Timeout Seconds

        //Depricated in 3.4.0
        /*return RestClient.builder()
                .baseUrl(properties.catalogServiceUrl())
                .requestFactory(ClientHttpRequestFactories .get(ClientHttpRequestFactorySettings.DEFAULTS
                        .withConnectTimeout(Duration.ofSeconds(5))
                        .withReadTimeout(Duration.ofSeconds(5))))
                .build();*/

        ClientHttpRequestFactory requestFactory = ClientHttpRequestFactoryBuilder.simple()
                .withCustomizer(customizer -> {
                    customizer.setConnectTimeout(Duration.ofSeconds(5));
                    customizer.setReadTimeout(Duration.ofSeconds(5));
                })
                .build();
        return builder.baseUrl(properties.catalogServiceUrl())
                .requestFactory(requestFactory)
                .build();
    }
}
