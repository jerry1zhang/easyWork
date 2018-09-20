package com.zking.biz.task;

import com.zking.biz.BaseBizImpl;
import com.zking.pojo.RepositoryBatch;
import com.zking.pojo.RepositoryOrder;
import com.zking.service.BaseService;
import com.zking.util.PageData;
import com.zking.util.UUIDUtil;
import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Repository
public class TaskBizImpl extends BaseBizImpl implements TaskBiz{
    @Resource
    BaseService<RepositoryOrder> repositoryOrder;
    @Resource
    BaseService<RepositoryBatch> repositoryBatch;
    @Override
    public PageData getTaskList(IdentityService identityService, TaskService taskService, PageData data) {
        PageData result = new PageData();
        //查询属于HR用户组的用户
//		User userInGroup = identityService.createUserQuery().memberOfGroup("hr").singleResult();
//		Assert.notNull(userInGroup);
//		Assert.isTrue(userInGroup.getId().equals("Jonathan"));
//		//查询用户所属组
//		Group groupContainsUser = identityService.createGroupQuery().groupMember("Jonathan").singleResult();
//		Assert.notNull(groupContainsUser);
//		Assert.isTrue(groupContainsUser.getId().equals("hr"));
        //空指针用过滤器处理
        String userName = data.getString("username");
        //查找用户信息
        User userinfo = identityService.createUserQuery().userId(userName).singleResult();
        result.put("User",userinfo);
        //判断用户所在组
        Group groupContainsUser = identityService.createGroupQuery().groupMember(userName).singleResult();
        result.put("Group",groupContainsUser);
        logger.info("用户id:"+userinfo.getId()+"||用户所属组：||ID:"+groupContainsUser.getId()+"||NAME:"+groupContainsUser.getName());
        //查找组所要处理的任务列表
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(groupContainsUser.getId()).list();
        logger.info("用户id:"+userinfo.getId()+"||用户组要处理的任务数:"+tasks.size());
        result.put("tasks",tasks);
//		tasks.get(0).getProcessInstanceId()
//		tasks.get(0).getDescription();
//		tasks.get(0).getCreateTime()
//		tasks.get(0).getDueDate()
//		tasks.get(0).getName()
//		tasks.get(0).getOwner()
        return result;
    }

    public void createRepositoryOrder(RepositoryOrder orderItem, List<RepositoryBatch> batches){
        repositoryOrder.add(orderItem);
        for (RepositoryBatch repositoryBatchItem : batches){
            repositoryBatchItem.setBatchId(UUIDUtil.getUUID());
            repositoryBatchItem.setOrderId(orderItem.getOrderId());
            repositoryBatchItem.setBatchStatus(orderItem.getOrderStatus());
            repositoryBatch.add(repositoryBatchItem);
        }
    }
}
