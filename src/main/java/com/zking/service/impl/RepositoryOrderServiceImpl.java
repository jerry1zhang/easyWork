package com.zking.service.impl;

import com.zking.dao.RepositoryOrderMapper;
import com.zking.pojo.RepositoryOrder;
import com.zking.service.BaseService;
import com.zking.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * RepositoryOrderServiceInpl
 *
 * @anthor zhanghy9
 * @date 2018-05-14 10:13
 */
@Service("repositoryOrder")
public class RepositoryOrderServiceImpl implements BaseService<RepositoryOrder> {
    @Resource
    RepositoryOrderMapper repositoryOrderMapper;
    /**
     * 获得全部列表
     *
     * @param pg
     * @return
     */
    @Override
    public PageData getAllList(PageData pg) {
        int start = pg.getInt("start");
        int rows = pg.getInt("rows");
        PageData result = new PageData();
        result.put("lists",repositoryOrderMapper.allList(start,rows));
        return result;
    }

    /**
     * 获得全部列表(分页)
     *
     * @param pg
     * @return
     */
    @Override
    public PageData getAllPageList(PageData pg) {
        return null;
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
        return null;
    }

    /**
     * 通过id删除
     *
     * @param object
     * @return
     */
    @Override
    public boolean del(RepositoryOrder object) {
        return false;
    }

    /**
     * 增加
     *
     * @param object
     * @return
     */
    @Override
    public boolean add(RepositoryOrder object) {

        return repositoryOrderMapper.insert(object)>0;
    }

    /**
     * 修改
     *
     * @param object
     * @return
     */
    @Override
    public boolean update(RepositoryOrder object) {
        return false;
    }

    /**
     * 通过某个值查询
     *
     * @param object
     * @return
     */
    @Override
    public RepositoryOrder select(RepositoryOrder object) {
        return repositoryOrderMapper.selectByPrimaryKey(object.getOrderId());
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
