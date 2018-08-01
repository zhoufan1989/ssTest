package com.sys.mapper.user;


import com.base.mapper.BaseMapper;
import com.sys.dto.SysUserDTO;

public interface SysUserMapper extends BaseMapper<SysUserDTO, String>{
	
	public SysUserDTO findById(String id);
	
	public SysUserDTO findByUserName(String userName);
}
