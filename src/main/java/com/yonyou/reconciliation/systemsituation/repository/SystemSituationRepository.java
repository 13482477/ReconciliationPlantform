package com.yonyou.reconciliation.systemsituation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.yonyou.reconciliation.systemsituation.entity.SystemSituation;

@Repository
public interface SystemSituationRepository extends JpaRepository<SystemSituation, Long>, JpaSpecificationExecutor<SystemSituation> {
	
	public SystemSituation getByTagString(String tagString);

}
