package com.sys.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.service.impl.BaseServiceImpl;
import com.sys.dto.FileDataDTO;
import com.sys.mapper.file.FileDataMapper;
import com.sys.service.FileDataService;

@Service("fileDataService")
public class FileDataServiceImpl extends BaseServiceImpl<FileDataDTO, String> implements FileDataService{
	
	@Autowired
	private FileDataMapper fileDataMapper;
	
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(fileDataMapper);
	}
	
	@Override
	public boolean insert(FileDataDTO file) {
		file.setAddTime(new Date());
		fileDataMapper.save(file);
		return true;
	}

	@Override
	public int deleteBatch(String[] ids) {
		int count = 0;
		for(String id : ids) {
			FileDataDTO file = fileDataMapper.findFileDataById(id);
			fileDataMapper.delete(file);
			count++;
		}
		return count;
	}

	@Override
	public FileDataDTO queryFileDataById(String id) {
		FileDataDTO fileData = fileDataMapper.findFileDataById(id);
		return fileData;
	}
	
}
