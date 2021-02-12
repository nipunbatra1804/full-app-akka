package com.learning.fullappakka.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class APIConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        return objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
    }

    @Bean
    fun objectWriter(): ObjectWriter {
        return objectMapper().writerWithDefaultPrettyPrinter();
    }

    @Bean
    fun webClient(): WebClient {
        return WebClient.builder().clientConnector(ReactorClientHttpConnector()).build()
    }
}
