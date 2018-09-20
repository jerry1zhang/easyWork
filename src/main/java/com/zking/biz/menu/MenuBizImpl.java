package com.zking.biz.menu;

import com.zking.biz.BaseBizImpl;
import com.zking.config.ConfigCode;
import com.zking.enetity.MenuBody;
import com.zking.pojo.Menu;
import com.zking.service.BaseService;
import com.zking.service.MenuService;
import com.zking.service.impl.MenuServiceImpl;
import com.zking.util.PageData;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Repository
public class MenuBizImpl extends BaseBizImpl implements MenuBiz{

    @Resource
    BaseService<Menu> menu;

    @Override
    public List<MenuBody> initMenu(String groupId) {
        PageData pg = new PageData();
        pg.put("level",ConfigCode.LEVEL_BIGMENU.getValue());
        pg.put("groupId",groupId);
        List<Menu> one = (List<Menu>)menu.getPartList(pg).get("list");
        pg.put("level",ConfigCode.LEVEL_SMALLMENU.getValue());
        List<Menu> two = (List<Menu>)menu.getPartList(pg).get("list");
        if (one==null || two == null) {
            return null;
        }
        List<MenuBody> list = new ArrayList<>();
        List<Menu> smallMenu;
        MenuBody menuBody;
        for (Menu e:one ) {
            smallMenu = new ArrayList<>();
            menuBody = new MenuBody();
            menuBody.setBigMenu(e);
            for (Menu e2: two) {
                if(e.getId()==e2.getUp()){
                    smallMenu.add(e2);
                }
            }
            menuBody.setSmallMenu(smallMenu);
            list.add(menuBody);
        }
        return list;
    }
}
