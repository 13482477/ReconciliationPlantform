package com.yonyou.reconciliation.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yonyou.reconciliation.image.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
	
	public Image getByFileName(String fileName);
	
}
