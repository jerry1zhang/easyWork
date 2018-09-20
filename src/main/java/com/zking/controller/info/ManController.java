package com.zking.controller.info;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.zking.controller.base.BasePageController;
import com.zking.enetity.AddMan;
import com.zking.enetity.UserBody;
import com.zking.util.MD5Util;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.persistence.entity.UserEntityImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * ManController
 *
 * @anthor zhanghy9
 * @date 2018-05-03 23:27
 */
@Controller
@RequestMapping("/man")
public class ManController extends BasePageController{
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
        return "info/man";
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
        UserQuery userQuery = identityService.createUserQuery();
        Method method;
        Object object;
        try {
            method = userQuery.getClass().getMethod(selectType,String.class);
            object = method.invoke(userQuery,"%"+selectValue+"%");
        }catch (Exception e){
            e.printStackTrace();
            return "admin-error";
        }
        userQuery = (UserQuery)object;
        List<User> users = userQuery.listPage(start,rows);
        List<UserBody> userBodies = new ArrayList<>();
        for (User item:users){
            UserBody userBody = new UserBody();
            userBody.setUser(item);
            userBody.setGroup(identityService.createGroupQuery().groupMember(item.getId()).singleResult());
            userBodies.add(userBody);
        }
        return new Gson().toJson(userBodies);
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
        UserQuery userQuery = identityService.createUserQuery();
        List<User> users = userQuery.listPage(start,rows);
        List<UserBody> userBodies = new ArrayList<>();
        for (User item:users){
            UserBody userBody = new UserBody();
            userBody.setUser(item);
            userBody.setGroup(identityService.createGroupQuery().groupMember(item.getId()).singleResult());
            userBodies.add(userBody);
        }
        return new Gson().toJson(userBodies);
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
        UserQuery userQuery = identityService.createUserQuery();
        Method method;
        Object object;
        try {
            method = userQuery.getClass().getMethod(selectType,String.class);
            object = method.invoke(userQuery,"%"+selectValue+"%");
        }catch (Exception e){
            e.printStackTrace();
            return "admin-error";
        }
        userQuery = (UserQuery)object;
        int sum = new Long(userQuery.count()).intValue();
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
        UserQuery userQuery = identityService.createUserQuery();
        int sum = new Long(userQuery.count()).intValue();
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
    @RequestMapping(value="/add/{Object:.+}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object add(@PathVariable String Object, Model model) {
        JsonObject result = new JsonObject();
        result.addProperty("addSum",0);
        AddMan item = new Gson().fromJson(Object, AddMan.class);
        try {
            synchronized (item){
                //创建用户
                User add = identityService.newUser(item.getName());
                add.setPassword(MD5Util.MD5Encode(item.getPassword()));
                add.setFirstName(item.getFirstName());
                add.setLastName(item.getLastName());
                add.setEmail(item.getEmail());
                identityService.saveUser(add);
                //创建用户和组关系
                identityService.createMembership(item.getName(),item.getGroupId());
                result.addProperty("addSum",1);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("用户新增记录失败");
        }
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
    @RequestMapping(value="/edit/{Object:.+}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object edit(@PathVariable String Object, Model model) {
        JsonObject result = new JsonObject();
        result.addProperty("changeSum",0);
        AddMan item = new Gson().fromJson(Object,AddMan.class);
        try {
            synchronized (item){
                //判断是否要重新建立用户与组关系
                if(identityService.createGroupQuery().groupMember(item.getName()).singleResult().getId()!=item.getGroupId()){
                    identityService.deleteMembership(item.getName(),identityService.createGroupQuery().groupMember(item.getName()).singleResult().getId());
                    identityService.createMembership(item.getName(),item.getGroupId());
                }
                //保存记录
                identityService.saveUser(getUser(item));
                result.addProperty("changeSum",1);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("用户新增记录失败");
        }
        return result;
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
        List<AddMan> items = new Gson().fromJson(Object,new TypeToken<List<AddMan>>(){}.getType());
        //TODO 这里考虑删除用户负责仓库或采购的业务逻辑
        int sum = 0;
        for (AddMan item : items){
            identityService.deleteUser(item.getName());
            sum++;
        }

        JsonObject result = new JsonObject();
        result.addProperty("delSum",sum);
        return new Gson().toJson(result);
    }

    public User getUser(AddMan man){
        User save = new UserEntityImpl();
        save.setId(man.getName());
        save.setPassword(MD5Util.MD5Encode(man.getPassword()));
        save.setFirstName(man.getFirstName());
        save.setLastName(man.getLastName());
        save.setEmail(man.getEmail());
        return save;
    }
}
