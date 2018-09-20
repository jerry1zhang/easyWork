package com.zking.controller;

import com.google.gson.Gson;
import com.zking.biz.menu.MenuBiz;
import com.zking.biz.task.TaskBiz;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zking.controller.base.BaseController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("menu")
public class PageController extends BaseController{

	@Resource
	MenuBiz menuBiz;

	@RequestMapping(value="/init" ,method= RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public Object init(){
//		Map<String,Object> INFO = menuBiz.initMenu(0);
		return new Gson().toJson("").toString();
	}


}
