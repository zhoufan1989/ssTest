package com.sys.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.base.controller.BaseController;
import com.sys.dto.SysMenuDTO;
import com.sys.dto.SysRoleDTO;
import com.sys.dto.SysUserDTO;
import com.sys.service.SysMenuService;
import com.sys.service.SysUserService;
import com.util.PageUtils;
import com.util.ShiroUtils;
import com.util.constant.Constant.MenuType;
import com.util.exception.RRException;


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
	 * 获取不是按钮类型的的资源菜单
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:menu:select")
	public Object select() {
		Query query = Query.query(Criteria.where("type").nin(2));
		List<SysMenuDTO> menuList = sysMenuService.queryAll(query, SysMenuDTO.class);
		//添加顶级菜单
		SysMenuDTO menu = new SysMenuDTO();
		menu.setMenuId(0);
		menu.setName("一级菜单");
		menu.setParentId(-1);
		menu.setOpen(true);
		menuList.add(menu);
		return putData("menuList", menuList);
	}
	
	/**
	 * 菜单信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:menu:info")
	public Object info(@PathVariable("id") String id) {
		SysMenuDTO menu = sysMenuService.queryMenuById(id);
		return putData("menu", menu);
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
	
	/**
	 * 资源菜单分页列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:menu:list")
	public Object menuPageList(int page, int limit,String name) {
		PageUtils pageSelect = new PageUtils();
		pageSelect.setCurrPage(page);	//当前页数
		pageSelect.setPageSize(limit);  //每页记录数
		Pageable pageable = new PageRequest(page - 1, limit, Direction.DESC, "_id");
		Query query = new Query();
		if(StringUtils.isNotBlank(name)) {
			Pattern pattern = Pattern.compile(".*?" + name.trim() + ".*?");
			Criteria criteria = new Criteria();
			query.addCriteria(criteria.orOperator(Criteria.where("name").regex(pattern)));
			
		}
		Page<SysMenuDTO> pageResult = sysMenuService.queryAllBy(query, pageable, SysMenuDTO.class);
		return putPageData(pageSelect, pageResult);
	}
	
	@RequestMapping("/save")
	@RequiresPermissions("sys:menu:save")
	public Object save(@RequestBody SysMenuDTO menu) {
		checkParameter(menu);
		List<SysMenuDTO> list = sysMenuService.queryAll();
		List<Integer> menuList = new ArrayList<>();
		for(SysMenuDTO sysMenu : list) {
			menuList.add(sysMenu.getMenuId());
		}
		int i = Collections.max(menuList);
		menu.setMenuId(i+1);
		sysMenuService.insert(menu);
		return putData();
	}
	
	@RequestMapping("/update")
	@RequiresPermissions("sys:menu:update")
	public Object update(@RequestBody SysMenuDTO menu) {
		checkParameter(menu);
		sysMenuService.update(menu);
		return putData();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:menu:delete")
	public Object delete(@RequestBody String[] ids) {
		sysMenuService.deleteBatch(ids);
		return putData();
	}
	
	/**
	 * 验证参数是否正确
	 */
	private void checkParameter(SysMenuDTO menu) {
		if (StringUtils.isBlank(menu.getName())) {
			throw new RRException("菜单名称不能为空");
		}

		// 菜单
		if (menu.getType() == MenuType.MENU.getValue()) {
			if (StringUtils.isBlank(menu.getUrl())) {
				throw new RRException("菜单URL不能为空");
			}
		}

		// 上级菜单类型
		int parentType = MenuType.CATALOG.getValue();
		if (menu.getParentId() != 0) {
			SysMenuDTO parentMenu = sysMenuService.queryMenuByMenuId(menu.getParentId());
			parentType = parentMenu.getType();
		}

		// 目录、菜单
		if (menu.getType() == MenuType.CATALOG.getValue() || menu.getType() == MenuType.MENU.getValue()) {
			if (parentType != MenuType.CATALOG.getValue()) {
				throw new RRException("上级菜单只能为目录类型");
			}
			return;
		}

		// 按钮
		if (menu.getType() == MenuType.BUTTON.getValue()) {
			if (parentType != MenuType.MENU.getValue()) {
				throw new RRException("上级菜单只能为菜单类型");
			}
			return;
		}
	}
}
