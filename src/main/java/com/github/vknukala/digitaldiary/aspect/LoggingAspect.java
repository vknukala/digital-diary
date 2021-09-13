package com.github.vknukala.digitaldiary.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTimeForResource(ProceedingJoinPoint joinPoint) throws Throwable{
        final String requestId = UUID.randomUUID().toString();
        Instant start = Instant.now();
        try{
            log.info("Start requestId {} : {} - {} with params {}",requestId, httpServletRequest.getMethod(),httpServletRequest.getRequestURI(),
                    Arrays.stream(joinPoint.getArgs()).map(a->a.toString()).collect(Collectors.joining(",")));
            Object proceed = joinPoint.proceed();
            log.info("End requestId {} : {} :time - {} ms",requestId, httpServletRequest.getRequestURI(),
                    Duration.between(start,Instant.now()).toMillis());
            return proceed;
        }catch(Throwable t){
            log.info("End requestId {} : {} :time - {} ms",requestId, httpServletRequest.getRequestURI(),
                    Duration.between(start,Instant.now()).toMillis());
            throw t;
        }
    }
}
