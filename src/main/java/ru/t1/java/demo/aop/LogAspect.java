package ru.t1.java.demo.aop;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.LoggerFactory;
import java.util.List;
import static java.util.Objects.isNull;

@Aspect
public class LogAspect {
    private static final Logger logger = (Logger) LoggerFactory.getILoggerFactory().getLogger(LogAspect.class.getName());

    public LogAspect(Level level) {
        logger.info("set level, level = {}", level);
        logger.setLevel(level);
    }

    @Before("@annotation(ru.t1.java.demo.aop.LogExecution)")
    public void logAnnotationBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        logger.info("Call method: {}", methodName);
        if (args != null && args.length > 0) {
            logger.debug("Call method: {} with arguments: {}", methodName, joinPoint.getArgs());
        } else {
            logger.warn("Call method: {} without arguments", methodName);
        }
    }

    @AfterThrowing(pointcut = "@annotation(ru.t1.java.demo.aop.LogException)", throwing = "e")
    public void logExceptionAnnotation(JoinPoint joinPoint, Exception e) {
        logger.error("ASPECT EXCEPTION ANNOTATION: Logging exception: {}", joinPoint.getSignature().getName());
        logger.warn("Message {}", e.getMessage());
        e.printStackTrace();
    }

    @AfterReturning(pointcut = "@annotation(ru.t1.java.demo.aop.HandlingResult)", returning = "result")
    public void handleResult(JoinPoint joinPoint, List<Object> result) {
        logger.info("В результате выполнения метода {}", joinPoint.getSignature().toShortString());
        List<Object> result_ = isNull(result) ? List.of() : result;
        logger.info("получен результат в количестве: {} ", result_.size());
        logger.warn("Подробности: {}", result_);
    }

}
