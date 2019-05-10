package com.demo.service.Impl;

import com.demo.service.QuartzService;
import org.quartz.*;
import org.quartz.core.jmx.JobDetailSupport;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class QuartzServiceImpl implements QuartzService {
	
	@Autowired
	private Scheduler quartzScheduler;

	@Override
	public int addJob(String jobName, String jobGroupName, Class<?> job, String method, String description, String triggerName,String triggerGroupName, int seconds, String cronExpress) {
		try {
			// 判断任务是否存在
			JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
			if (quartzScheduler.checkExists(jobKey)) {
				return 1;// 任务已经存在
			}
			// 创建一个JobDetail实例
			Map<String, Object> JobDetailMap = new HashMap<>();
			// 设置任务名字
			JobDetailMap.put("name", jobName);
			// 设置任务组
			JobDetailMap.put("group", jobGroupName);
			// 设置描述
//			JobDetailMap.put("description", description);
			// 指定执行类
			JobDetailMap.put("jobClass", Class.forName("com.demo.service.Impl.JobDetailBean").getCanonicalName());

			Map<String, String> jobDataMap = new HashMap<>();
			jobDataMap.put("targetObject", job.getCanonicalName());
			jobDataMap.put("targetMethod", method);
			jobDataMap.put("jobName", jobName);
			JobDetailMap.put("jobDataMap", jobDataMap);

			JobDetail jobDetail = JobDetailSupport.newJobDetail(JobDetailMap);

			// 触发器
			CronTriggerImpl trigger = new CronTriggerImpl();
			trigger.setName(triggerName);
			trigger.setGroup(triggerGroupName);
			trigger.setCronExpression(cronExpress);

			trigger.setStartTime(new Date(new Date().getTime() + 1000 * seconds));

			// 通过SchedulerFactory获取一个调度器实例
			quartzScheduler.scheduleJob(jobDetail, trigger);//  注册并进行调度
			quartzScheduler.start();// ⑤调度启动
			return 0;// 添加成功
		} catch (Exception e) {
			e.printStackTrace();
			return 2;// 操作异常
		}
	}


	@Override
	public void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class cls, String cron) {
		try {
			// 获取调度器
			Scheduler sched = quartzScheduler;
			// 创建一项作业
			JobDetail job = JobBuilder.newJob(cls)
					.withIdentity(jobName, jobGroupName).build();
			// 创建一个触发器
			CronTrigger trigger = TriggerBuilder.newTrigger()
					.withIdentity(triggerName, triggerGroupName)
					.withSchedule(CronScheduleBuilder.cronSchedule(cron))
					.build();
			// 告诉调度器使用该触发器来安排作业
			sched.scheduleJob(job, trigger);
			// 启动
			if (!sched.isShutdown()) {
				sched.start();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 修改定时器任务信息
	 */
	@Override
	public boolean modifyJobTime(String oldjobName, String oldjobGroup, String oldtriggerName, String oldtriggerGroup, String jobName, String jobGroup,
			String triggerName, String triggerGroup, String cron) {
		try {
			Scheduler sched = quartzScheduler;
			CronTrigger trigger = (CronTrigger) sched.getTrigger(TriggerKey
					.triggerKey(oldtriggerName, oldtriggerGroup));
			if (trigger == null) {
				return false;
			}

			JobKey jobKey = JobKey.jobKey(oldjobName, oldjobGroup);
			TriggerKey triggerKey = TriggerKey.triggerKey(oldtriggerName,
					oldtriggerGroup);

			JobDetail job = sched.getJobDetail(jobKey);
			Class jobClass = job.getJobClass();
			// 停止触发器
			sched.pauseTrigger(triggerKey);
			// 移除触发器
			sched.unscheduleJob(triggerKey);
			// 删除任务
			sched.deleteJob(jobKey);
			
			addJob(jobName, jobGroup, triggerName, triggerGroup, jobClass,
					cron);
			
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public void modifyJobTime(String triggerName, String triggerGroupName,
			String time) {
		try {
			Scheduler sched = quartzScheduler;
			CronTrigger trigger = (CronTrigger) sched.getTrigger(TriggerKey
					.triggerKey(triggerName, triggerGroupName));
			if (trigger == null) {
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				CronTrigger ct = (CronTrigger) trigger;
				// 修改时间
				ct.getTriggerBuilder()
						.withSchedule(CronScheduleBuilder.cronSchedule(time))
						.build();
				// 重启触发器
				sched.resumeTrigger(TriggerKey.triggerKey(triggerName,
						triggerGroupName));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void removeJob(String jobName, String jobGroupName,
			String triggerName, String triggerGroupName) {
		try {
			Scheduler sched = quartzScheduler;
			// 停止触发器
			sched.pauseTrigger(TriggerKey.triggerKey(triggerName,
					triggerGroupName));
			// 移除触发器
			sched.unscheduleJob(TriggerKey.triggerKey(triggerName,
					triggerGroupName));
			// 删除任务
			sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void startSchedule() {
		try {
			Scheduler sched = quartzScheduler;
			sched.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void shutdownSchedule() {
		try {
			Scheduler sched = quartzScheduler;
			if (!sched.isShutdown()) {
				sched.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void pauseJob(String jobName, String jobGroupName) {
		try {
			quartzScheduler.pauseJob( JobKey.jobKey(jobName, jobGroupName));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	
	}

	@Override
	public void resumeJob(String jobName, String jobGroupName) {
		try {
			quartzScheduler.resumeJob(JobKey.jobKey(jobName, jobGroupName));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	
}
