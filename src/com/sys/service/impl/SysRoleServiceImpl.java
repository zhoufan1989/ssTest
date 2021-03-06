package com.sys.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.service.impl.BaseServiceImpl;
import com.sys.dto.SysRoleDTO;
import com.sys.mapper.rolemenu.SysRoleMapper;
import com.sys.service.SysRoleService;

@Service("sysRoleService")
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleDTO, String> implements SysRoleService{
	
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(sysRoleMapper);
	}

	@Override
	public boolean insert(SysRoleDTO sysRoleDTO) {
		
		SysRoleDTO role = sysRoleDTO;
		role.setAddDate(new Date());
		sysRoleMapper.save(role);
		return true;
	}

	@Override
	public SysRoleDTO queryRoleByRoleId(int roleId) {
		SysRoleDTO role = sysRoleMapper.findRoleByRoleId(roleId);
		return role;
	}

	@Override
	public SysRoleDTO queryRoleById(String id) {
		SysRoleDTO role = sysRoleMapper.findRoleById(id);
		return role;
	}

	@Override
	public int deleteBatch(String[] ids) {
		int count = 0;
		for(String id : ids) {
			SysRoleDTO role = sysRoleMapper.findRoleById(id);
			sysRoleMapper.delete(role);
			count++;
		}
		return count;
	}
	
}
