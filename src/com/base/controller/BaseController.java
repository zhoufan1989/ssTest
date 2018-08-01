package com.base.controller;

import java.util.HashMap;
import java.util.Map;


public class BaseController {
	
	
	
	public static Map<Object, Object> error() {
		Map<Object, Object> error =  new HashMap<Object, Object>();
		error.put(500, "未知异常，请联系管理员");
		return error;
	}

	public static Map<Object, Object> error(String msg) {
		Map<Object, Object> error =  new HashMap<Object, Object>();
		error.put("code", 500);
		error.put("msg", msg);
		return error;
	}

	public static Map<Object, Object> error(int code, String msg) {
		Map<Object, Object> error =  new HashMap<Object, Object>();
		error.put("code", code);
		error.put("msg", msg);
		return error;
	}

	public static Map<Object, Object> putData(String msg) {
		Map<Object, Object> map =  new HashMap<Object, Object>();
		map.put("msg", msg);
		return map;
	}

	public static Map<Object, Object> putData(Map<Object, Object> map) {
		Map<Object, Object> mapData =  new HashMap<Object, Object>();
		mapData.putAll(map);
		return mapData;
	}

	public static Map<Object, Object> putData() {
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
	
}
