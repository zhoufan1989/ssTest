package com.shiro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.sys.dto.SysMenuDTO;
import com.sys.dto.SysRoleDTO;
import com.sys.dto.SysUserDTO;
import com.sys.service.SysMenuService;
import com.sys.service.SysUserService;

public class UserRealm extends AuthorizingRealm{
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private SysMenuService sysMenuService;
	
	/**
	 * 授权(验证权限时调用)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		/**
		 * principal代表为:
		 * 1.可以是uuid 
         * 2.数据库中的主键 
         * 3.LDAP UUID或静态DN 
         * 4.在所有用户帐户中唯一的字符串用户名
		 */
		String userName = (String) principals.getPrimaryPrincipal();
		SysUserDTO user = sysUserService.queryByUserName(userName);
		int userId = user.getUserId();
		
		List<String> permsList = null;
		
		//系统管理员,拥有最高权限
		if(userId == 1) {
			List<SysMenuDTO> menuList = sysMenuService.queryAll();
			permsList = new ArrayList<>(menuList.size());
			for(SysMenuDTO menu : menuList) {
				permsList.add(menu.getPerms());
			}
		} else {
			//获取用户对应角色的目录资源
			permsList = new ArrayList<>();
			List<SysRoleDTO> roleList = user.getRoleIdList();
			for(SysRoleDTO role : roleList) {
				List<SysMenuDTO> menuList = role.getMenuList();
				for(SysMenuDTO menu : menuList) {
					permsList.add(menu.getPerms());
				}
			}
		}
		//用户权限列表
		Set<String> permsSet = new HashSet<String>();
		for(String perms : permsList) {
			if(StringUtils.isBlank(perms)) {
				continue;
			}
			permsSet.addAll(Arrays.asList(perms.trim().split(",")));
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permsSet);
		return info;
	}
	
	/**
	 * 认证(登陆时调用)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userName = (String) token.getPrincipal();
		String password = new String((char[]) token.getCredentials());
		
		//查询用户信息
		SysUserDTO user = sysUserService.queryByUserName(userName);
		
		//账号不存在
		if(user == null) {
			throw new UnknownAccountException("账户不存在");
		}
			
		//密码错误
		if(!password.equals(user.getPassword())) {
			throw new IncorrectCredentialsException("密码不正确");
		}
			
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userName, password,getName());
		
		return info;
	}

}
