package com.guce.application.infrastructure.config.aop;

import java.util.UUID;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MDCLoggingAspect {

    @Around("@annotation(com.guce.application.infrastructure.config.aop.MDCLogging)")
    public Object logMDC(ProceedingJoinPoint joinPoint) throws Throwable {
        MDC.put("correlation_id", UUID.randomUUID().toString());
        try {
            return joinPoint.proceed();
        } finally {
            MDC.clear();
        }
    }
}

