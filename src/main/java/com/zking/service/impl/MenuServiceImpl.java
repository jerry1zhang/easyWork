package com.zking.service.impl;

import com.zking.dao.MenuMapper;
import com.zking.pojo.Menu;
import com.zking.service.BaseService;
import com.zking.service.MenuService;
import com.zking.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("menu")
public class MenuServiceImpl implements BaseService<Menu>{
    @Resource
    MenuMapper menuMapper;


    @Override
    public PageData getAllList(PageData pg) {
        return null;
    }

    @Override
    public PageData getAllPageList(PageData pg) {
        return null;
    }

    @Override
    public PageData getPartList(PageData pg) {
        int level = pg.getInt("level");
        String groupId = pg.getString("groupId");
        List<Menu> list = menuMapper.ListByLevel(level,groupId);
        pg = new PageData();
        pg.put("list",list);
        return pg;
    }

    @Override
    public PageData getPartPageList(PageData pg) {
        return null;
    }

    @Override
    public boolean del(Menu object) {
        return menuMapper.deleteByPrimaryKey(object.getId())!=0;
    }

    @Override
    public boolean add(Menu object) {
        return menuMapper.insertSelective(object)!=0;
    }

    @Override
    public boolean update(Menu object) {
        return menuMapper.updateByPrimaryKeySelective(object)!=0;
    }

    @Override
    public Menu select(Menu object) {
        return null;
    }

    /**
     * 列表和
     *
     * @param pg
     * @return
     */
    @Override
    public int listSum(PageData pg) {
        return 0;
    }
}
