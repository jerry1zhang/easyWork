package com.zking.service.impl;

import com.zking.dao.ShopMapper;
import com.zking.pojo.Shop;
import com.zking.service.BaseService;
import com.zking.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ShopServiceImpl
 *
 * @anthor zhanghy9
 * @date 2018-05-08 0:12
 */
@Service("shop")
public class ShopServiceImpl implements BaseService<Shop> {
    @Resource
    ShopMapper shopMapper;
    /**
     * 获得全部列表
     *
     * @param pg
     * @return
     */
    @Override
    public PageData getAllList(PageData pg) {

        pg.put("lists",shopMapper.allList(0,0));
        return pg;
    }

    /**
     * 获得全部列表(分页)
     *
     * @param pg
     * @return
     */
    @Override
    public PageData getAllPageList(PageData pg) {
        int start = pg.getInt("start");
        int rows = pg.getInt("rows");
        PageData result = new PageData();
        result.put("lists",shopMapper.allList(start,rows));
        return result;
    }

    /**
     * 获得部分列表（查询）
     *
     * @param pg
     * @return
     */
    @Override
    public PageData getPartList(PageData pg) {
        return null;
    }

    /**
     * 获得部分列表（查询和分页）
     *
     * @param pg
     * @return
     */
    @Override
    public PageData getPartPageList(PageData pg) {
        int start = pg.getInt("start");
        int rows = pg.getInt("rows");
        String selectType = pg.getString("selectType");
        String selectValue = pg.getString("selectValue");
        PageData result = new PageData();
        result.put("lists",shopMapper.partList(start,rows,selectType,selectValue));
        return result;
    }

    /**
     * 通过id删除
     *
     * @param object
     * @return
     */
    @Override
    public boolean del(Shop object) {
        return shopMapper.deleteByPrimaryKey(object.getShopId())>0;
    }

    /**
     * 增加
     *
     * @param object
     * @return
     */
    @Override
    public boolean add(Shop object) {
        return shopMapper.insertSelective(object)>0;
    }

    /**
     * 修改
     *
     * @param object
     * @return
     */
    @Override
    public boolean update(Shop object) {
        return shopMapper.updateByPrimaryKeySelective(object)>0;
    }

    /**
     * 通过某个值查询
     *
     * @param object
     * @return
     */
    @Override
    public Shop select(Shop object) {
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
        String selectType = pg.getString("selectType");
        String selectValue = pg.getString("selectValue");
        return shopMapper.listPage(selectType,selectValue);
    }
}
