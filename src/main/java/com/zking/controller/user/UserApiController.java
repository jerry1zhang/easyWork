package com.zking.controller.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zking.biz.user.UserBiz;
import com.zking.config.KeyConfig;
import com.zking.controller.base.BaseController;
import com.zking.enetity.UserBody;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/apiuser")
public class UserApiController extends BaseController{

    @Resource
    UserBiz userBiz;
    @Resource
    IdentityService identityService;

    @RequestMapping(value="/userList/{page}/{rows}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object userList(@PathVariable int page,
                           @PathVariable int rows,
                           Model model){
        userBiz.getUserList(identityService,(page-1)* KeyConfig.pagenum,page*KeyConfig.pagenum);
        int totalData = Integer.valueOf(String.valueOf(identityService.createUserQuery().count()));
        int showData = KeyConfig.pagenum;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("totalData",totalData);
        jsonObject.addProperty("showData",showData);
        return new Gson().toJson(jsonObject);
    }

    /**
     * 返回所有用户数据
     * @param model
     * @return
     */
    @RequestMapping(value="/userList" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object userList(Model model){
        List<User> users = identityService.createUserQuery().list();
        return new Gson().toJson(getUserBodys(users));
    }

    @RequestMapping(value="/userList/{groupId}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object userList(@PathVariable String groupId, Model model){
        List<User> users = identityService.createUserQuery().memberOfGroup(groupId).list();
        return new Gson().toJson(getUserBodys(users));
    }

    /**
     * 用户列表封装用户和组信息
     * @param users
     * @return
     */
    private List<UserBody> getUserBodys(List<User> users){
        List<UserBody> userBodies = new ArrayList<>();
        for (User user : users){
            Group group = identityService.createGroupQuery().groupMember(user.getId()).singleResult();
            UserBody item = new UserBody();
            item.setUser(user);
            item.setGroup(group);
            userBodies.add(item);
        }
        return userBodies;
    }
}
