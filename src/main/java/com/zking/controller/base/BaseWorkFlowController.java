package com.zking.controller.base;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zking.enetity.Task;
import com.zking.pojo.RepositoryBatch;
import com.zking.pojo.RepositoryOrder;
import org.activiti.engine.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * BaseWorkFlowController
 *
 * @anthor zhanghy9
 * @date 2018-05-14 9:20
 */
@Repository
public abstract class BaseWorkFlowController extends BaseController{
    @Resource
    TaskService taskService;
    /**
     * 页面跳转
     * @param model
     * @return
     */
    public abstract String init(Model model);
    @RequestMapping("/page")
    private String page(Model model){
        return init(model);
    }



    /**
     * 搜索返回列表
     * @param page
     * @param rows
     * @param selectType
     * @param selectValue
     * @param model
     * @return
     */
    public abstract Object getList(int page,int rows,String selectType,String selectValue,Model model);
    @RequestMapping(value="/info/{page}/{rows}/{selectType}/{selectValue}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    private Object listBySelect(@PathVariable int page,
                                @PathVariable int rows,
                                @PathVariable String selectType,
                                @PathVariable String selectValue,Model model){
        return getList(page,rows,selectType,selectValue,model);
    }

    /**
     * 初始化列表
     * @param page
     * @param rows
     * @param model
     * @return
     */
    public abstract Object getList(int page,int rows, Model model);
    @RequestMapping(value="/info/{page}/{rows}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    private Object list(@PathVariable int page,
                       @PathVariable int rows, Model model){
        return getList(page,rows,model);
    }



    /**
     * 搜索返回分页数据
     * @param rows
     * @param selectType
     * @param selectValue
     * @param model
     * @return
     */
    public abstract Object getPage(int rows,String selectType,String selectValue,Model model);
    @RequestMapping(value="/page/{rows}/{selectType}/{selectValue}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    private Object pageBySelect(@PathVariable int rows,
                                @PathVariable String selectType,
                                @PathVariable String selectValue,Model model){
        return getPage(rows,selectType,selectValue,model);
    }

    /**
     * 初始化分页数据
     * @param rows
     * @param model
     * @return
     */
    public abstract Object getPage(int rows,Model model);
    @RequestMapping(value="/page/{rows}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    private Object page(@PathVariable int rows,Model model){
        return getPage(rows,model);
    }


    /**
     * 新增记录
     * @param Object
     * @param model
     * @return
     */
    public abstract Object add(String Object,Model model);
    @RequestMapping(value="/add/{Object}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    private Object addRecord(@PathVariable String Object,Model model){
        return add(Object,model);
    }


    /**
     * 暂停流程
     * @param Object
     * @param model
     * @return
     */
    public abstract Object pause(String Object,Model model);
    @RequestMapping(value="/pause/{Object}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    private Object pauseRecord(@PathVariable String Object,Model model){
        return pause(Object,model);
    }

    /**
     * 删除流程
     * @param Object
     * @param model
     * @return
     */
    public abstract Object del(String Object,Model model);
    @RequestMapping(value="/del/{Object}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    private Object delRecord(@PathVariable String Object,Model model){
        return del(Object,model);
    }

    /**
     * 完成任务
     * @param Object
     * @param model
     * @return
     */
    public abstract Object finish(String Object,Model model);
    @RequestMapping(value="/finish/{Object}" ,method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    private Object finishTask(@PathVariable String Object,Model model){

        return finish(Object,model);
    }

    /**
     * 完成任务时夹带业务数据
     * @param task
     * @param info
     */
    public void taskInfo(Task task,Map<String,Object> info){};

}
