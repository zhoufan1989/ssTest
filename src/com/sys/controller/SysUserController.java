package com.sys.controller;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
		String userName = (String) SecurityUtils.getSubject().getPrincipal();
		SysUserDTO user = sysUserService.queryByUserName(userName);
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
		boolean userIs = sysUserService.updatePassword(user.getUserId(), password, newPassword);
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
	
	//用户添加和更新
	
	//用户删除
}
