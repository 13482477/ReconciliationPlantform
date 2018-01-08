package com.yonyou.reconciliation.menu.dto;

import com.yonyou.reconciliation.menu.entity.IconType;
import com.yonyou.reconciliation.menu.entity.Menu;

public class MenuDto {
	
	private Long id;
	
	private String systemId;
	
	private String name;
	
	private String menuCode;
	
	private String url;
	
	private Integer sequence;
	
	private String iconPath;
	
	private Long parentId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Menu createEntity() {
		Menu menu = new Menu();
		menu.setId(this.id);
		menu.setName(this.name);
		menu.setMenuCode(this.menuCode);
		menu.setIconPath(this.iconPath);
		menu.setIconType(IconType.ICON);
		return menu;
	}

}
