package com.sys.dto;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.base.dto.BaseDTO;

/**
 * 文件管理数据源
 * @author Zhoufan
 * @Date 2018-09-09 09:30:00
 */
@Document(collection="tb_file_data")
public class FileDataDTO extends BaseDTO{
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private int age;
	
	private Date addTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	
}
