package com.yonyou.reconciliation.task.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	@Transactional
	public void save(Task task) {
		this.taskRepository.save(task);
	}

	@Transactional
	public void update(Task task) {
		this.taskRepository.saveAndFlush(task);
	}
	
	@Transactional
	public void updateTaskStatusById(TaskStatus taskStatus, Long id) {
		Task task = this.taskRepository.findOne(id);
		task.setTaskStatus(taskStatus);
		this.taskRepository.saveAndFlush(task);
	}
	
	@Transactional
	public Task findOne(Long id) {
		return this.taskRepository.findOne(id);
	}
	
	@Transactional
	public void delete(Long id) {
		this.taskRepository.delete(id);
	}

	public Page<Task> find(String cron, TaskStatus taskStatus, String taskName, String taskGroup, Pageable pageable) {
		return this.taskRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<Predicate>();

			if (StringUtils.isNotBlank(cron)) {
				predicates.add(criteriaBuilder.equal(root.get("cron"), cron));
			}

			if (taskStatus != null) {
				predicates.add(criteriaBuilder.equal(root.get("taskStatus"), taskStatus));
			}

			if (StringUtils.isNotBlank(taskName)) {
				predicates.add(criteriaBuilder.equal(root.get("taskName"), taskName));
			}

			if (StringUtils.isNotBlank(taskGroup)) {
				predicates.add(criteriaBuilder.equal(root.get("taskGroup"), taskGroup));
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[] {}));
		}, pageable);
	}

	public JobDetail getJobDetailFromSchema(Long taskId) {
		Task task = this.taskRepository.findOne(taskId);

		try {
			return task == null ? null : this.scheduler.getJobDetail(JobKey.jobKey(task.getTaskName(), task.getTaskGroup()));
		} catch (SchedulerException e) {
			throw new RuntimeException("Can not found job detail with JobKey", e);
		}
	}

	@Transactional
	public void start(Long taskId) {
		Task task = this.taskRepository.findOne(taskId);

		if (task == null) {
			throw new RuntimeException("The task is not exists!");
		}

		try {
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(task.getTaskName(), task.getTaskGroup()).withSchedule(CronScheduleBuilder.cronSchedule(task.getCron())).build();
			
			JobDataMap jobDataMap = new JobDataMap();
			jobDataMap.putAsString("taskId", taskId);

			JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class).withIdentity(task.getTaskName(), task.getTaskGroup()).storeDurably(Boolean.TRUE).requestRecovery(Boolean.TRUE).setJobData(jobDataMap).build();
			
			JobKey jobKey = JobKey.jobKey(task.getTaskName(), task.getTaskGroup());
			
			if (!scheduler.checkExists(jobKey)) {
				scheduler.scheduleJob(jobDetail, trigger);
			}
			else {
				scheduler.resumeJob(jobKey);
			}
			
		} catch (SchedulerException e) {
			throw new RuntimeException("Start job failed", e);
		}
		
		task.setTaskStatus(TaskStatus.PENDDING);
		this.taskRepository.saveAndFlush(task);
	}

	@Transactional
	public void pause(Long taskId) {
		Task task = this.taskRepository.findOne(taskId);

		if (task == null) {
			throw new RuntimeException("The task is not exists!");
		}

		try {
			this.scheduler.pauseJob(JobKey.jobKey(task.getTaskName(), task.getTaskGroup()));
		} catch (SchedulerException e) {
			throw new RuntimeException("Stop job failed", e);
		}
		
		task.setTaskStatus(TaskStatus.PAUSE);
		this.taskRepository.saveAndFlush(task);
	}

	@Transactional
	public void stop(Long taskId) {
		Task task = this.taskRepository.findOne(taskId);

		if (task == null) {
			throw new RuntimeException("The task is not exists!");
		}

		try {
			this.scheduler.deleteJob(JobKey.jobKey(task.getTaskName(), task.getTaskGroup()));
		} catch (SchedulerException e) {
			throw new RuntimeException("Stop job failed", e);
		}
		
		task.setTaskStatus(TaskStatus.STOP);
		this.taskRepository.saveAndFlush(task);
	}

}
