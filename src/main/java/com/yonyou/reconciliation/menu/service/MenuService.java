package com.yonyou.reconciliation.menu.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.yonyou.reconciliation.authority.entity.Authority;
import com.yonyou.reconciliation.menu.dto.NavigationMenuDto;
import com.yonyou.reconciliation.menu.dto.SelectedResourceDto;
import com.yonyou.reconciliation.menu.entity.Menu;
import com.yonyou.reconciliation.menu.repository.MenuRepository;
import com.yonyou.reconciliation.resource.entity.Resource;
import com.yonyou.reconciliation.resource.repository.ResourceRepository;

@Service
public class MenuService {

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private ResourceRepository resourceRepository;

	@Autowired
	private ServletContext servletContext;

	private static String MENU_ATTRIBUTE_NAME = "__SYSTEM_MENU";

	@Transactional
	public void saveAndUpdate(Menu menu) {
		this.menuRepository.save(menu);
	}

	@Transactional
	public void refreshNavigationMenu() {
		Menu rootMenu = this.menuRepository.findByParent(null);
		if (rootMenu != null) {
			NavigationMenuDto menuDto = this.createNavigationMenuDto(rootMenu);
			this.setNavigationMenuDtoReference(menuDto, rootMenu);
			putMenuIntoServletContext(menuDto);
		}
	}

	private void setNavigationMenuDtoReference(NavigationMenuDto menuDto, Menu menu) {
		for (Menu menu1 : menu.getChildren()) {
			NavigationMenuDto menuDto1 = this.createNavigationMenuDto(menu1);
			menuDto1.setParent(menuDto);
			menuDto.getChildren().add(menuDto1);
			this.setNavigationMenuDtoReference(menuDto1, menu1);
		}
	}

	private NavigationMenuDto createNavigationMenuDto(Menu menu) {
		NavigationMenuDto menuDto = new NavigationMenuDto();
		menuDto.setId(menu.getId());
		menuDto.setMenuName(menu.getName());
		menuDto.setIconType(menu.getIconType());
		menuDto.setIconPath(menu.getIconPath());
		menuDto.setUrl(menu.getUrl());
		menuDto.getAuthorities().addAll(this.extractAuthorityMarkFromResource(menu));
		return menuDto;
	}

	private Set<String> extractAuthorityMarkFromResource(Menu menu) {
		Set<String> result = new HashSet<String>();
		for (Resource resource : menu.getResources()) {
			for (Authority authority : resource.getAuthorities()) {
				result.add(authority.getAuthorityCode());
			}
		}
		return result;
	}

	private void putMenuIntoServletContext(NavigationMenuDto menuDto) {
		if (this.servletContext != null) {
			servletContext.setAttribute(MENU_ATTRIBUTE_NAME, menuDto);
		}
	}

	public List<Menu> loadByParentId(Long id) {
		return this.menuRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
			return id == null ? criteriaBuilder.isNull(root.get("parent")) : criteriaBuilder.equal(root.get("parent").get("id"), id);
		}, new Sort(Sort.Direction.ASC, "id"));
	}

	@Transactional
	public void deleteById(Long id) {
		Menu menu = this.menuRepository.findOne(id);
		
		for (Resource resource : menu.getResources()) {
			resource.getMenus().remove(menu);
		}
		
		this.menuRepository.delete(menu);
	}

	public List<SelectedResourceDto> getSelectedResourceByMenuId(Long menuId) {
		List<Resource> allResource = this.resourceRepository.findAll();
		List<Resource> selectedResources = this.menuRepository.findOne(menuId).getResources();
		List<SelectedResourceDto> result = new ArrayList<SelectedResourceDto>();

		for (Resource resource : allResource) {
			SelectedResourceDto selectedResourceDto = new SelectedResourceDto();
			selectedResourceDto.setResourceId(resource.getId());
			selectedResourceDto.setResourceName(resource.getResourceName());
			
			selectedResourceDto.setSelected(selectedResources.stream().anyMatch(
					selectedResource -> 
						selectedResource.getId() == resource.getId()
					));
			result.add(selectedResourceDto);
		}

		return result;
	}
	
	@Transactional
	public void updateMenuResoure(Long menuId, List<Long> resourceIds) {
		Menu menu = this.menuRepository.findOne(menuId);
		List<Resource> resourceList = this.resourceRepository.findAll(resourceIds);
		menu.getResources().clear();
		menu.getResources().addAll(resourceList);
		this.menuRepository.save(menu);
	}
	
	public Set<Menu> findMenusByRoleId(Long roleId) {
		
		
		return null;
	}
	
}
