package com.demo.task;

import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TestTask1 {


    /**
     * "0/5 * * * * ?  五秒运行一次
     */
    public void execute() throws JobExecutionException {
        System.out.println("----hello world1---" + new Date());
    }

}
