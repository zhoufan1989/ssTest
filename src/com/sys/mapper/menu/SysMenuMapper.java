package com.sys.mapper.menu;

import java.util.List;

import com.base.mapper.BaseMapper;
import com.sys.dto.SysMenuDTO;

public interface SysMenuMapper extends BaseMapper<SysMenuDTO, String>{
	
	public SysMenuDTO findMenuByMenuId(int menuId);
	
	public List<SysMenuDTO> findMenuByParentId(int parentId);
}
