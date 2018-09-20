package com.zking.biz.menu;

import com.zking.enetity.MenuBody;

import javax.annotation.Resource;
import java.util.List;
@Resource()
public interface MenuBiz {
    /**
     * 初始化菜单
     * @return
     */
    List<MenuBody> initMenu(String groupId);



}
