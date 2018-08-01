package com.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类
 * @author zhoufan
 *
 */
public class ShiroUtils {
	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}
	
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}
	
	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}
	
	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}
	
	public static boolean isLogin() {
		return SecurityUtils.getSubject().getPrincipal() != null;
	}
	
	public static void logout() {
		SecurityUtils.getSubject().logout();
	}
	
	public static String getKaptcha(String key) {
		String kaptcha = getSessionAttribute(key).toString();
		getSession().removeAttribute(key);
		return kaptcha;
	} 
}
