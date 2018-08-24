package com.sys.dto;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.base.dto.BaseDTO;

/**
 * 菜单管理
 * @author zhoufan
 *
 */
@Document(collection="tb_sys_menu")
public class SysMenuDTO extends BaseDTO{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 菜单ID
	 */
	private int menuId;
	
	/**
	 * 父菜单ID,一级菜单为0
	 */
	private int parentId;
	
	/**
	 * 父菜单名称
	 */
	private String parentName;
	
	/**
	 * 菜单名称
	 */
	private String name;
	
	/**
	 * 菜单URL
	 */
	private String url;
	
	/**
	 * 授权(多个用逗号分隔，如:user:list,user:create)
	 */
	private String perms;
	
	/**
	 * 类型:<1).目录,状态值为:0; 2).菜单,状态值为:1; 3).按钮,状态值为:2>
	 */
	private int type;
	
	/**
	 * 菜单图标
	 */
	private String icon;
	
	/**
	 * 排序
	 */
	private int orderNum;
	
	/**
	 * ztree属性
	 */
	private Boolean open;
	
	/**
	 * 列表
	 */
	private List<?> list;
	
	private Date addDate;
	
	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	
}
