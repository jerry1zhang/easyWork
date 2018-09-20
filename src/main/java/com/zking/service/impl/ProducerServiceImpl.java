package com.zking.service.impl;

import com.zking.dao.ProducerMapper;
import com.zking.pojo.Producer;
import com.zking.service.BaseService;
import com.zking.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ProducerServiceImpl
 *
 * @anthor zhanghy9
 * @date 2018-04-24 9:31
 */
@Service("producer")
public class ProducerServiceImpl implements BaseService<Producer>{
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
        pg.put("producers",producerMapper.producerList(null,null));
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
        pg.put("producers",producerMapper.producerList(start,rows));
        return pg;
    }

    /**
     * 获得部分列表（查询）
     *
     * @param pg
     * @return
     */
    @Override
    public PageData getPartList(PageData pg) {
        return pg;
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
        pg.put("producers",producerMapper.producerPartList(start,rows,selectType,selectValue));
        return pg;
    }

    /**
     * 通过id删除
     *
     * @param object
     * @return
     */
    @Override
    public boolean del(Producer object) {
        return producerMapper.deleteByPrimaryKey(object.getProducerId())>0;
    }

    /**
     * 增加
     *
     * @param object
     * @return
     */
    @Override
    public boolean add(Producer object) {
        return producerMapper.insertSelective(object)>0;
    }

    /**
     * 修改
     *
     * @param object
     * @return
     */
    @Override
    public boolean update(Producer object) {
        return producerMapper.updateByPrimaryKeySelective(object)>0;
    }

    /**
     * 通过某个值查询
     *
     * @param object
     * @return
     */
    @Override
    public Producer select(Producer object) {
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
        return producerMapper.producerListSum(selectType,selectValue);
    }
}
