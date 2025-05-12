package com.example.scheduleapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    //기본 설정
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Schedule API")
                        .description("일정 관리 API 문서")
                        .version("v1.0"));
    }

    //기본 API 구성
    @Bean
    public GroupedOpenApi publicApi(){
        return GroupedOpenApi.
                builder().
                group("schedule-public").
                pathsToMatch("/api/schedules/**").
                build();
    }
}
