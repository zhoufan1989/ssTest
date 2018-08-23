package com.sys.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.controller.BaseController;
import com.sys.dto.SysMenuDTO;
import com.sys.dto.SysRoleDTO;
import com.sys.service.SysMenuService;
import com.sys.service.SysRoleService;
import com.util.PageUtils;

/**
 * 角色管理
 * @author Zhoufan
 * @Date 2018.08.21
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController{
	
	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private SysMenuService sysMenuService;
	
	/**
	 * 角色分页列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:role:list")
	public Object getRoleList(int page, int limit,String roleName) {
		PageUtils pageSelect = new PageUtils();
		pageSelect.setCurrPage(page);	//当前页数
		pageSelect.setPageSize(limit);  //每页记录数
		Pageable pageable = new PageRequest(page - 1, limit, Direction.DESC, "_id");
		Query query = new Query();
		if(StringUtils.isNotBlank(roleName)) {
			Pattern pattern = Pattern.compile(".*?" + roleName.trim() + ".*?");
			Criteria criteria = new Criteria();
			query.addCriteria(criteria.orOperator(Criteria.where("roleName").regex(pattern)));
			
		}
		
		Page<SysRoleDTO> pageResult = sysRoleService.queryAllBy(query, pageable, SysRoleDTO.class);
		return putPageData(pageSelect, pageResult);
	}
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:role:select")
	public Object select() {
		List<SysRoleDTO> roles = sysRoleService.queryAll();
		return putData("list",roles);
	}
	
	/**
	 * 角色信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:role:info")
	public Object roleInfo(@PathVariable("id") String id) {
		SysRoleDTO role = sysRoleService.queryRoleById(id);
		return putData("role",role);
	}
	
	/**
	 * 保存角色
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:role:save")
	public Object save(@RequestBody SysRoleDTO role) {
		if(StringUtils.isEmpty(role.getRoleName())) {
			return error("角色名称不为空");
		}
		List<Integer> menuIdList = role.getMenuIdList();
		List<SysMenuDTO> menuList = new ArrayList<>();
		for(Integer menuId : menuIdList) {
			SysMenuDTO menu = sysMenuService.queryMenuByMenuId(menuId);
			menuList.add(menu);
		}
		role.setMenuList(menuList);
		sysRoleService.insert(role);
		return putData();
	}
	
	@RequestMapping("/update")
	@RequiresPermissions("sys:role:update")
	public Object update(@RequestBody SysRoleDTO role) {
		if(StringUtils.isEmpty(role.getRoleName())) {
			return error("角色名称不为空");
		}
		List<Integer> menuIdList = role.getMenuIdList();
		List<SysMenuDTO> menuList = new ArrayList<>();
		for(Integer menuId : menuIdList) {
			SysMenuDTO menu = sysMenuService.queryMenuByMenuId(menuId);
			menuList.add(menu);
		}
		role.setMenuList(menuList);
		sysRoleService.update(role);
		return putData();
	}
	
	/**
	 * 删除角色
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public Object delete(@RequestBody String[] ids) {
		sysRoleService.deleteBatch(ids);
		return putData();
	}
}
