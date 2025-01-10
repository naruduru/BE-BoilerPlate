package com.beboilerplate.global.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LogAop {

    @Around("(execution(* com.beboilerplate.domain..controller..*(..)) || execution(* com.beboilerplate.domain..service..*(..))) && !execution(* com.beboilerplate.domain.HomeController.*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("START: {}", joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            log.info("END: {}", joinPoint.toString());
        }
    }
}
