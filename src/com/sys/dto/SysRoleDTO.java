package com.sys.dto;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.base.dto.BaseDTO;

@Document(collection="tb_sys_role")
public class SysRoleDTO extends BaseDTO{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 角色ID
	 */
	private int roleId;
	
	/**
	 * 角色名称
	 */
	private String roleName;
	
	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 资源菜单列表
	 */
	@DBRef
	private List<SysMenuDTO> menuList;
	
	/**
	 * 选取的资源menuId列表
	 */
	private List<Integer> menuIdList;
	
	private Date addDate;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<SysMenuDTO> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<SysMenuDTO> menuList) {
		this.menuList = menuList;
	}
	
	public List<Integer> getMenuIdList() {
		return menuIdList;
	}

	public void setMenuIdList(List<Integer> menuIdList) {
		this.menuIdList = menuIdList;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	
}
