package com.sys.junit;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSON;
import com.sys.dto.SysMenuDTO;
import com.sys.dto.SysRoleDTO;
import com.sys.dto.SysUserDTO;
import com.sys.service.SysMenuService;
import com.sys.service.SysRoleService;
import com.sys.service.SysUserService;
import com.util.EndecryptUtils;

@RunWith(SpringJUnit4ClassRunner.class) //让测试运行于Spring环境  
@ContextConfiguration({"classpath*:spring/spring-applicationContext.xml"}) //引入Spring配置 
@WebAppConfiguration
public class CarTest {
	
	
//	@Autowired
//	private UserService userService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private SysMenuService sysMenuService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SysRoleService sysRoleService;

	
	@Test
	public void addUser() {

		
		SysUserDTO user = new SysUserDTO();
		user.setName("122");
		user.setUserName("admin");
		user.setPassword(new Sha256Hash("123456").toHex());
		
//		SysUserDTO userDTO =  EndecryptUtils.md5Password(user.getUserName(), user.getPassword(), EndecryptUtils.ITERATION);
//		user.setUserName(userDTO.getUserName());
//		
//		user.setCredentialsSalt(userDTO.getCredentialsSalt());
		user.setCreateTime(new Date());
		sysUserService.insert(user);
	}
	
	
	
	@Test
	public void addUsers() {
//		SysUserDTO user = new SysUserDTO();
//		user.setName("122");
//		user.setUserName("admin");
//		
//		user.setCreateTime(new Date());
//		user.setPassword(new Sha256Hash("123456").toHex());
//		sysUserService.insert(user);
		
		SysUserDTO user = new SysUserDTO();
		user.setName("zhoufan");
		user.setUserName("zhoufan");
		user.setUserId(2);
		user.setCreateTime(new Date());
		user.setPassword(new Sha256Hash("123456").toHex());
		
		SysRoleDTO role = new SysRoleDTO();
		role.setRoleId(1);
		SysRoleDTO roleNew = sysRoleService.queryRoleByRoleId(role.getRoleId());
		role.setId(roleNew.getId());
		List<SysRoleDTO> list = new ArrayList<>();
		list.add(role);
		user.setRoleIdList(list);
		sysUserService.insert(user);
	}
	
	@Test
	public void updateUsers() {
		SysUserDTO user = sysUserService.queryByUserName("admin");
		user.setUserId(1);
		sysUserService.update(user);
	}
	
	@Test
	public void addMenus() {
		String sql = "select * from sys_menu";
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
		System.out.println(JSON.toJSONString(list));
		for(Map<String, Object> map : list) {
			SysMenuDTO menu = new SysMenuDTO();
			menu.setMenuId(Integer.valueOf(map.get("menu_id").toString()));
			menu.setParentId(Integer.valueOf(map.get("parent_id").toString()));
			menu.setName(JSON.toJSONString(map.get("name")));
			menu.setType(Integer.valueOf(map.get("type").toString()));
			menu.setIcon(JSON.toJSONString(map.get("icon")));
			menu.setOrderNum(Integer.valueOf(map.get("order_num").toString()));
			if(StringUtils.isNotBlank(JSON.toJSONString(map.get("url")))) {
				menu.setUrl(JSON.toJSONString(map.get("url")));
			}else {
				menu.setUrl(menu.getUrl() == null ? "": menu.getUrl());
			}
			menu.setPerms(JSON.toJSONString(map.get("perms")));
			sysMenuService.insert(menu);
		}
		
	}
	
	@Test
	public void addRole() {
		SysRoleDTO role = new SysRoleDTO();
		role.setRoleId(1);
		role.setRoleName("超级管理员");
		role.setRemark("超级管理员，拥有所有权限");
		List<SysMenuDTO> menuIds = new ArrayList<>();
		
		String sql = "select * from sys_role_menu";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		for(Map<String, Object> map :list){
			if(Integer.valueOf(map.get("role_id").toString()) == 1){
//				System.out.println(JSON.toJSONString(map.get("menu_id")));
				String menuIdStr = JSON.toJSONString(map.get("menu_id"));
				
				SysMenuDTO menu = new SysMenuDTO();
				int menuId = Integer.valueOf(menuIdStr);
				menu.setMenuId(menuId);
				SysMenuDTO menuOld = sysMenuService.queryMenuByMenuId(menuId);
				menu.setId(menuOld.getId());
				menuIds.add(menu);
			}
		}
		role.setMenuList(menuIds);;
		sysRoleService.insert(role);
	}
	
	@Test
	public void findMenu() {
		int menuId = 1;
		SysMenuDTO menuOld = sysMenuService.queryMenuByMenuId(menuId);
		System.out.println(">>>" + JSON.toJSONString(menuOld));
	}
}
