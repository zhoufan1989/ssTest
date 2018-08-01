package com.sys.service;

import com.base.service.BaseService;
import com.sys.dto.SysRoleDTO;

public interface SysRoleService extends BaseService<SysRoleDTO, String>{

	/**
	 * 添加角色信息
	 */
	public boolean insert(SysRoleDTO sysRoleDTO);
	
	/**
	 * 根据角色Id查询角色详情
	 */
	public SysRoleDTO queryRoleByRoleId(int roleId);
}
