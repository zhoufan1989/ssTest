package com.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.util.PageUtils;




public abstract class BaseController {
	
	
	public Map<Object, Object> error() {
		Map<Object, Object> error =  new HashMap<Object, Object>();
		error.put(500, "未知异常，请联系管理员");
		return error;
	}

	public  Map<Object, Object> error(String msg) {
		Map<Object, Object> error =  new HashMap<Object, Object>();
		error.put("code", 500);
		error.put("msg", msg);
		return error;
	}

	public Map<Object, Object> error(int code, String msg) {
		Map<Object, Object> error =  new HashMap<Object, Object>();
		error.put("code", code);
		error.put("msg", msg);
		return error;
	}

	public Map<Object, Object> putData(String msg) {
		Map<Object, Object> map =  new HashMap<Object, Object>();
		map.put("msg", msg);
		return map;
	}

	public  Map<Object, Object> putData(Map<Object, Object> map) {
		Map<Object, Object> mapData =  new HashMap<Object, Object>();
		mapData.putAll(map);
		return mapData;
	}

	public Map<Object, Object> putData() {
		Map<Object, Object> mapData = new HashMap<Object, Object>();
		mapData.put("code", 0);
		return mapData;
	}

	public Map<Object, Object> putData(String key, Object value) {
		Map<Object, Object> mapData =  new HashMap<Object, Object>();
		mapData.put(key, value);
		mapData.put("code", 0);
		return mapData;
	}
	
	/**
	 * 封装分页返回值
	 * 
	 * @param pager 分页对象
	 * @param pageList 数据列表
	 * @return
	 */
	public <T> Map<String, Object> putPageData(PageUtils pager, Page<T> pageList) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("isSuccess", Boolean.TRUE);
		resultMap.put("nowPage", pager.getCurrPage());	//当前页数
		resultMap.put("pageSize", pager.getPageSize()); //每页多少条
		
		List<T> list = pageList.getContent();
		resultMap.put("totalPage", pageList.getTotalPages());     //总页数
		resultMap.put("totalCount", pageList.getTotalElements()); //总记录数
		resultMap.put("startRecord", pageList.getNumberOfElements()); //启动记录
		
		//列表展示数据
		resultMap.put("list", list);
		return resultMap;
	}
}
