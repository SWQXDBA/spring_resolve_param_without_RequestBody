package com.example.demo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestAttributeMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class HandlerConfig {

    @Bean
    public BeanPostProcessor beanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof RequestMappingHandlerAdapter) {
                    final RequestMappingHandlerAdapter requestMappingHandlerAdapter = (RequestMappingHandlerAdapter) bean;
                    final List<HandlerMethodArgumentResolver> argumentResolvers = requestMappingHandlerAdapter.getArgumentResolvers();
                    JsonAndFormArgumentResolver myResolver = new JsonAndFormArgumentResolver();
                    List<HandlerMethodArgumentResolver> newList = new ArrayList<>();
                    for (HandlerMethodArgumentResolver argumentResolver : argumentResolvers) {
                        if (argumentResolver instanceof ServletModelAttributeMethodProcessor) {
                            newList.add(myResolver);
                            myResolver.setAttributeMethodArgumentResolver((ServletModelAttributeMethodProcessor) argumentResolver);
                        }
                        if (argumentResolver instanceof RequestResponseBodyMethodProcessor) {
                            myResolver.setRequestResponseBodyMethodProcessor((RequestResponseBodyMethodProcessor) argumentResolver);
                        }
                        newList.add(argumentResolver);
                    }
                    requestMappingHandlerAdapter.setArgumentResolvers(newList);
                    return requestMappingHandlerAdapter;
                }
                return bean;
            }
        };
    }


}
