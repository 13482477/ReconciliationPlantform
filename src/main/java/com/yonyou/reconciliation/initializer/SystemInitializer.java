package com.yonyou.reconciliation.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.yonyou.reconciliation.initializer.service.SystemInitializeService;
import com.yonyou.reconciliation.menu.service.MenuService;
import com.yonyou.reconciliation.requestmapping.service.RequestMappingService;
import com.yonyou.reconciliation.security.metadatasource.DatabaseMetadataSource;

@Component
@Order(1)
public class SystemInitializer implements CommandLineRunner {
	
	private @Autowired SystemInitializeService systemInitializeService;
	
	private @Autowired DatabaseMetadataSource securityMetadataSource;

	private @Autowired MenuService menuService;
	
	private @Autowired RequestMappingService requestMappingService;
	
	@Override
	public void run(String... args) throws Exception {
		this.initializeSystemData();
		this.initializeAccessPermission();
		this.initializeSystemMenu();
		this.requestMappingService.refreshCachedData();
	}

	private void initializeSystemMenu() {
		this.menuService.refreshNavigationMenu();
	}

	private void initializeAccessPermission() {
		this.securityMetadataSource.refreshRequestMap();
	}

	private void initializeSystemData() {
		if (!this.systemInitializeService.isSystemInitialized()) {
			this.systemInitializeService.initializingSystemSituatiopn();
			this.systemInitializeService.initMenuAndResource();
			this.systemInitializeService.initAuthority();
			this.systemInitializeService.initRole();
			this.systemInitializeService.initAdminUser();
			this.systemInitializeService.initializedSystemSituatiopn();
		}
	}
	
}
