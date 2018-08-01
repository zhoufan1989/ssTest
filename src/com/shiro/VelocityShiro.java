package com.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class VelocityShiro {
	
	/**
	 * 是否拥有该权限
	 * @param permission 权限标识
	 * @return <1.true:是；2.false:否>
	 */
	public boolean hasPermission(String permission) {
		Subject subject = SecurityUtils.getSubject();
		return subject != null && subject.isPermitted(permission);
	}
	
}
