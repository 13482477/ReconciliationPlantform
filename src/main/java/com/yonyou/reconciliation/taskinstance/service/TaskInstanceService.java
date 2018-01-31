package com.yonyou.reconciliation.taskinstance.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yonyou.reconciliation.taskinstance.entity.TaskInstance;
import com.yonyou.reconciliation.taskinstance.entity.TaskInstanceStatus;
import com.yonyou.reconciliation.taskinstance.repository.TaskInstanceRepository;

@Service
public class TaskInstanceService {

	@Autowired
	private TaskInstanceRepository taskInstanceRepository;

	@Transactional
	public void save(TaskInstance taskInstance) {
		this.taskInstanceRepository.save(taskInstance);
	}

	@Transactional
	public void update(TaskInstance taskInstance) {
		this.taskInstanceRepository.saveAndFlush(taskInstance);
	};

	@Transactional
	public TaskInstance findOne(Long id) {
		return this.taskInstanceRepository.findOne(id);
	}

	@Transactional
	public void updateTaskInstanceStatusById(TaskInstanceStatus taskInstanceStatus, Long id) {
		TaskInstance taskInstance = this.taskInstanceRepository.findOne(id);
		taskInstance.setTaskInstanceStatus(taskInstanceStatus);
		this.taskInstanceRepository.saveAndFlush(taskInstance);
	}

	@Transactional
	public void updateFinishedDateById(Date date, Long id) {
		TaskInstance taskInstance = this.taskInstanceRepository.findOne(id);
		taskInstance.setFinishedDate(date);
		this.taskInstanceRepository.saveAndFlush(taskInstance);
	}

	public Page<TaskInstance> find(String taskName, String taskGroup, TaskInstanceStatus taskInstanceStatus, Date createDateStart, Date createDateEnd, Date executeDateStart, Date executeDateEnd,
			Date finishedDateStart, Date finishedDateEnd, Pageable pageable) {
		return this.taskInstanceRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<Predicate>();
			if (StringUtils.isNotBlank(taskName)) {
				predicates.add(criteriaBuilder.equal(root.get("taskName"), taskName));
			}
			if (StringUtils.isNotBlank(taskName)) {
				predicates.add(criteriaBuilder.equal(root.get("taskGroup"), taskGroup));
			}
			if (StringUtils.isNotBlank(taskName)) {
				predicates.add(criteriaBuilder.equal(root.get("taskInstanceStatus"), taskInstanceStatus));
			}
			if (createDateStart != null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createDate"), createDateStart));
			}
			if (createDateEnd != null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createDate"), createDateStart));
			}
			if (createDateStart != null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("executeDate"), executeDateStart));
			}
			if (createDateEnd != null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("executeDate"), executeDateStart));
			}
			if (createDateStart != null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("finishedDate"), finishedDateStart));
			}
			if (createDateEnd != null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("finishedDate"), finishedDateStart));
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[] {}));
		}, pageable);
	}

}
