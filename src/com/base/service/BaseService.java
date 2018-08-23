package com.base.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	/**
	 * 采用mongoTemplate进行条件分页，默认按_id降序排序
	 * @param query 查询条件
	 * @param pageable 分页
	 * @param clazz 类型
	 * @return
	 */
	public Page<T> queryAllBy(Query query, Pageable pageable, Class<T> clazz);
	
	
	
}
