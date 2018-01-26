package com.yonyou.reconciliation.conf;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.TriggerFiredBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import com.yonyou.reconciliation.initializer.QuartzScriptInitializer;
import com.yonyou.reconciliation.task.listener.GlobalTaskListener;

@Configuration
public class QuartzConfig {
	
	private static Logger LOGGER = LoggerFactory.getLogger(QuartzConfig.class);

	@Autowired
	private DataSource dataSource;
	
	public static class AutowireSpringBeanJobFactory extends SpringBeanJobFactory {

		@Autowired
		private AutowireCapableBeanFactory capableBeanFactory;

		@Override
		protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
			Object jobInstance = super.createJobInstance(bundle);
			this.capableBeanFactory.autowireBean(jobInstance);
			return jobInstance;
		}

	}

	@Bean
	public AutowireSpringBeanJobFactory jobFactory() {
		AutowireSpringBeanJobFactory jobFactory = new AutowireSpringBeanJobFactory();
		return jobFactory;
	}

	@Bean
	public Properties quartzProperties() {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		try {
			propertiesFactoryBean.afterPropertiesSet();
			return propertiesFactoryBean.getObject();
		} catch (IOException e) {
			LOGGER.error("Process quartz.properties file error.", e);
		}
		return null;
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		schedulerFactoryBean.setDataSource(this.dataSource);
		schedulerFactoryBean.setQuartzProperties(quartzProperties());
		schedulerFactoryBean.setJobFactory(this.jobFactory());
		return schedulerFactoryBean;
	}

	@Bean
	public Scheduler scheduler(@Autowired GlobalTaskListener globalTaskListener) {
		Scheduler scheduler = this.schedulerFactoryBean().getScheduler();
		
		try {
			scheduler.getListenerManager().addJobListener(globalTaskListener);
		} catch (SchedulerException e) {
			LOGGER.error("Can not add global task listener into scheduler.", e);
			throw new RuntimeException(e);
		}
		
		return scheduler;
	}
	
	@Bean
	public QuartzScriptInitializer quartzScriptInitializer() {
		QuartzScriptInitializer quartzScriptInitializer = new QuartzScriptInitializer();
		return quartzScriptInitializer;
	}
	

}
