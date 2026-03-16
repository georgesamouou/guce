package com.guce.application.infrastructure.config;

import com.guce.application.domain.annotation.DomainService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages="com.guce.application", includeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION, value = DomainService.class))
public class DomainConfig {

}
