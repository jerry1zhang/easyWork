package com.zking.controller.base;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * BasePageController
 *
 * @anthor zhanghy9
 * @date 2018-05-02 8:40
 */
public abstract class BasePageController extends BaseController{
    /**
     * 页面跳转
     * @param model
     * @return
     */
    public abstract String getPage(Model model);

    /**
     * 搜索返回列表
     * @param page
     * @param rows
     * @param selectType
     * @param selectValue
     * @param model
     * @return
     */
    public abstract Object listBySelect(int page,int rows,String selectType,String selectValue,Model model);

    /**
     * 初始化列表
     * @param page
     * @param rows
     * @param model
     * @return
     */
    public abstract Object list(int page,int rows,Model model);

    /**
     * 搜索返回分页数据
     * @param rows
     * @param selectType
     * @param selectValue
     * @param model
     * @return
     */
    public abstract Object pageBySelect(int rows,String selectType,String selectValue,Model model);

    /**
     * 初始化分页数据
     * @param rows
     * @param model
     * @return
     */
    public abstract Object page(int rows,Model model);

    /**
     * 新增记录
     * @param Object
     * @param model
     * @return
     */
    public abstract Object add(String Object,Model model);

    /**
     * 更新记录
     * @param Object
     * @param model
     * @return
     */
    public abstract Object edit(String Object,Model model);

    /**
     * 删除记录
     * @param Object
     * @param model
     * @return
     */
    public abstract Object del(String Object,Model model);
}

