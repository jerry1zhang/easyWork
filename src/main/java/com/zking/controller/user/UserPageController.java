package com.zking.controller.user;

import com.zking.biz.user.UserBiz;
import com.zking.config.ConfigCode;
import com.zking.config.KeyConfig;
import com.zking.controller.base.BaseController;
import com.zking.enetity.UserBody;
import com.zking.pojo.ListStatic;
import com.zking.service.BaseService;
import com.zking.service.ListStaticService;
import com.zking.service.impl.ListStaticServiceImpl;
import com.zking.util.PageData;
import org.activiti.engine.*;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.UserEntityImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserPageController extends BaseController{
    @Resource
    RepositoryService repositoryService;
    @Resource
    TaskService taskService;
    @Resource
    IdentityService identityService;
    @Resource
    RuntimeService runtimeService;
    @Resource
    HistoryService historyService;
    @Resource
    BaseService list;

    @Resource
    UserBiz userBiz;
    @RequestMapping("/addUser")
    //TODO 考虑将重复代码优化一下
    public String addUser(){
        User user = new UserEntityImpl();
        user.setId(getRequest().getParameter("username"));
        user.setFirstName(getRequest().getParameter("firstname"));
        user.setLastName(getRequest().getParameter("lastname"));
        user.setEmail(getRequest().getParameter("email"));
        user.setPassword(getRequest().getParameter("password"));

        if(!userBiz.addUser(identityService,user,getRequest().getParameter("group"))){
            getRequest().setAttribute("error_info", ConfigCode.ADD_USER_ERROR.getValue());
            return "error";
        }
        return "/user/addUser";
    }

    @RequestMapping("/addGroup")
    public String addGroup(){
        Group group = identityService.newGroup(getRequest().getParameter("groupname"));
        group.setName(getRequest().getParameter("groupname"));
        group.setType(getRequest().getParameter("type"));
        if(!userBiz.addGroup(identityService,group)){
            logger.error("||用户"+getUser().getId()+
                    "||创建组"+getRequest().getParameter("groupname")+"("+getRequest().getParameter("type")+")"+
                    "||失败");
            return "error";
        }
        logger.info("||用户"+getUser().getId()+
                "||创建组"+getRequest().getParameter("groupname")+"("+getRequest().getParameter("type")+")"+
                "||成功");
        return "/user/addGroup";
    }

    /**
     * 用户列表界面刷新
     * @param model
     * @return
     */
    @RequestMapping("/userList")
    public String UserList(Model model){
        String type = getRequest().getParameter("type")==null?"":getRequest().getParameter("type");
        String value = getRequest().getParameter("value")==null?"":getRequest().getParameter("value");
        logger.info("type="+type+"value="+value);
        
        int page = 1;
        int start = (page-1)*KeyConfig.pagenum;
        int end = page*KeyConfig.pagenum;
        List<UserBody> userBodies =  null;
        if(!"".equals(type)&&!"".equals(value)){
            //通过某个值进行搜索
            userBodies = userBiz.getUserList(identityService,start,end,type,value);
        }else{
            userBodies = userBiz.getUserList(identityService,start,end);
        }
        if(userBodies==null){
            return "error";
        }
        PageData pg = new PageData();
        pg.put("list_type", "userList");
        model.addAttribute("userBodies",userBodies);
        model.addAttribute("userList",list.getAllList(pg).get("list"));
        model.addAttribute("listNum",identityService.createUserQuery().count());
        return "/user/userList";
    }

    /**
     * 用户列表表格刷新
     * @param page
     * @param model
     * @return
     */
    @RequestMapping(value="/{page}/{type}/{value}", method= RequestMethod.POST)
    public String getUserListPageTypeValue(@PathVariable int page,
    				@PathVariable String type,
    				@PathVariable String value,
    				Model model){
        String typeValue = type==null?"":type;
        String valueValue = value==null?"":value;
        int start = (page-1)*KeyConfig.pagenum;
        int end = page*KeyConfig.pagenum;
        List<UserBody> userBodies =  null;
        if(!"".equals(type)&&!"".equals(value)){
            //通过某个值进行搜索
            userBodies = userBiz.getUserList(identityService,start,end,typeValue,valueValue);
        }else{
            userBodies = userBiz.getUserList(identityService,start,end);
        }
        if(userBodies==null){
            return "error";
        }
        model.addAttribute("userBodies",userBodies);
        model.addAttribute("num",page);
        return "/user/userFrom";
    }
    
    @RequestMapping(value="/{page}", method= RequestMethod.POST)
    public String getUserListPage(@PathVariable int page,Model model) {
    	return getUserListPageTypeValue(page, null, null, model);
    }


//    @RequestMapping("/groupList")
//    public String GroupList(){
//        if(!userBiz.addGroup(identityService)){
//            return "error";
//        }
//        return "/user/groupList";
//    }

}
