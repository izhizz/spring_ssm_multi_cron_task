package com.demo.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class HelloWorldJob implements Job{

	
	/**
	 * "0/10 * * * * ?  
	 */
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("----hello world---" + new Date());
	}

}
