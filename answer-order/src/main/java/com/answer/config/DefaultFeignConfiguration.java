package com.answer.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @author answer
 */
public class DefaultFeignConfiguration {
    @Bean
    public Logger.Level logLevel(){
        return Logger.Level.BASIC;
    }
}
