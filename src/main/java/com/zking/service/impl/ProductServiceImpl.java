package com.zking.service.impl;

import com.zking.dao.ProducerMapper;
import com.zking.dao.ProductMapper;
import com.zking.pojo.Producer;
import com.zking.pojo.Product;
import com.zking.service.BaseService;
import com.zking.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * ProductServiceImpl
 *
 * @anthor zhanghy9
 * @date 2018-04-17 15:27
 */
@Service("product")
public class ProductServiceImpl implements BaseService<Product> {
    @Resource
    ProductMapper productMapper;
    @Resource
    ProducerMapper producerMapper;

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
        result.put("products",productMapper.productList(start,rows));
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
        result.put("products",productMapper.productPartList(start,rows,selectType,selectValue));
        return result;
    }

    /**
     * 通过id删除
     *
     * @param object
     * @return
     */
    @Override
    public boolean del(Product object) {
        return productMapper.deleteByPrimaryKey(object.getProductId())>0;
    }

    /**
     * 增加
     *
     * @param object
     * @return
     */
    @Override
    public boolean add(Product object) {
        return productMapper.insertSelective(object)>0;
    }

    /**
     * 修改
     *
     * @param object
     * @return
     */
    @Override
    public boolean update(Product object) {
        return productMapper.updateByPrimaryKeySelective(object)>0;
    }

    /**
     * 通过某个值查询
     *
     * @param object
     * @return
     */
    @Override
    public Product select(Product object) {
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
        int sum = productMapper.productListSum(selectType,selectValue);
        return sum;
    }
}
