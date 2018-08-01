package com.base.mapper;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseMapper<T, ID extends Serializable> extends  MongoRepository<T, ID> {
	
	public void deleteById(ID id);
	
	public List<T> findByIdIn(List<ID> list);
	
	public Long deleteByIdIn(List<ID> list);
	
}
