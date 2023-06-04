package com.example.demo;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestAttributeMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class HandlerConfig implements WebMvcConfigurer {
    @Autowired
    JsonAndFormArgumentResolver jsonAndFormArgumentResolver;


    //这里添加的实际上是CustomArgumentResolvers
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(jsonAndFormArgumentResolver);
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }

    @Bean
    public BeanPostProcessor beanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof RequestMappingHandlerAdapter) {
                    final RequestMappingHandlerAdapter requestMappingHandlerAdapter = (RequestMappingHandlerAdapter) bean;
                    for (HandlerMethodArgumentResolver argumentResolver : requestMappingHandlerAdapter.getArgumentResolvers()) {
                        if (argumentResolver instanceof ServletModelAttributeMethodProcessor) {
                            jsonAndFormArgumentResolver.setAttributeMethodArgumentResolver((ServletModelAttributeMethodProcessor) argumentResolver);
                        }
                        if (argumentResolver instanceof RequestResponseBodyMethodProcessor) {
                            jsonAndFormArgumentResolver.setRequestResponseBodyMethodProcessor((RequestResponseBodyMethodProcessor) argumentResolver);
                        }
                    }
                }
                return bean;
            }
        };
    }
}
