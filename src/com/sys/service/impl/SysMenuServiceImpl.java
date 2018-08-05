package com.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.base.service.impl.BaseServiceImpl;
import com.sys.dto.SysMenuDTO;
import com.sys.mapper.menu.SysMenuMapper;
import com.sys.service.SysMenuService;

import com.util.constant.Constant.MenuType;

@Service("sysMenuService")
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuDTO, String> implements SysMenuService{
	
	@Autowired
	private SysMenuMapper sysMenuMapper;
	
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(sysMenuMapper);
	}
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 * @param menuIdList 用户菜单ID
	 */
	@Override
	public List<SysMenuDTO> queryListParentId(int parentId, List<Integer> menuIdList) {
		List<SysMenuDTO> menuList = sysMenuMapper.findMenuByParentId(parentId);
		if(StringUtils.isEmpty(menuIdList)) {
			return menuList;
		}
		
		List<SysMenuDTO> userMenuList = new ArrayList<SysMenuDTO>();
		for(SysMenuDTO menu : menuList) {
			if(menuIdList.contains(menu.getMenuId())) {
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}
	
	
	@Override
	public List<SysMenuDTO> getAllMenuList(List<Integer> menuIdList) {
		//查询根菜单列表
		List<SysMenuDTO> menuList = queryListParentId(0, menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);
		return menuList;
	}
	
	/**
	 * 菜单关系递归
	 */
	private List<SysMenuDTO> getMenuTreeList(List<SysMenuDTO> menuList, List<Integer> menuIdList){
		List<SysMenuDTO> subMenuList = new ArrayList<SysMenuDTO>();
		for(SysMenuDTO menu : menuList) {
			if(menu.getType() == MenuType.CATALOG.getValue()) {
				menu.setList(getMenuTreeList(queryListParentId(menu.getMenuId(), menuIdList), menuIdList));
			}
			subMenuList.add(menu);
		}
		
		return subMenuList;
	}

	@Override
	public List<SysMenuDTO> queryNotButtonList() {
		return null;
	}

	@Override
	public List<SysMenuDTO> getUserMenuList(String id) {
		return null;
	}

	@Override
	public SysMenuDTO queryObject(String id) {
		return null;
	}

	@Override
	public boolean insert(SysMenuDTO sysMenuDTO) {
		sysMenuMapper.save(sysMenuDTO);
		return true;
	}

	@Override
	public SysMenuDTO queryMenuByMenuId(int menuId) {
		SysMenuDTO menu = sysMenuMapper.findMenuByMenuId(menuId);
		return menu;
	}

	
	
}
