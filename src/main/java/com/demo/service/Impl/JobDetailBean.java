package com.demo.service.Impl;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class JobDetailBean extends QuartzJobBean {

    private Logger logger = LoggerFactory.getLogger(JobDetailBean.class);

    private String targetObject;
    private String targetMethod;
    private String jobName;

    private static ApplicationContext ctx = SpringServiceIml.getApplicationContext();

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            System.out.println();
            Class<?> aClass = Class.forName(targetObject);
            Object bean = ctx.getBean(aClass);
            Method m = bean.getClass().getMethod(this.targetMethod);
            m.invoke(bean);


        } catch (Exception e) {
            e.printStackTrace();
            throw new JobExecutionException(e);
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }

    public void setTargetObject(String targetObject) {
        this.targetObject = targetObject;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}