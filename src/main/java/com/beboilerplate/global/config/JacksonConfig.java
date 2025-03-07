package com.beboilerplate.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // Java 8 날짜/시간 지원 추가
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // 커스텀 역직렬화 설정
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDateTime.class,
                new com.fasterxml.jackson.databind.JsonDeserializer<>() {
                    @Override
                    public LocalDateTime deserialize(
                            com.fasterxml.jackson.core.JsonParser parser,
                            com.fasterxml.jackson.databind.DeserializationContext context
                    ) throws IOException {
                        String text = parser.getText();
                        return LocalDateTime.parse(text, DateTimeFormatter.ISO_DATE_TIME);
                    }
                }
        );
        mapper.registerModule(module);

        return mapper;
    }
}