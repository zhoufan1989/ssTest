package com.sys.mapper.rolemenu;

import com.base.mapper.BaseMapper;
import com.sys.dto.SysRoleDTO;

public interface SysRoleMapper extends BaseMapper<SysRoleDTO, String>{
  
	public SysRoleDTO findRoleByRoleId(int roleId);
}
