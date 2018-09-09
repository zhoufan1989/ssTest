package com.sys.mapper.file;

import com.base.mapper.BaseMapper;
import com.sys.dto.FileDataDTO;

public interface FileDataMapper extends BaseMapper<FileDataDTO, String>{
	
	public FileDataDTO findFileDataById(String id);
	
	
}
