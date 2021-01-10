package com.db.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.db.common.vo.JsonResult;
import com.db.common.vo.PageObject;
import com.db.sys.entity.SysLog;
import com.db.sys.service.SysLogService;

@RequestMapping("/log/")
@Controller
public class SysLogController {
	
	@Autowired
	private SysLogService sysLogService;
	
	@RequestMapping("doLogListUI")
	public String doLogListUI() {
		return "sys/log_list";
	}
	
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String username,Integer pageCurrent) {
		PageObject<SysLog> pageObject = sysLogService.findPageObjects(username, pageCurrent);
		return new JsonResult(pageObject);
	}
	
	@RequestMapping("doDeleteObjects")
	@ResponseBody
	public JsonResult doDeleteObjects(@RequestParam("idArray")Integer... ids) {
		try {
			sysLogService.deleteObjects(ids);
			return new JsonResult("删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new JsonResult("删除失败!");
		}
	}
	
}
