package com.zking.service;

import com.zking.pojo.Menu;

import javax.annotation.Resource;
import java.util.List;

public interface MenuService {
    /**
     * 获得菜单列表
     * @param powerLevel
     * @param level
     * @return
     */
    List<Menu> getMenuList(int powerLevel, int level);

    /**
     * 删除菜单
     * @param menu
     * @return
     */
    boolean del(Menu menu);

    /**
     * 增加菜单
     * @param menu
     * @return
     */
    boolean add(Menu menu);

    /**
     * 修改菜单
     * @param menu
     * @return
     */
    boolean update(Menu menu);
}
