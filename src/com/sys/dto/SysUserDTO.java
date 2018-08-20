package com.sys.dto;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.base.dto.BaseDTO;

/**
 * 系统用户
 * @author zhoufan
 *
 */
@Document(collection="tb_sys_user")
public class SysUserDTO extends BaseDTO{
	
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 用户真实姓名
	 */
	private String name;
	
	/**
	 * 用户账号名
	 */
	private String userName;
	
	/**
	 * 用户密码
	 */
	private String password;
	
	/**
	 * 加密盐
	 */
	private String credentialsSalt;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 状态<1.正常,'1';2.禁用,'0'>
	 */
	private int status;
	
	/**
	 * 角色ID列表
	 */
	@DBRef
	private List<SysRoleDTO> roleIdList;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getCredentialsSalt() {
		return credentialsSalt;
	}

	public void setCredentialsSalt(String credentialsSalt) {
		this.credentialsSalt = credentialsSalt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<SysRoleDTO> getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(List<SysRoleDTO> roleIdList) {
		this.roleIdList = roleIdList;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
