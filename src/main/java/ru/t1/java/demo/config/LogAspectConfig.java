package ru.t1.java.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.t1.java.demo.aop.LogAspect;

@Configuration
@AutoConfigureAfter(LoggerProperties.class)
public class LogAspectConfig {
    private static final Logger log = LoggerFactory.getLogger(LogAspectConfig.class);
    @Autowired
    private LoggerProperties properties;

    @Bean
    @ConditionalOnBean(LoggerProperties.class)
    public LogAspect logAspect() {
        log.info("CREATE LogAspect");
       return new LogAspect(properties.getLevel());
    }
}
