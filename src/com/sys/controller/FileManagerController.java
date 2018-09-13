package com.sys.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.controller.BaseController;
import com.sys.dto.FileDataDTO;
import com.sys.service.FileDataService;
import com.util.ExcelUtil;
import com.util.PageUtils;

/**
 * 文件管理
 * @author zhoufan
 * @Date 2018-09-09 09:30:00
 */
@Controller
@RequestMapping("/file/data")
public class FileManagerController extends BaseController{
	
	@Autowired
	private FileDataService fileDataService;
	
	/**
	 * 数据源添加
	 * @param fileData
	 * @return
	 */
	@RequestMapping("/save")
	@RequiresPermissions("file:data:save")
	@ResponseBody
	public Object save(@RequestBody FileDataDTO fileData) {
		if(StringUtils.isEmpty(fileData.getName())) {
			return error("名称不能为空");
		}
		
		fileDataService.insert(fileData);
		return putData();
		
	}
	
	/**
	 * 删除数据源
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("file:data:delete")
	@ResponseBody
	public Object delete(@RequestBody String[] ids) {
		fileDataService.deleteBatch(ids);
		return putData();
	}
	
	@RequestMapping("/info/{id}")
	@RequiresPermissions("file:data:info")
	@ResponseBody
	public Object info(@PathVariable("id") String id) {
		FileDataDTO fileData = fileDataService.queryFileDataById(id);
		return putData("file", fileData);
	}
	
	/**
	 * 数据源分页列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("file:data:list")
	@ResponseBody
	public Object dataPageList(int page, int limit,String name) {
		PageUtils pageSelect = new PageUtils();
		pageSelect.setCurrPage(page);	//当前页数
		pageSelect.setPageSize(limit);  //每页记录数
		Pageable pageable = new PageRequest(page - 1, limit, Direction.DESC, "_id");
		Query query = new Query();
		if(StringUtils.isNotBlank(name)) {
			Pattern pattern = Pattern.compile(".*?" + name.trim() + ".*?");
			Criteria criteria = new Criteria();
			query.addCriteria(criteria.orOperator(Criteria.where("name").regex(pattern)));
			
		}
		Page<FileDataDTO> pageResult = fileDataService.queryAllBy(query, pageable, FileDataDTO.class);
		return putPageData(pageSelect, pageResult);
	}
	
	@RequestMapping("/fileDataExport")
	@ResponseBody
	public Object fileDataExport(HttpServletResponse response, String name) {
		LinkedHashMap<String, String> fieldMap = new LinkedHashMap<>();
		fieldMap.put("name", "名称");
		fieldMap.put("age", "年龄");
		fieldMap.put("addTime", "添加时间");
		
		try {
			Query query = new Query();
			//名称
			if(StringUtils.isNotBlank(name)) {
				query.addCriteria(Criteria.where("name").is(name));
			}
			query.with(new Sort(Direction.DESC, "addTime"));
			List<FileDataDTO> list = fileDataService.queryAll(query, FileDataDTO.class);
			new ExcelUtil().listToExcel(list, fieldMap, "数据测试统计表","111", response, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
