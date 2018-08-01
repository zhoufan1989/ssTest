package com.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.base.service.impl.BaseServiceImpl;
import com.sys.dto.SysUserDTO;
import com.sys.mapper.user.SysUserMapper;
import com.sys.service.SysMenuService;
import com.sys.service.SysRoleService;
import com.sys.service.SysUserService;

@Service("sysUserService")
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDTO, String> implements SysUserService{
	
	@Autowired
	private SysUserMapper sysUserMapper;
	

	
	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private SysMenuService sysMenuService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(sysUserMapper);
	}

	@Override
	public SysUserDTO queryById(String id) {
		SysUserDTO sysUserDTO = sysUserMapper.findById(id);
		return sysUserDTO;
	}
	
	

	@Override
	public List<String> queryAllPerms(String userID) {
//		List<SysUserDTO> list = sysUserMapper.findByAccount(account);
		List<String> permList = new ArrayList<String>();
		Query surQuery = Query.query(Criteria.where("userId").is(userID));
//		List<SysUserRoleDTO> surList = sysUserRoleService.queryAll(surQuery, SysUserRoleDTO.class);
//		for(SysUserRoleDTO sur : surList) {
//			Query srmQuery = Query.query(Criteria.where("roleId").is(sur.getRoleId()));
//			List<SysRoleMenuDTO> srmList = sysRoleMenuService.queryAll(srmQuery, SysRoleMenuDTO.class);
//			for(SysRoleMenuDTO srm : srmList) {
//				Query menuQuery = Query.query(Criteria.where("menuId").is(srm.getMenuId()));
//				List<SysMenuDTO> menuList = sysMenuService.queryAll(menuQuery, SysMenuDTO.class);
//				for(SysMenuDTO menu : menuList) {
//					permList.add(menu.getId());
//				}
//			}
//			
//		}
		return permList;
	}

	@Override
	public SysUserDTO queryByUserName(String userName) {
		SysUserDTO user = sysUserMapper.findByUserName(userName);
		return user;
	}

	@Override
	public boolean insert(SysUserDTO sysUserDTO) {
		sysUserDTO.setCreateTime(new Date());
		sysUserMapper.save(sysUserDTO);
		return true;
	}

	@Override
	public boolean updatePassword(int userId, String password, String newPassword) {
		Query query = Query.query(Criteria.where("password").is(password).and("userId").is(userId));
		SysUserDTO user = mongoTemplate.findOne(query, SysUserDTO.class);
		user.setPassword(newPassword);
		return true;
	}

}
