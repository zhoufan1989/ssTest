package com.sys.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.controller.BaseController;
import com.sys.dto.SysMenuDTO;
import com.sys.dto.SysRoleDTO;
import com.sys.dto.SysUserDTO;
import com.sys.service.SysMenuService;
import com.sys.service.SysUserService;


/**
 * 系统菜单
 * @author Zhoufan
 * @Date 2018-07-17 17:05:00
 *
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController{
	
	@Autowired
	private SysMenuService sysMenuService;
	
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 用户菜单列表
	 */
	@RequestMapping("/user")
	public Object getMenuByUserId() {
		String userName = (String) SecurityUtils.getSubject().getPrincipal();
		SysUserDTO user = sysUserService.queryByUserName(userName);
		List<SysMenuDTO> menuList = new ArrayList<>();
		List<Integer> menuIds = new ArrayList<>();
		
		if(StringUtils.equals(userName, "admin")) { // 系统管理员，拥有最高权限
			menuList = sysMenuService.getAllMenuList(null);
			
		}else {	//其他用户的菜单列表											
			
			List<SysRoleDTO> roleList = user.getRoleIdList();
			for(SysRoleDTO role : roleList) {
				List<SysMenuDTO> menus = role.getMenuList();
				for(SysMenuDTO menu : menus) {
					menuIds.add(menu.getMenuId());
				}
			}
			menuList = sysMenuService.getAllMenuList(menuIds);
		}
		
		return putData("menuList", menuList);
	}
	
	/**
	 * 角色授权菜单
	 */
	@RequestMapping("/perms")
	@RequiresPermissions("sys:menu:perms")
	public Object perms() {
		List<SysMenuDTO> menuList = sysMenuService.queryAll();
		return putData("menuList", menuList);
	}
	
}
