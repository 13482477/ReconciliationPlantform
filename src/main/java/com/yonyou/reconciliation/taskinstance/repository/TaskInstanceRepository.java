package com.yonyou.reconciliation.taskinstance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.yonyou.reconciliation.taskinstance.entity.TaskInstance;

@Repository
public interface TaskInstanceRepository extends JpaRepository<TaskInstance, Long>, JpaSpecificationExecutor<TaskInstance>{

}
