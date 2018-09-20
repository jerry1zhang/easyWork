package com.zking.service.impl;

import com.zking.dao.GoodListMapper;
import com.zking.pojo.GoodList;
import com.zking.pojo.GoodListKey;
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
@Service("goodList")
public class GoodListServiceImpl implements BaseService<GoodList> {
    @Resource
    GoodListMapper goodListMapper;
    /**
     * 获得全部列表
     *
     * @param pg
     * @return
     */
    @Override
    public PageData getAllList(PageData pg) {
        return null;
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
        result.put("lists",goodListMapper.allList(start,rows));
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
        result.put("lists",goodListMapper.partList(start,rows,selectType,selectValue));
        return result;
    }

    /**
     * 通过id删除
     *
     * @param object
     * @return
     */
    @Override
    public boolean del(GoodList object) {
        return false;
    }

    /**
     * 增加
     *
     * @param object
     * @return
     */
    @Override
    public boolean add(GoodList object) {
        return goodListMapper.insertSelective(object)>0;
    }

    /**
     * 修改
     *
     * @param object
     * @return
     */
    @Override
    public boolean update(GoodList object) {
        return goodListMapper.updateByPrimaryKeySelective(object)>0;
    }

    /**
     * 通过某个值查询
     *
     * @param object
     * @return
     */
    @Override
    public GoodList select(GoodList object) {
        GoodListKey goodListKey = new GoodListKey();
        goodListKey.setrSId(object.getrSId());
        goodListKey.setProductId(object.getProductId());
        return goodListMapper.selectByPrimaryKey(goodListKey);
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
        return goodListMapper.listPage(selectType,selectValue);
    }
}
