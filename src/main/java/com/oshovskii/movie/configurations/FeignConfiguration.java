package com.oshovskii.movie.configurations;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.oshovskii.movie.clients")
public class FeignConfiguration {
}
