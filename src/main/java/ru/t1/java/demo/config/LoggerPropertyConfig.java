package ru.t1.java.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(LogAspectConfig.class)
public class LoggerPropertyConfig {
    private static final Logger log = LoggerFactory.getLogger(LoggerPropertyConfig.class);

    @Bean
    @ConfigurationProperties("log.aspect")
    @ConditionalOnProperty(value = "log.aspect.enable", havingValue = "true")
    public LoggerProperties loggerProperties() {
        log.info("CREATE LoggerProperties");
        return new LoggerProperties();
    }
}
