package com.yonyou.reconciliation.task.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleJob implements Job {
	
	private static Logger LOGGER = LoggerFactory.getLogger(SimpleJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.debug("JOB EXECUTE");
		
	}

}
