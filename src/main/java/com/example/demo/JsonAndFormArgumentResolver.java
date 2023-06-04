package com.example.demo;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestAttributeMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import javax.servlet.http.HttpServletRequest;


public class JsonAndFormArgumentResolver implements HandlerMethodArgumentResolver {

    //用来处理表单提交
    ServletModelAttributeMethodProcessor servletModelAttributeMethodProcessor;

    //用来处理json提交
    RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor;



    public void setAttributeMethodArgumentResolver(ServletModelAttributeMethodProcessor servletModelAttributeMethodProcessor) {
        this.servletModelAttributeMethodProcessor = servletModelAttributeMethodProcessor;
    }

    public void setRequestResponseBodyMethodProcessor(RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor) {
        this.requestResponseBodyMethodProcessor = requestResponseBodyMethodProcessor;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return servletModelAttributeMethodProcessor.supportsParameter(parameter)||requestResponseBodyMethodProcessor.supportsParameter(parameter);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        if (request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
            return requestResponseBodyMethodProcessor.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
        }else{
            return servletModelAttributeMethodProcessor.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
        }
    }
}
