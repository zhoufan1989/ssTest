package com.sys.service;

import java.util.List;

import com.base.service.BaseService;
import com.sys.dto.SysUserDTO;

public interface SysUserService extends BaseService<SysUserDTO, String>{
	
	/**
	 * 查询用户，逻辑：
	 * 1、根据用户名查询用户
	 * 2、根据用户ID，查询用户角色
	 */
	public SysUserDTO queryById(String id);
	
	/**
	 * 根据账户查询用户信息
	 * @param account
	 * @return
	 */
	public List<String> queryAllPerms(String userID);
	
	/**
	 * 添加用户信息
	 * @param sysUserDTO
	 * @return
	 */
	public boolean insert(SysUserDTO sysUserDTO);
	
	/**
	 * 编辑用户信息
	 * @param sysUserDTO
	 * @return
	 */
	public boolean updater(SysUserDTO sysUserDTO);
	
	/**
	 * 根据用户账号名查询出账户信息
	 * @param UserName
	 * @return
	 */
	public SysUserDTO queryByUserName(String UserName);
	
	
	/**
	 * 修改密码
	 * @param userId 用户ID
	 * @param password 原密码
	 * @param newPassword 新密码
	 */
	public boolean updatePassword(int userId, String password, String newPassword);
	
	/**
	 * 
	 */
	public int deleteBatchById(Integer[] usetIds);
	
	/**
	 * 获取当前系统用户
	 */
	public SysUserDTO getCurrentSysUserDTO();
	
}
