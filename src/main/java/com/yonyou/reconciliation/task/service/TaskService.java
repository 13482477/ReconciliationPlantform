package com.yonyou.reconciliation.task.service;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.reconciliation.task.entity.Task;
import com.yonyou.reconciliation.task.entity.TaskStatus;
import com.yonyou.reconciliation.task.job.SimpleJob;
import com.yonyou.reconciliation.task.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private Scheduler scheduler;

	@Autowired
	private TaskRepository taskRepository;
	
	
	private void addTaskToScheduler(Task task) {
		
		TriggerKey triggerKey = TriggerKey.triggerKey(task.getTaskName(), task.getTaskGroup());
		try {
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			if (trigger != null) {
				return;
			}
			
			JobDetail jobDetail = JobBuilder
					.newJob(SimpleJob.class)
					.withIdentity(task.getTaskName(), task.getTaskGroup())
					.storeDurably(Boolean.TRUE)
					.requestRecovery(Boolean.TRUE)
					.build();
			
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCron());
			trigger = TriggerBuilder.newTrigger().withIdentity(task.getTaskName(), task.getTaskGroup()).withSchedule(scheduleBuilder).build();
			scheduler.scheduleJob(jobDetail, trigger);
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	
	
	private void updateTaskInScheduler(Task task) {
		TriggerKey triggerKey = TriggerKey.triggerKey(task.getTaskName(), task.getTaskGroup());
		try {
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			if (trigger == null) {
				return;
			}
			
			JobDetail jobDetail = JobBuilder.newJob().build();
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCron());
			trigger = TriggerBuilder.newTrigger().withIdentity(task.getTaskName(), task.getTaskGroup()).withSchedule(scheduleBuilder).build();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
	}
	
	private void registTask(Task task) {
		TriggerKey triggerKey = TriggerKey.triggerKey(task.getTaskName(), task.getTaskGroup());
		try {
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
	}
		
		
		

	public void refreshTrigger() {
		try {
			// 查询出数据库中所有的定时任务
			List<Task> taskList = this.taskRepository.findAll();
			if (taskList != null) {
				for (Task task : taskList) {
					TaskStatus taskStatus = task.getTaskStatus();
					TriggerKey triggerKey = TriggerKey.triggerKey(task.getTaskName(), task.getTaskGroup());
					CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
					
					if (null == trigger) {
						if (TaskStatus.DISABLE.equals(taskStatus)) {
							continue;
						}

						JobDetail jobDetail = null;
						// 创建JobDetail（数据库中job_name存的任务全路径，这里就可以动态的把任务注入到JobDetail中）
						jobDetail = JobBuilder.newJob().build();
						CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCron());
						trigger = TriggerBuilder.newTrigger().withIdentity(task.getTaskName(), task.getTaskGroup()).withSchedule(scheduleBuilder).build();

						scheduler.scheduleJob(jobDetail, trigger);
					} else { // 说明查出来的这条任务，已经设置到quartz中了
						// Trigger已存在，先判断是否需要删除，如果不需要，再判定是否时间有变化
						if (TaskStatus.ENABLE.equals(taskStatus)) { // 如果是禁用，从quartz中删除这条任务
							JobKey jobKey = JobKey.jobKey(task.getTaskName(), task.getTaskGroup());
							scheduler.deleteJob(jobKey);
							continue;
						}
						String searchCron = task.getCron(); // 获取数据库的
						String currentCron = trigger.getCronExpression();
						if (!searchCron.equals(currentCron)) { // 说明该任务有变化，需要更新quartz中的对应的记录
							// 表达式调度构建器
							CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(searchCron);

							// 按新的cronExpression表达式重新构建trigger
							trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

							// 按新的trigger重新设置job执行
							scheduler.rescheduleJob(triggerKey, trigger);
						}
					}
				}
			}
		} catch (Exception e) {
		}

	}

}
