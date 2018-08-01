package com.sys.service;

import java.util.List;

import com.base.service.BaseService;
import com.sys.dto.SysMenuDTO;

public interface SysMenuService extends BaseService<SysMenuDTO, String>{
	
	/**
	 * 根据父级菜单，查询子级菜单
	 * @param parentId 父级菜单ID
	 * @param menuIdList 用户菜单ID
	 * @return
	 */
	public List<SysMenuDTO> queryListParentId(int parentId, List<Integer> menuIdList);
	
	/**
	 * 获取不包含按钮的菜单列表
	 * @return
	 */
	public List<SysMenuDTO> queryNotButtonList();
	
	/**
	 * 获取用户菜单列表
	 * @param id 用户记录ID
	 * @return
	 */
	public List<SysMenuDTO> getUserMenuList(String id);
	
	/**
	 * 查询菜单
	 * @param id 菜单记录ID
	 * @return 
	 */
	public SysMenuDTO queryObject(String id);
	
	/**
	 * 
	 * 添加菜单信息
	 */
	public boolean insert(SysMenuDTO sysMenuDTO);
	
	/**
	 * 根据菜单ID查询出
	 */
	public SysMenuDTO queryMenuByMenuId(int menuId);
	
	/**
	 * 获取所有菜单列表
	 * @param menuIdList
	 * @return
	 */
	public List<SysMenuDTO> getAllMenuList(List<Integer> menuIdList);
}
