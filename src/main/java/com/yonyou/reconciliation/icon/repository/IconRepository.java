package com.yonyou.reconciliation.icon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yonyou.reconciliation.icon.entity.Icon;
import com.yonyou.reconciliation.icon.entity.IconType;

@Repository
public interface IconRepository extends JpaRepository<Icon, Long>  {
	
	public List<Icon> findByIconType(IconType iconType);

}
