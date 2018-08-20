package com.base.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.query.Query;


public interface BaseService<T, ID extends Serializable> {
	
	/**
	 * 获取所有数据
	 * @return
	 */
	public List<T> queryAll();
	
	/**
	 * 按条件获取所有数据
	 * @param query
	 * @return
	 */
	public List<T> queryAll(Query query, Class<T> clazz);
	
	public int update(T t);
	
	
	
}
