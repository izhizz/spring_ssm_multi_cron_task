package com.demo.service.Impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class SpringServiceIml implements ApplicationContextAware {
    private static ApplicationContext context;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    public static <T> T getBean(String beanName) {
        if (context == null) {
            return null;
        }
        return (T) context.getBean(beanName);
    }

    public static <T> T getBean(Class<T> beanType) {
        if (context == null) {
            return null;
        }
        return context.getBean(beanType);
    }

    public static void cleanContext() {
        context = null;
    }
}

