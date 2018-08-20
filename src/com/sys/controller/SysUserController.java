package com.sys.controller;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.base.controller.BaseController;

import com.sys.dto.SysUserDTO;
import com.sys.service.SysUserService;
import com.util.PageUtils;
import com.util.ShiroUtils;

/**
 * 系统用户
 * @author Zhoufan
 *
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController{
	
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public Object info() {
		SysUserDTO user = sysUserService.getCurrentSysUserDTO();
		return putData("user",user);
	}
	
	/**
	 * 修改用户密码
	 * @param password
	 * @param newPassword
	 * @return
	 */
	@RequestMapping("/password")
	public Object password(String password, String newPassword) {
		if (StringUtils.isBlank(newPassword)) {
			return error("新密码不为能空");
		}
		
		// sha256加密
		password = new Sha256Hash(password).toHex();
		// sha256加密
		newPassword = new Sha256Hash(newPassword).toHex();
		// 更新密码
		SysUserDTO user = (SysUserDTO) SecurityUtils.getSubject().getPrincipal();
		boolean userIs = sysUserService.updatePassword(user.getUserName(), password, newPassword);
		if (userIs == false) {
			return error("原密码不正确");
		}
		// 退出
		ShiroUtils.logout();
		return putData();
	}
	
	//用户列表
	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	public Object getUserList(int page, int limit,String userName) {
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
		int total = userList.size();
		PageUtils pageResponse = new PageUtils(userList, total, limit, page);
		return putData("page", pageResponse);
	}
	
	//用户添加
	@RequestMapping("/save")
	@RequiresPermissions("sys:user:save")
	public Object save(@RequestBody SysUserDTO user) {
		if(StringUtils.isBlank(user.getUserName())) {
			return error("用户名不能为空");
		}
		if(StringUtils.isBlank(user.getPassword())) {
			return error("密码不能为空");
		}
		sysUserService.insert(user);					
		
		return putData();
	}
	
	/**
	 * 用户信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:user:info")
	public Object info(@PathVariable("id") String id) {
		SysUserDTO user = sysUserService.queryById(id);
		return putData("user", user);
	}
	
	//用户编辑
	@RequestMapping("/update")
	@RequiresPermissions("sys:user:update")
	public Object update(@RequestBody SysUserDTO user) {
		if(StringUtils.isBlank(user.getUserName())) {
			return error("用户名不能为空");
		}
		if(StringUtils.isBlank(user.getPassword())) {
			return error("密码不能为空");
		}
		sysUserService.update(user);					//更新
		return putData();
	}
	
	//用户删除
	@RequestMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public Object delete(@RequestBody String[] ids) {
		SysUserDTO user = sysUserService.getCurrentSysUserDTO();
		if(ArrayUtils.contains(ids, 1)) {
			return error("系统管理员不能删除");
		}
		if(ArrayUtils.contains(ids, user.getId())) {
			return error("当前用户不能删除");
		}
		sysUserService.deleteBatchById(ids);
		return putData();
	}
	
}
