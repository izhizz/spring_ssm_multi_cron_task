package com.demo.task;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class TestTask {


    /**
     * "0/5 * * * * ?  五秒运行一次
     */
    public void execute() throws JobExecutionException {
        System.out.println("----hello world---" + new Date());
    }

}
