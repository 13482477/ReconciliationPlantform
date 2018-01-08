package com.yonyou.reconciliation.menu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.reconciliation.icon.entity.Icon;
import com.yonyou.reconciliation.icon.service.IconService;
import com.yonyou.reconciliation.menu.dto.MenuDto;
import com.yonyou.reconciliation.menu.dto.MenuTreeNode;
import com.yonyou.reconciliation.menu.dto.SelectedResourceDto;
import com.yonyou.reconciliation.menu.entity.Menu;
import com.yonyou.reconciliation.menu.repository.MenuRepository;
import com.yonyou.reconciliation.menu.service.MenuService;
import com.yonyou.reconciliation.menu.validator.MenuDtoValidator;
import com.yonyou.reconciliation.security.metadatasource.DatabaseMetadataSource;
import com.yonyou.reconciliation.web.convert.ConvertUtils;

@Controller
public class MenuController {
	
	private static final String MENU_OPEN_STATUS = "__MENU_OPEN_STATUS";

	@Autowired
	private MenuService menuService;

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private IconService iconService;

	@Autowired
	private MenuDtoValidator menuDtoValidator;
	
	@Autowired
	private DatabaseMetadataSource databaseMetadataSource;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(menuDtoValidator);
	}

	@RequestMapping(value = "/menu/page", method = RequestMethod.GET)
	public String resource(Model model) {

		LinkedHashMap<String, List<Icon>> fontAwesomeMap = this.iconService.findFontAwesomeGroupBySubGroup();
		List<Icon> glyphicons = this.iconService.findGlyphicons();

		model.addAttribute("fontAwesomeMap", fontAwesomeMap);
		model.addAttribute("glyphicons", glyphicons);

		return "menu/menu";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/menu/refresh", method = RequestMethod.POST)
	@ResponseBody
	public void refreshMenu(@RequestParam Long menuId, @RequestParam Boolean isOpen, HttpSession session) {
		session.setAttribute(MENU_OPEN_STATUS, session.getAttribute(MENU_OPEN_STATUS) == null ? new HashMap<Long, Boolean>() : session.getAttribute(MENU_OPEN_STATUS));
		if (isOpen) {
			((Map<Long, Boolean>)session.getAttribute(MENU_OPEN_STATUS)).put(menuId, isOpen);
		} else {
			((Map<Long, Boolean>)session.getAttribute(MENU_OPEN_STATUS)).remove(menuId);
		}
	}

	@RequestMapping(value = "/menu/children", method = RequestMethod.GET)
	@ResponseBody
	public List<MenuTreeNode> loadChildren(@RequestParam(value = "parentId", required = false) Long parentId) {
		List<Menu> children = this.menuService.loadByParentId(parentId);

		List<MenuTreeNode> result = new ArrayList<MenuTreeNode>();

		CollectionUtils.collect(children, input -> {
			MenuTreeNode menuTreeNode = new MenuTreeNode();
			menuTreeNode.setId(input.getId());
			menuTreeNode.setName(input.getName());
			menuTreeNode.setIsParent(input.getChildren().size() > 0);
			menuTreeNode.setUrl(input.getUrl());
			menuTreeNode.setCode(input.getMenuCode());
			menuTreeNode.setIconPath(input.getIconPath());
			menuTreeNode.setParentId(input.getParent() == null ? null : input.getParent().getId());
			return menuTreeNode;
		}, result);

		return result;
	}

	@RequestMapping(value = "/menu", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<MenuDto> create(@RequestBody @Validated MenuDto menuDto) {
		Menu menu = menuDto.createEntity();
		menu.setParent(menuDto.getParentId() == null ? null : this.menuRepository.findOne(menuDto.getParentId()));
		this.menuService.saveAndUpdate(menu);
		
		this.menuService.refreshNavigationMenu();
		return ResponseEntity.ok(ConvertUtils.convert(menu, input -> {
			MenuDto result = new MenuDto();
			result.setId(input.getId());
			result.setName(input.getName());
			result.setMenuCode(input.getMenuCode());
			result.setSystemId(input.getSystemId());
			result.setUrl(input.getUrl());
			result.setSequence(input.getSequence());
			result.setIconPath(input.getIconPath());
			return result;
		}));
	}
	
	@RequestMapping(value = "/menu/{id}", method = RequestMethod.GET)
	@ResponseBody
	public MenuDto read(@PathVariable Long id) {
		Menu menu = this.menuRepository.findOne(id);

		return ConvertUtils.convert(menu, source -> {
			MenuDto menuDto = new MenuDto();
			menuDto.setId(source.getId());
			menuDto.setName(source.getName());
			menuDto.setMenuCode(source.getMenuCode());
			menuDto.setSystemId(source.getSystemId());
			menuDto.setUrl(source.getUrl());
			menuDto.setSequence(source.getSequence());
			menuDto.setIconPath(source.getIconPath());
			menuDto.setParentId(source.getParent() == null ? null : menu.getParent().getId());
			return menuDto;
		});
	}
	
	@RequestMapping(value = "/menu/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<MenuDto> update(@PathVariable Long id, @RequestBody @Validated MenuDto menuDto) {
		Menu menu = this.menuRepository.findOne(id);
		
		menu.setName(menuDto.getName());
		menu.setMenuCode(menuDto.getMenuCode());
		menu.setIconPath(menuDto.getIconPath());
		
		this.menuService.saveAndUpdate(menu);
		this.menuService.refreshNavigationMenu();
		
		return ResponseEntity.ok(ConvertUtils.convert(menu, input -> {
			MenuDto result = new MenuDto();
			result.setId(input.getId());
			result.setName(input.getName());
			result.setMenuCode(input.getMenuCode());
			result.setSystemId(input.getSystemId());
			result.setUrl(input.getUrl());
			result.setSequence(input.getSequence());
			result.setIconPath(input.getIconPath());
			return result;
		}));
	}
	
	@RequestMapping(value = "/menu/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@PathVariable Long id) {
		this.menuService.deleteById(id);
		this.menuService.refreshNavigationMenu();
	}
	
	@RequestMapping(value = "/menu/{menuId}/selectedMenus", method = RequestMethod.GET)
	@ResponseBody
	public List<SelectedResourceDto> getSelectedResource(@PathVariable Long menuId) {
		return this.menuService.getSelectedResourceByMenuId(menuId);
	}
	
	@RequestMapping(value = "/menu/{menuId}/selectedMenus", method = RequestMethod.PUT)
	@ResponseBody
	public void updateSelectedResource(@PathVariable Long menuId, @RequestParam("resourceIds[]") List<Long> resourceIds) {
		this.menuService.updateMenuResoure(menuId, resourceIds);
		this.databaseMetadataSource.refreshRequestMap();
	}
	
	
	

}