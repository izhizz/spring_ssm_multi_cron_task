package com.demo.task;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestTask implements Job{


    /**
     * "0/5 * * * * ?  五秒运行一次
     */
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.out.println("----hello world---" + new Date());
    }

}
