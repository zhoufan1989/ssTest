package com.sys.service;

import com.base.service.BaseService;
import com.sys.dto.FileDataDTO;

public interface FileDataService extends BaseService<FileDataDTO, String>{
	
	/**
	 * 添加文件数据源
	 * @param file
	 * @return
	 */
	public boolean insert(FileDataDTO file);
	
	/**
	 * 批量删除文件数据源
	 * @param ids
	 * @return
	 */
	public int deleteBatch(String[] ids);
	
	/**
	 * 查询数据详情
	 * @param id
	 * @return
	 */
	public FileDataDTO queryFileDataById(String id);
}
