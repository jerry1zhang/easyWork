package com.zking.controller.info;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.zking.controller.base.BasePageController;
import com.zking.pojo.ListStatic;
import com.zking.service.BaseService;
import com.zking.util.UUIDUtil;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.GroupEntityImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;

/**
 * GroupController
 *
 * @anthor zhanghy9
 * @date 2018-05-03 23:29
 */
@Controller
@RequestMapping("/group")
public class GroupController extends BasePageController{
    @Resource
    IdentityService identityService;
    @Resource
    BaseService<ListStatic> list;

    /**
     * 页面跳转
     *
     * @param model
     * @return
     */
    @Override
    @RequestMapping("/page")
    public String getPage(Model model) {
        return "info/group";
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
        GroupQuery query = identityService.createGroupQuery();
        Method method;
        Object object;
        try {
            method = query.getClass().getDeclaredMethod(selectType,String.class);
            object = method.invoke(query,"%"+selectValue+"%");
        }catch (Exception e){
            e.printStackTrace();
            return "admin-error";
        }
        query = (GroupQuery)object;
        List<Group> groups = query.listPage(start,rows);
        return new Gson().toJson(groups);
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
        GroupQuery query = identityService.createGroupQuery();
        List<Group> groups = query.listPage(start,rows);
        return new Gson().toJson(groups);
    }

    @RequestMapping(value="/info" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object allList(Model model) {
        GroupQuery query = identityService.createGroupQuery();
        List<Group> groups = query.list();
        return new Gson().toJson(groups);
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
        GroupQuery query = identityService.createGroupQuery();
        Method method;
        Object object;
        try {
            method = query.getClass().getDeclaredMethod(selectType,String.class);
            object = method.invoke(query,"%"+selectValue+"%");
        }catch (Exception e){
            e.printStackTrace();
            return "admin-error";
        }
        query = (GroupQuery)object;
        int sum = new Long(query.count()).intValue();
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
        GroupQuery query = identityService.createGroupQuery();
        int sum = new Long(query.count()).intValue();
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
        Group item = new Gson().fromJson(Object,GroupEntityImpl.class);
        item.setId(UUIDUtil.getUUID());
        Group newGroup = identityService.newGroup(item.getId());
        newGroup.setName(item.getName());
        newGroup.setType(item.getType());
        identityService.saveGroup(newGroup);
        JsonObject result = new JsonObject();
        result.addProperty("addSum",1);
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
        com.zking.enetity.Group item = new Gson().fromJson(Object, com.zking.enetity.Group.class);
//        item.setId(UUIDUtil.getUUID());
        identityService.saveGroup(item.getActGroup());
        JsonObject result = new JsonObject();
        result.addProperty("changeSum",1);
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
        List<com.zking.enetity.Group> items = new Gson().fromJson(Object, new TypeToken<List<com.zking.enetity.Group>>(){}.getType());
        int sum = 0;
        for (com.zking.enetity.Group item : items) {
            //先删除用户，用户和用户组之间关系
            List<User> users = identityService.createUserQuery().memberOfGroup(item.getId()).list();
            for (User user : users) {
                identityService.deleteMembership(user.getId(), item.getId());
                identityService.deleteUser(user.getId());
            }
            //删除组
            identityService.deleteGroup(item.getId());
            sum++;
        }
        JsonObject result = new JsonObject();
        result.addProperty("delSum",sum);
        return new Gson().toJson(result);
    }
}
