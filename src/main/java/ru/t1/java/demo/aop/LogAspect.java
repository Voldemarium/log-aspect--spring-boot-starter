package ru.t1.java.demo.aop;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Aspect
public class LogAspect {
    private static final Logger logger = (Logger) LoggerFactory.getILoggerFactory().getLogger(LogAspect.class.getName());
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;

    public LogAspect(Level level) {
        logger.info("set level, level = {}", level);
        logger.setLevel(level);
    }

    @Before("@annotation(ru.t1.java.demo.aop.LogExecution))")
    public void logAnnotationBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            logger.debug("Call method: {}, with arguments: {}", methodName, joinPoint.getArgs());
        } else {
            logger.warn("Call method: {} without arguments", methodName);
        }
        if (null != request) {
            logger.debug("Method Type : {}", request.getMethod());
            logger.info("Request URL: {}", request.getRequestURL().toString());
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                logger.debug("Header Name: {} Header Value : {}", headerName, headerValue);
            }
        }
    }

    @AfterThrowing(pointcut = "@annotation(ru.t1.java.demo.aop.LogException)", throwing = "e")
    public void logExceptionAnnotation(JoinPoint joinPoint, Exception e) {
        logger.error("ASPECT EXCEPTION ANNOTATION: Logging exception: {}", joinPoint.getSignature().getName());
        if (null != request) {
            logger.warn("Method Type : {}", request.getMethod());
            logger.warn("Request URL: {}", request.getRequestURL().toString());
        }
        logger.error("Message: {}", e.getMessage());
        logger.info("Exception in {}.{}() with arguments: {} with cause = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                joinPoint.getArgs(),
                e.getCause() != null ? e.getCause() : "NULL");
    }

    @AfterReturning(pointcut = "@annotation(ru.t1.java.demo.aop.HandlingResult)", returning = "result")
    public void handleResult(JoinPoint joinPoint, Object result) {
        logger.info("В результате выполнения метода: {}", joinPoint.getSignature().toShortString());
        logger.info("Статус ответа {}", response.getStatus());
        logger.info("получен результат: {}", result);
    }
}
