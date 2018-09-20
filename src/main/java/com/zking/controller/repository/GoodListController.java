package com.zking.controller.repository;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.zking.controller.base.BasePageController;
import com.zking.pojo.GoodList;
import com.zking.pojo.Shop;
import com.zking.service.BaseService;
import com.zking.util.PageData;
import org.activiti.engine.IdentityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * ProducerController
 *
 * @anthor zhanghy9
 * @date 2018-05-02 8:28
 */
@Controller
@RequestMapping("/goodlist")
public class GoodListController extends BasePageController{

    @Resource
    BaseService<GoodList> goodList;
    @Resource
    IdentityService identityService;
    /**
     * 页面跳转
     *
     * @param model
     * @return
     */
    @Override
    @RequestMapping("/page")
    public String getPage(Model model) {
        return "repository/goodList";
    }

    /**
     * 搜索返回列表
     *
     * @param page
     * @param rows
     * @param selectType
     * @param selectValue
     * @param model
     * @return
     */
    @Override
    @RequestMapping(value="/info/{page}/{rows}/{selectType}/{selectValue}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object listBySelect(@PathVariable int page,
                               @PathVariable int rows,
                               @PathVariable String selectType,
                               @PathVariable String selectValue, Model model) {
        int start = (page-1)*rows;
        PageData sendData = new PageData();
        sendData.put("start",start);
        sendData.put("rows",rows);
        sendData.put("selectType",selectType);
        sendData.put("selectValue","%"+selectValue+"%");
        PageData resultData = goodList.getPartPageList(sendData);
        List<Shop> lists = (List<Shop>)resultData.get("lists");
        return new Gson().toJson(lists);
    }

    /**
     * 初始化列表
     *
     * @param page
     * @param rows
     * @param model
     * @return
     */
    @Override
    @RequestMapping(value="/info/{page}/{rows}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object list(@PathVariable int page,
                       @PathVariable int rows, Model model) {
        int start = (page-1)*rows;
        PageData sendData = new PageData();
        sendData.put("start",start);
        sendData.put("rows",rows);
        PageData resultData = goodList.getAllPageList(sendData);
        List<Shop> lists = (List<Shop>)resultData.get("lists");
        logger.info("lists="+lists);
        return new Gson().toJson(lists);
    }

    @RequestMapping(value="/info" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object producerList(Model model){
        PageData sendData = new PageData();
        sendData.put("start",null);
        sendData.put("rows",null);
        PageData resultData = goodList.getAllList(sendData);
        List<Shop> lists = (List<Shop>)resultData.get("lists");
        logger.info("lists="+lists);
        return new Gson().toJson(lists);
    }

    /**
     * 搜索返回分页数据
     *
     * @param rows
     * @param selectType
     * @param selectValue
     * @param model
     * @return
     */
    @Override
    @RequestMapping(value="/page/{rows}/{selectType}/{selectValue}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object pageBySelect(@PathVariable int rows,
                               @PathVariable String selectType,
                               @PathVariable String selectValue, Model model) {
        PageData sendData = new PageData();
        sendData.put("selectType",selectType);
        sendData.put("selectValue","%"+selectValue+"%");
        int sum = goodList.listSum(sendData);
        JsonObject result = new JsonObject();
        result.addProperty("listSum",sum);
        return new Gson().toJson(result);
    }

    /**
     * 初始化分页数据
     *
     * @param rows
     * @param model
     * @return
     */
    @Override
    @RequestMapping(value="/page/{rows}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object page(@PathVariable int rows, Model model) {
        PageData sendData = new PageData();
        sendData.put("selectType",null);
        sendData.put("selectValue",null);
        int sum = goodList.listSum(sendData);
        JsonObject result = new JsonObject();
        result.addProperty("listSum",sum);
        return new Gson().toJson(result);
    }

    /**
     * 新增记录
     *
     * @param Object
     * @param model
     * @return
     */
    @Override
    @RequestMapping(value="/add/{Object}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object add(@PathVariable String Object, Model model) {
        GoodList item = new Gson().fromJson(Object,GoodList.class);
        int sum = 0;
        if(goodList.add(item)){
            sum+=1;
        }
        JsonObject result = new JsonObject();
        result.addProperty("addSum",sum);
        return new Gson().toJson(result);
    }

    /**
     * 更新记录
     *
     * @param Object
     * @param model
     * @return
     */
    @Override
    @RequestMapping(value="/edit/{Object}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object edit(@PathVariable String Object, Model model) {
        GoodList item = new Gson().fromJson(Object,GoodList.class);
        int sum = 0;
        if(goodList.update(item)){
            sum+=1;
        }
        JsonObject result = new JsonObject();
        result.addProperty("changeSum",sum);
        return new Gson().toJson(result);
    }

    /**
     * 删除记录
     *
     * @param Object
     * @param model
     * @return
     */
    @Override
    @RequestMapping(value="/del/{Object}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object del(@PathVariable String Object, Model model) {
        JsonObject result = new JsonObject();
        List<GoodList> pids = new Gson().fromJson(Object,new TypeToken<List<GoodList>>(){}.getType());
        int sum = 0;
        for (GoodList productObject : pids){
            if(goodList.del(productObject)){
                sum+=1;
            }

        }
        result.addProperty("delSum",sum);
        return new Gson().toJson(result);
    }
}
