package com.zking.biz.user;

import com.zking.enetity.UserBody;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;

import java.util.List;

public interface UserBiz {
    boolean addUser(IdentityService identityService, User user, String groupId);
    boolean addGroup(IdentityService identityService, Group group);
    UserBody getUser(IdentityService identityService, User user);
    List<UserBody> getUserList(IdentityService identityService, int start, int end);
    List<UserBody> getUserList(IdentityService identityService, int start, int end,String type,String value);
    List<Group> getGroupList(IdentityService identityService, int start, int end);
}
