package com.base.dto;

import java.io.Serializable;

/**
 * 统一定义id的BaseDTO基类.
 * 基类统一定义id的属性名称、数据类型.
 * 子类可重载getId()函数.
 * @author Zhoufan
 *
 */

import org.springframework.data.annotation.Id;

/**
 * 统一定义id的BaseDTO基类.
 * 基类统一定义id的属性名称、数据类型.
 * 子类可重载getId()函数.
 * @author Zhoufan
 *
 */
public abstract class BaseDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	protected String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
