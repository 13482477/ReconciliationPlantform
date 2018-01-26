package com.yonyou.reconciliation.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.yonyou.reconciliation.task.entity.Task;
import com.yonyou.reconciliation.task.entity.TaskAttribute;

@Repository
public interface TaskAttributeRepository extends JpaRepository<TaskAttribute, Long>, JpaSpecificationExecutor<TaskAttribute> {
	
	public List<TaskAttribute> findByTask(Task task);

}
