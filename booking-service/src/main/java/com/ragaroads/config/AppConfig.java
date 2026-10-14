package com.ragaroads.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Value("${artist.service.url}")
    private String artistServiceUrl;

    @Value("${concert.service.url}")
    private String concertServiceUrl;

    @Bean
    public WebClient artistWebClient() {
        return WebClient.builder()
                .baseUrl(artistServiceUrl)
                .build();
    }

    @Bean
    public WebClient concertWebClient() {
        return WebClient.builder()
                .baseUrl(concertServiceUrl)
                .build();
    }
}
