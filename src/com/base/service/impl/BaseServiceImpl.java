package com.base.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.base.mapper.BaseMapper;
import com.base.service.BaseService;

public class BaseServiceImpl<T,ID extends Serializable> implements BaseService<T, ID> {
	
	private BaseMapper<T, ID> baseMapper;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public void setBaseMapper(BaseMapper<T, ID> baseMapper) {
		this.baseMapper = baseMapper;
	}
	
	@Override
	public int update(T t) {
		baseMapper.save(t);
		return 1;
	}

	public Page<T> queryAllBy(Query query, Pageable pageable, Class<T> clazz) {
		long count = mongoTemplate.count(query, clazz);
		Sort sort = pageable.getSort();
		if(sort == null) sort = new Sort(Direction.DESC, "_id");		
		
		//总条比起始条数要大，否则起始条数从0开始
		int skip = pageable.getPageNumber() * pageable.getPageSize();
		if(count <= (pageable.getPageNumber() * pageable.getPageSize())) {
			pageable = new PageRequest(0, pageable.getPageSize(), sort);
			skip = 0;
		}
		
		query.with(sort).skip(skip).limit(pageable.getPageSize());
		List<T> list = mongoTemplate.find(query, clazz);
		return new PageImpl<T>(list, pageable, count);
	}

	public List<T> queryByIdIn(List<ID> list) {
		return baseMapper.findByIdIn(list);
	}

	public Long deleteByIdIn(List<ID> list, Class<T> clazz) {
		Long count = 0L;
		if(CollectionUtils.isEmpty(list)) return count;

		Query query = new Query();
		query.addCriteria(Criteria.where("_id").in(list));
		List<T> removelist = mongoTemplate.findAllAndRemove(query, clazz);
		return removelist == null ? 0L : removelist.size();
	}
	
	public void save(T t) {
		baseMapper.save(t);
	}

	@CacheEvict(value="mongodbCache", allEntries=true) //清除掉全部缓存
	public void delete(T t) {
		baseMapper.delete(t);
	}

	@CacheEvict(value="mongodbCache", allEntries=true) //清除掉全部缓存
	public void deleteById(ID id) {
		baseMapper.deleteById(id);
	}

	public T queryById(ID id) {
		return baseMapper.findOne(id);
	}

	public List<T> queryAll() {
		return transformToList(baseMapper.findAll());
	}

	public List<T> queryAll(Query query, Class<T> clazz) {
		return mongoTemplate.find(query, clazz);
	}
	
	public List<T> transformToList(Iterable<T> iterable) {
		List<T> list = new ArrayList<T>();
		if(iterable == null) return list;
		for(T t : iterable) {
			list.add(t);
		}
		return list;
	}

	public void index(String collectionName, String indexKey) {
		mongoTemplate.indexOps(collectionName).ensureIndex(new Index(indexKey, Direction.ASC));
	}
	
	public boolean collectionExits(String tableName) {
		return mongoTemplate.collectionExists(tableName);
	}
	
	public void createCollection(String tableName) {
		mongoTemplate.createCollection(tableName);
	}

	public long count() {
		return baseMapper.count();
	}
	

	
}
