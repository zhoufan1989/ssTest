package com.sys.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



/**
 * 代码生成器
 * @author Zhoufan
 * @date 2018年07月19日 下午9:12:58
 */
@Controller
@RequestMapping("/sys/generator")
public class SysGeneratorController {
//	@Autowired
//	private SysGeneratorService sysGeneratorService;
//
//	/**
//	 * 列表
//	 */
//	@ResponseBody
//	@RequestMapping("/list")
//	@RequiresPermissions("sys:generator:list")
//	public R list(String tableName, Integer page, Integer limit) {
//		Map<String, Object> map = new HashMap<>();
//		map.put("tableName", tableName);
//		map.put("offset", (page - 1) * limit);
//		map.put("limit", limit);
//
//		// 查询列表数据
//		List<Map<String, Object>> list = sysGeneratorService.queryList(map);
//		int total = sysGeneratorService.queryTotal(map);
//
//		PageUtils pageUtil = new PageUtils(list, total, limit, page);
//
//		return R.ok().put("page", pageUtil);
//	}
//
//	/**
//	 * 生成代码
//	 */
//	@RequestMapping("/code")
//	@RequiresPermissions("sys:generator:code")
//	public void code(String tables, HttpServletResponse response) throws IOException {
//		String[] tableNames = new String[] {};
//		tableNames = JSON.parseArray(tables).toArray(tableNames);
//
//		byte[] data = sysGeneratorService.generatorCode(tableNames);
//
//		response.reset();
//		response.setHeader("Content-Disposition", "attachment; filename=\"framework.zip\"");
//		response.addHeader("Content-Length", "" + data.length);
//		response.setContentType("application/octet-stream; charset=UTF-8");
//
//		IOUtils.write(data, response.getOutputStream());
//	}
}
