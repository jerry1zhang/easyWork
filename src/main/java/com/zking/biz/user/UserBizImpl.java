package com.zking.biz.user;

import com.zking.biz.BaseBizImpl;
import com.zking.config.ConfigCode;
import com.zking.enetity.UserBody;
import com.zking.util.MD5Util;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class UserBizImpl extends BaseBizImpl implements UserBiz{

    @Override
    public boolean addUser(IdentityService identityService,User arg0,String GroupId) {
        //新建用户
        try {
            User user = identityService.newUser(arg0.getId());
            user.setFirstName(arg0.getFirstName());
            user.setLastName(arg0.getLastName());
            user.setEmail(arg0.getEmail());
            user.setPassword(MD5Util.MD5Encode(arg0.getPassword()));
            identityService.saveUser(user);
            identityService.createMembership(arg0.getId(),GroupId);
        }catch (Exception e){
            logger.error(ConfigCode.ADD_USER_ERROR+"||error:"+e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean addGroup(IdentityService identityService,Group arg0) {
        //新建组
        if(arg0!=null) {
            Group group = identityService.newGroup(arg0.getId());
            group.setName(arg0.getName());
            group.setType(arg0.getType());
            identityService.saveGroup(group);
        }else{
            logger.error("组新增失败");
        }
        return true;
    }

    @Override
    public UserBody getUser(IdentityService identityService,User user) {
        UserBody userBody = new UserBody();
        //查找用户信息
        User userinfo = identityService.createUserQuery().userId(user.getId()).singleResult();
        if(userinfo.getPassword().equals(MD5Util.MD5Encode(user.getPassword()))) {
            userBody.setUser(userinfo);
            //判断用户所在组
            Group groupContainsUser = identityService.createGroupQuery().groupMember(user.getId()).singleResult();
            userBody.setGroup(groupContainsUser);
            logger.info("用户id:" + userinfo.getId() + "||用户所属组：||ID:" + groupContainsUser.getId() + "||NAME:" + groupContainsUser.getName()+"||登陆");
        }else {
            logger.info("用户id:"+user.getId()+"||错误信息:"+ConfigCode.USER_ERROR_1.getValue());
            return null;
        }
        return userBody;
    }

    @Override
    public  List<UserBody> getUserList( IdentityService identityService,int start,int end) {
        List<UserBody> userBodies = new ArrayList<>();
        List<User> users = identityService.createUserQuery().listPage(start,end);
        UserBody userBody;
        for (User e: users) {
            userBody = new UserBody();
            userBody.setUser(e);
            userBody.setGroup(identityService.createGroupQuery().groupMember(e.getId()).singleResult());
            userBodies.add(userBody);
        }
        return userBodies;
    }

    @Override
    public List<UserBody> getUserList(IdentityService identityService, int start, int end, String type, String value) {
        List<UserBody> userBodies = new ArrayList<>();
        List<User> users = identityService.createUserQuery().listPage(start,end);
        long num = 0;
        switch (type){
            case "username":
                users = identityService.createUserQuery().userId(value).listPage(start,end);
                num  = identityService.createUserQuery().userId(value).count();
                break;
            case "group":
                users = identityService.createUserQuery().memberOfGroup(value).listPage(start,end);
                num = identityService.createUserQuery().memberOfGroup(value).count();
                break;
            case "email":
                users = identityService.createUserQuery().userEmailLike(value).listPage(start,end);
                num = identityService.createUserQuery().userEmailLike(value).count();
                break;
            case "firstname":
                users = identityService.createUserQuery().userFirstNameLike(value).listPage(start,end);
                num = identityService.createUserQuery().userFirstNameLike(value).count();
                break;
            case "lastname":
                users = identityService.createUserQuery().userLastNameLike(value).listPage(start,end);
                num = identityService.createUserQuery().userLastNameLike(value).count();
                break;
        }
        UserBody userBody;
        for (User e: users) {
            userBody = new UserBody();
            userBody.setUser(e);
            userBody.setGroup(identityService.createGroupQuery().groupMember(e.getId()).singleResult());
            userBody.setNum(num);
            userBodies.add(userBody);
        }
        return userBodies;
    }

    @Override
    public List<Group> getGroupList( IdentityService identityService,int start,int end) {
        List<Group> groups = identityService.createGroupQuery().listPage(start,end);
        return groups;
    }


}
