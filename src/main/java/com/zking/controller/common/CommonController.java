package com.zking.controller.common;

import com.google.gson.Gson;
import com.zking.controller.base.BaseController;
import com.zking.controller.base.BasePageController;
import com.zking.pojo.ListStatic;
import com.zking.pojo.RepositoryBatch;
import com.zking.pojo.RepositoryOrder;
import com.zking.service.BaseService;
import com.zking.util.PageData;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * CommonController
 *
 * @anthor zhanghy9
 * @date 2018-04-23 17:13
 */
@Controller
@RequestMapping("/common")
public class CommonController extends BaseController{
    @Resource
    BaseService<ListStatic> list;
    @Resource
    RepositoryService repositoryService;
    @Resource
    TaskService taskService;
    @Resource
    BaseService<RepositoryOrder> repositoryOrder;
    @Resource
    BaseService<RepositoryBatch> repositoryBatch;

    @RequestMapping("/weihu")
    private String page(Model model){
        return "admin-weihu";
    }

    @RequestMapping("/license")
    private String License(Model model){
        return "admin-info";
    }


    @RequestMapping("/home")
    private String home(Model model){
        return "admin-home";
    }


    @RequestMapping(value="/selectOptionList/{selectType}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object productList(@PathVariable String selectType,
                              Model model){
        PageData sendData = new PageData();
        sendData.put("list_type",selectType);
        PageData resultData = list.getAllList(sendData);
        return new Gson().toJson(resultData.get("list"));
    }

    /**
     * 流程图信息
     * @param business
     * @param model
     * @return
     */
    @RequestMapping(value="/workFlowList/{business}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object workFlowList(@PathVariable String business,
                               Model model){
        PageData sendData = new PageData();
        sendData.put("list_type","workFlow");
        PageData resultData = list.getAllList(sendData);
        List<ListStatic> listInfo =  (List<ListStatic>)resultData.get("list");
        String businessKey = null;
        for (ListStatic item : listInfo){
            if(business.equals(item.getItemName())){
                businessKey = item.getItemValue();
                break;
            }
        }
        if(businessKey==null){
            return new Gson().toJson(resultData.get("list"));
        }
        BpmnModel bpmnModel = repositoryService.getBpmnModel(businessKey);
        if(bpmnModel == null) {
            return new Gson().toJson(resultData.get("list"));
        }
        Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements();
        List<Map<String,String>> list = new ArrayList<>();
        for(FlowElement e : flowElements) {
            Map<String,String> map = new HashMap<>();
            map.put("id",e.getId());
            map.put("name",e.getName());
            if(e.getName()!=null){
                list.add(map);
            }
        }
        return new Gson().toJson(list);
    }

    /**
     * 任务订单信息
     * @param taskId
     * @param model
     * @return
     */
    @RequestMapping(value="/taskInfo/{taskId}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object taskInfo(@PathVariable String taskId,
                               Model model){
        Map<String,Object> variables = taskService.getVariables(taskId);
        for (String key : variables.keySet()){
            logger.debug(">>>>>>>>key="+key+"||value="+variables.get(key));
        }
        String orderId = variables.get("orderId").toString();
        Map<String,Object> result = new HashMap<>(16);
        PageData pageData = new PageData();
        pageData.put("start",0);
        pageData.put("rows",0);
        RepositoryOrder repositoryOrderItem = new RepositoryOrder();
        repositoryOrderItem.setOrderId(orderId);
        result.put("orderInfo",repositoryOrder.select(repositoryOrderItem));
        pageData.put("selectType","order_id");
        pageData.put("selectValue",orderId);
        result.put("batchInfo",repositoryBatch.getAllPageList(pageData).get("lists"));
        return new Gson().toJson(result);
    }



}
