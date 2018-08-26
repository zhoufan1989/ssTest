package com.sys.junit;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sys.dto.SysMenuDTO;
import com.sys.dto.SysRoleDTO;
import com.sys.dto.SysUserDTO;
import com.sys.service.SysMenuService;
import com.sys.service.SysRoleService;
import com.sys.service.SysUserService;

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
	
	/**
	 * 添加资源目录
	 */
	@Test
	public void addMenu() {
//		String menusql = "{\"menuId\":1,\"parentId\":0,\"name\":\"系统管理\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"fa fa-cog\",\"orderNum\":0}";
		
        String menu3 = "{\"menuId\":2,\"parentId\":1,\"name\":\"管理员列表\",\"url\":\"sys/user.html\",\"perms\":\"\",\"type\":1,\"icon\":\"fa fa-user\",\"orderNum\":1}";
        String menu4 ="{\"menuId\":3,\"parentId\":1,\"name\":\"角色管理\",\"url\":\"sys/role.html\",\"perms\":\"\",\"type\":1,\"icon\":\"fa fa-user-secret\",\"orderNum\":2}";
        String menu5 ="{\"menuId\":4,\"parentId\":1,\"name\":\"菜单管理\",\"url\":\"sys/menu.html\",\"perms\":\"\",\"type\":1,\"icon\":\"fa fa-th-list\",\"orderNum\":3}";
        String menu6 ="{\"menuId\":5,\"parentId\":1,\"name\":\"SQL监控\",\"url\":\"druid/sql.html\",\"perms\":\"\",\"type\":1,\"icon\":\"fa fa-bug\",\"orderNum\":4}";
        String menu7 ="{\"menuId\":6,\"parentId\":1,\"name\":\"定时任务\",\"url\":\"sys/schedule.html\",\"perms\":\"\",\"type\":1,\"icon\":\"fa fa-tasks\",\"orderNum\":5}";
        String menu8 ="{\"menuId\":7,\"parentId\":6,\"name\":\"查看\",\"url\":\"\",\"perms\":\"sys:schedule:list,sys:schedule:info\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";
        String menu9 ="{\"menuId\":8,\"parentId\":6,\"name\":\"新增\",\"url\":\"\",\"perms\":\"sys:schedule:save\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";
        String menu10 ="{\"menuId\":9,\"parentId\":6,\"name\":\"修改\",\"url\":\"\",\"perms\":\"sys:schedule:update\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";
        String menu11 ="{\"menuId\":10,\"parentId\":6,\"name\":\"删除\",\"url\":\"\",\"perms\":\"sys:schedule:delete\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";
        String menu12 ="{\"menuId\":11,\"parentId\":6,\"name\":\"暂停\",\"url\":\"\",\"perms\":\"sys:schedule:pause\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";
        String menu13 ="{\"menuId\":12,\"parentId\":6,\"name\":\"恢复\",\"url\":\"\",\"perms\":\"sys:schedule:resume\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";
        String menu14 ="{\"menuId\":13,\"parentId\":6,\"name\":\"立即执行\",\"url\":\"\",\"perms\":\"sys:schedule:run\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";
        String menu15 ="{\"menuId\":14,\"parentId\":6,\"name\":\"日志列表\",\"url\":\"\",\"perms\":\"sys:schedule:log\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";
		String menu16 ="{\"menuId\":15,\"parentId\":2,\"name\":\"查看\",\"url\":\"\",\"perms\":\"sys:user:list,sys:user:info\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";
		String menu17 ="{\"menuId\":16,\"parentId\":2,\"name\":\"新增\",\"url\":\"\",\"perms\":\"sys:user:save,sys:role:select\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";
		String menu18 ="{\"menuId\":17,\"parentId\":2,\"name\":\"修改\",\"url\":\"\",\"perms\":\"sys:user:update,sys:role:select\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";
		String menu19 ="{\"menuId\":18,\"parentId\":2,\"name\":\"删除\",\"url\":\"\",\"perms\":\"sys:user:delete\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";
		String menu20 ="{\"menuId\":19,\"parentId\":3,\"name\":\"查看\",\"url\":\"\",\"perms\":\"sys:role:list,sys:role:info\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";
		String menu21 ="{\"menuId\":20,\"parentId\":3,\"name\":\"新增\",\"url\":\"\",\"perms\":\"sys:role:save,sys:menu:perms\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";
		String menu22 ="{\"menuId\":21,\"parentId\":3,\"name\":\"修改\",\"url\":\"\",\"perms\":\"sys:role:update,sys:menu:perms\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";
		String menu23 ="{\"menuId\":22,\"parentId\":3,\"name\":\"删除\",\"url\":\"\",\"perms\":\"sys:role:delete\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";
		String menu24 ="{\"menuId\":23,\"parentId\":4,\"name\":\"查看\",\"url\":\"\",\"perms\":\"sys:menu:list,sys:menu:info\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";
		String menu25 ="{\"menuId\":24,\"parentId\":4,\"name\":\"新增\",\"url\":\"\",\"perms\":\"sys:menu:save,sys:menu:select\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";
		String menu26 ="{\"menuId\":25,\"parentId\":4,\"name\":\"修改\",\"url\":\"\",\"perms\":\"sys:menu:update,sys:menu:select\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";
		String menu27 ="{\"menuId\":26,\"parentId\":4,\"name\":\"删除\",\"url\":\"\",\"perms\":\"sys:menu:delete\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";
		String menu28 ="{\"menuId\":27,\"parentId\":1,\"name\":\"参数管理\",\"url\":\"sys/config.html\",\"perms\":\"sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete\",\"type\":1,\"icon\":\"fa fa-sun-o\",\"orderNum\":6}";
		String menu29 ="{\"menuId\":28,\"parentId\":1,\"name\":\"代码生成器\",\"url\":\"sys/generator.html\",\"perms\":\"sys:generator:list,sys:generator:code\",\"type\":1,\"icon\":\"fa fa-rocket\",\"orderNum\":7}";
		String menu30 ="{\"menuId\":29,\"parentId\":0,\"name\":\"智能家居\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"glyphicon glyphicon-cd\",\"orderNum\":1}";
		String menu31 ="{\"menuId\":30,\"parentId\":29,\"name\":\"产品管理\",\"url\":\"smart/product.html\",\"perms\":\"smart:product:list,smart:product:info\",\"type\":1,\"icon\":\"glyphicon glyphicon-th\",\"orderNum\":1}";
		String menu32 ="{\"menuId\":31,\"parentId\":30,\"name\":\"查看\",\"url\":\"\",\"perms\":\"smart:product:list,smart:product:info\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";
		String menu33 ="{\"menuId\":32,\"parentId\":30,\"name\":\"新增\",\"url\":\"\",\"perms\":\"smart:product:save,smart:product:info\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";
		String menu34 ="{\"menuId\":33,\"parentId\":30,\"name\":\"修改\",\"url\":\"\",\"perms\":\"smart:product:update,smart:product:info\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";
		String menu35 ="{\"menuId\":34,\"parentId\":30,\"name\":\"删除\",\"url\":\"\",\"perms\":\"smart:product:delete\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";
		String menu36 ="{\"menuId\":35,\"parentId\":29,\"name\":\"产品类别\",\"url\":\"smart/productType.html\",\"perms\":\"smart:productType:list,smart:productType:info\",\"type\":1,\"icon\":\"glyphicon glyphicon-move\",\"orderNum\":0}";
		String menu37 ="{\"menuId\":36,\"parentId\":36,\"name\":\"新增\",\"url\":\"\",\"perms\":\"smart:productType:save,smart:productType:info\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";
		String menu38 ="{\"menuId\":37,\"parentId\":35,\"name\":\"修改\",\"url\":\"\",\"perms\":\"smart:productType:update,smart:productType:info\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";
		String menu39 ="{\"menuId\":38,\"parentId\":35,\"name\":\"删除\",\"url\":\"\",\"perms\":\"smart:productType:delete\",\"type\":2,\"icon\":\"\",\"orderNum\":0}";

		List<String> list = new ArrayList<>();
		list.add(menu3);
		list.add(menu4);
		list.add(menu5);
		list.add(menu6);
		list.add(menu7);
		list.add(menu8);
		list.add(menu9);
		list.add(menu10);
		list.add(menu11);
		list.add(menu12);
		list.add(menu13);
		list.add(menu14);
		list.add(menu15);
		list.add(menu16);
		list.add(menu17);
		list.add(menu18);
		list.add(menu19);
		list.add(menu20);
		list.add(menu21);
		list.add(menu22);
		list.add(menu23);
		list.add(menu24);
		list.add(menu25);
		list.add(menu26);
		list.add(menu27);
		list.add(menu28);
		list.add(menu29);
		list.add(menu30);
		list.add(menu31);
		list.add(menu32);
		list.add(menu33);
		list.add(menu34);
		list.add(menu35);
		list.add(menu36);
		list.add(menu37);
		list.add(menu38);
		list.add(menu39);
		
		for(String json : list) {
			SysMenuDTO menu = JSONObject.parseObject(json, SysMenuDTO.class);
			sysMenuService.insert(menu);
		}
	}
	
	/**
	 * 添加角色
	 */
	@Test
	public void addRoles() {
		SysRoleDTO role = new SysRoleDTO();
		role.setRoleId(1);
		role.setRemark("超级管理,拥有所有权限");
		role.setRoleName("超级管理员");
		List<SysMenuDTO> menuList = sysMenuService.queryAll();
		role.setMenuList(menuList);
		role.setAddDate(new Date());
		sysRoleService.insert(role);
	}
	
	/**
	 * 添加用户
	 */
	@Test
	public void addUserdto() {
		SysUserDTO user = new SysUserDTO();
		user.setCreateTime(new Date());
		user.setName("admin");
		user.setUserName("admin");
		user.setStatus(1);
		user.setPassword("123456");
		List<SysRoleDTO> roleList = sysRoleService.queryAll();
		user.setRoleIdList(roleList);
		sysUserService.insert(user);
	}
	
	@Test
	public void queryUser() {
		String userName = "zhou";
		List<SysUserDTO> userList = null;
		
		Query query = new Query();
		if(StringUtils.isNotBlank(userName)) {
			Pattern pattern = Pattern.compile(".*?" + userName.trim() + ".*?");
			Criteria criteria = new Criteria();
			query.addCriteria(criteria.orOperator(Criteria.where("userName").regex(pattern)));
			userList = sysUserService.queryAll(query, SysUserDTO.class);
	   }else {
		    userList =  sysUserService.queryAll();
	   }
		
	   System.out.println(">>>userList:" + JSONObject.toJSONString(userList));
    }
	
	@Test
	public void queryPassword() {
		SysUserDTO user = sysUserService.queryByUserName("admin");
		String pwd = StringUtils.substring(user.getPassword(),64);
		System.out.println(">>>pwd:" + pwd);
	}
	
	@Test
	public void getUserInfo() {
		SysUserDTO user = sysUserService.queryByUserName("admin");
		user.setPassword("");
		System.out.println(">>>pwd:" + JSON.toJSONString(user));
	}
	
	@Test
	public void getRoleInfo() {
		SysRoleDTO role = sysRoleService.queryRoleById("5b556bf4ce62332790a16ce1");
		System.out.println(">>>role:" + JSON.toJSONString(role));
	}
	
	@Test
	public void getRole() {
		String role = "{\'roleName\': \'aa\', remark: \'aa\', \'menuIdList\': [1, 6, 7, 8, 9, 10, 11, 12, 13, 14]}";
		SysRoleDTO roleDTO =JSON.parseObject(role, SysRoleDTO.class);
		System.out.println(">>>roleDTO:" + JSON.toJSONString(roleDTO));
		List<Integer> menuIdList = roleDTO.getMenuIdList();
		List<SysMenuDTO> menuList = new ArrayList<>();
		for(Integer menuId : menuIdList) {
			SysMenuDTO menu = sysMenuService.queryMenuByMenuId(menuId);
			menuList.add(menu);
		}
		roleDTO.setMenuList(menuList);
		sysRoleService.insert(roleDTO);
	}
	
	@Test
	public void getMenuListNotButton() {
		Query query = Query.query(Criteria.where("type").nin(2));
		List<SysMenuDTO> menuList = sysMenuService.queryAll(query, SysMenuDTO.class);
		System.out.println(">>>menuList:" + JSON.toJSONString(menuList));
	}
	
	@Test
	public void getMenuIdMax() {
		List<SysMenuDTO> list = sysMenuService.queryAll();
		List<Integer> menuList = new ArrayList<>();
		for(SysMenuDTO menu : list) {
			menuList.add(menu.getMenuId());
		}
		int i = Collections.max(menuList);
		System.out.println(">>>i:" + i);
	}
	
	@Test
	public void getNu() {
		int totalCount = 28;
		int pageSize = 10;
		
		int page = (int) Math.ceil((double) totalCount / pageSize);
		System.out.println(">>>page:" + page);
	}
	
	@Test 
	public void getMenuList() {
		List<SysMenuDTO> list = sysMenuService.queryAll();
		System.out.println(">>>list:" + JSON.toJSONString(list));
	}
	
	@Test
	public void saveMenu() {
		SysMenuDTO menu = new SysMenuDTO();
		menu.setIcon("");
		menu.setMenuId(22);
		menu.setName("删除");
		menu.setOrderNum(0);
		menu.setParentId(3);
		menu.setParentName("角色管理");
		menu.setPerms("sys:role:delete");
		menu.setType(2);
		menu.setUrl("");
		sysMenuService.insert(menu);
	}
}
