package com.zking.controller.repository;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.zking.config.KeyConfig;
import com.zking.config.OrderConfig;
import com.zking.controller.base.BasePageController;
import com.zking.controller.base.BaseWorkFlowController;
import com.zking.pojo.GoodList;
import com.zking.pojo.Repository;
import com.zking.pojo.RepositoryBatch;
import com.zking.pojo.RepositoryOrder;
import com.zking.service.BaseService;
import com.zking.util.TimeUtil;
import com.zking.util.UUIDUtil;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * GoodInController
 *
 * @anthor zhanghy9
 * @date 2018-05-10 23:27
 */
@Controller
@RequestMapping("rep/in")
public class GoodInController extends BaseWorkFlowController{
    @Resource
    RepositoryService repositoryService;
    @Resource
    TaskService taskService;
    @Resource
    IdentityService identityService;
    @Resource
    RuntimeService runtimeService;
    @Resource
    BaseService<RepositoryOrder> repositoryOrder;
    @Resource
    BaseService<RepositoryBatch> repositoryBatch;
    @Resource
    BaseService<Repository> repository;
    @Resource
    BaseService<GoodList> goodList;
    /**
     * 页面跳转
     *
     * @param model
     * @return
     */
    @Override
    public String init(Model model) {
        return "repository/in";
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
    public Object getList(int page, int rows, String selectType, String selectValue, Model model) {
        return null;
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
    public Object getList(int page, int rows, Model model) {
        //判断身份
        Group group = (Group) getSession().getAttribute("Group");
        User user = (User) getSession().getAttribute("User");
        int start = (page-1)*rows;
//
        List<Task> taskList;
        if("a92ab458da6f49f199a1d4cab32ed057".equals(group.getId())){
            taskList = taskService.createTaskQuery().orderByTaskDueDate().asc().listPage(start,rows);
        }else{
            taskList = taskService.createTaskQuery().taskCandidateOrAssigned(user.getId()).orderByTaskDueDate().asc().listPage(start,rows);
        }


        Assert.notNull(taskList,"任务列表为空");
        List<com.zking.enetity.Task> taskList1 = new ArrayList<>();
        for (Task item:taskList){
            logger.debug("taskid="+item.getId());
            com.zking.enetity.Task zTask = new com.zking.enetity.Task();
            zTask.setId(item.getId());
            zTask.setName(item.getName());
            zTask.setDescription(item.getDescription());
            zTask.setOwner(item.getOwner());
            zTask.setAssignee(item.getAssignee());
            zTask.setDueDate(item.getDueDate());
            zTask.setTaskDefinitionKey(item.getTaskDefinitionKey());
//            taskList1.add(new com.zking.enetity.Task().getActivitiTask(item));
            taskList1.add(zTask);
        }
        return new Gson().toJson(taskList1);
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
    public Object getPage(int rows, String selectType, String selectValue, Model model) {
        Group group = (Group) getSession().getAttribute("Group");
        int sum = new Long(taskService.createTaskQuery().taskCandidateGroup(group.getId()).count()).intValue();
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
    public Object getPage(int rows, Model model) {
        Group group = (Group) getSession().getAttribute("Group");
        User user = (User) getSession().getAttribute("User");
        int sum;
        if("a92ab458da6f49f199a1d4cab32ed057".equals(group.getId())){
            sum = new Long(taskService.createTaskQuery().count()).intValue();
        }else{
            sum = new Long(taskService.createTaskQuery().taskCandidateOrAssigned(user.getId()).count()).intValue();
        }
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
    public Object add(String Object, Model model) {
        Assert.notNull(Object,"||"+getClass().getName()+"||新增数据为空");
        RepositoryBatch batcheInfo = new Gson().fromJson(Object, RepositoryBatch.class);
        RepositoryOrder orderInfo = new RepositoryOrder();
        orderInfo.setOrderId(UUIDUtil.getUUID());
        orderInfo.setOrderType(OrderConfig.ORDER_IN_REPOSITORY);
        orderInfo.setOrderStatus(OrderConfig.ORDER_STATUS_DO);
        orderInfo.setMoney(batcheInfo.getBatchRs1());
        batcheInfo.setOrderId(orderInfo.getOrderId());
        batcheInfo.setBatchId(UUIDUtil.getUUID());
        batcheInfo.setBatchStatus(OrderConfig.BATCH_STATUS_DO);
        //todo 暂时的订单业务逻辑
        synchronized (getSession().getAttribute("User")){
            repositoryOrder.add(orderInfo);
            repositoryBatch.add(batcheInfo);
        }
        Date duedate = TimeUtil.getAddDay(KeyConfig.CAIGOUENDDAY);
        //创建流程
        Map<String, Object> createInfo = new HashMap<String, Object>();
        //夹带任务信息
        RepositoryOrder order = new RepositoryOrder();
        createInfo.put("applyuser",batcheInfo.getBatchRs2());
        createInfo.put("duedate",duedate);
        createInfo.put("orderId",orderInfo.getOrderId().toString());
        //启动流程
        runtimeService.startProcessInstanceByKey("createGoodList",getUser().getId(),createInfo);
        JsonObject result = new JsonObject();
        result.addProperty("addSum",1);
        return new Gson().toJson(result);
    }

    /**
     * 暂停流程
     *
     * @param Object
     * @param model
     * @return
     */
    @Override
    public Object pause(String Object, Model model) {
        return null;
    }

    /**
     * 删除记录
     *
     * @param Object
     * @param model
     * @return
     */
    @Override
    public Object del(String Object, Model model) {
        return null;
    }

    /**
     * 完成任务
     *
     * @param Object
     * @param model
     * @return
     */
    @Override
    public Object finish(String Object, Model model) {
        Gson gson = new Gson();
        List<com.zking.enetity.Task> tasks = gson.fromJson(Object,new TypeToken<List<com.zking.enetity.Task>>(){}.getType());
        com.zking.enetity.Task task = tasks.get(0);
        Task activitiTask = taskService.createTaskQuery().taskId(task.getId()).singleResult();
        Map<String,Object> info = new HashMap<>(16);
        int sum = 1;
        switch (activitiTask.getTaskDefinitionKey()){
            case "getGood":
                getGood(activitiTask,info);
                break;
            case "goodCheck" :
                goodCheck(activitiTask,info);
                break;
            default:
                sum = 0;
                break;
        }
        JsonObject result = new JsonObject();
        result.addProperty("finSum",sum);
        return new Gson().toJson(result);
    }

    /**
     * 完成任务时夹带业务数据
     *
     * @param task
     * @param info
     */
    @Override
    public void taskInfo(com.zking.enetity.Task task, Map<String, Object> info) {
        info = new HashMap<>();
        info.put("kuguan",task.getNextTasker());
    }

    private void getGood(Task task,Map<String,Object> info){
        Map<String, Object> taskVariables = taskService.getVariables(task.getId());
        RepositoryBatch repositoryBatchItem = new RepositoryBatch();
        repositoryBatchItem.setOrderId(taskVariables.get("orderId").toString());
        info.put("orderId",taskVariables.get("orderId").toString());
        repositoryBatchItem = repositoryBatch.select(repositoryBatchItem);
        Repository repositoryItem = new Repository();
        repositoryItem.setRepositoryId(repositoryBatchItem.getRepositoryId());
        repositoryItem = repository.select(repositoryItem);
        info.put("kuguan",repositoryItem.getRepositoryAdmin());
        taskService.complete(task.getId(),info);
    }

    private void goodCheck(Task task,Map<String,Object> info){
        Map<String, Object> taskVariables = taskService.getVariables(task.getId());
        RepositoryBatch repositoryBatchItem = new RepositoryBatch();
        repositoryBatchItem.setOrderId(taskVariables.get("orderId").toString());
        info.put("orderId",taskVariables.get("orderId").toString());
        repositoryBatchItem = repositoryBatch.select(repositoryBatchItem);
        GoodList goodListItem = new GoodList();
        goodListItem.setrSId(repositoryBatchItem.getRepositoryId());
        goodListItem.setProductId(repositoryBatchItem.getProductId());
        GoodList goodListItem2 = goodList.select(goodListItem);
        if(goodListItem2!=null){
            goodListItem.setNumber(goodListItem2.getNumber()+repositoryBatchItem.getNumber());
            goodList.update(goodListItem);
        }else{
            goodListItem.setNumber(repositoryBatchItem.getNumber());
            goodList.add(goodListItem);
        }
        taskService.complete(task.getId());
    }
}
