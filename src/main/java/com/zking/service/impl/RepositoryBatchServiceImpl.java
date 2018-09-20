package com.zking.service.impl;

import com.zking.dao.RepositoryBatchMapper;
import com.zking.pojo.RepositoryBatch;
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
@Service("repositoryBatch")
public class RepositoryBatchServiceImpl implements BaseService<RepositoryBatch> {
    @Resource
    RepositoryBatchMapper repositoryBatchMapper;
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
        result.put("lists",repositoryBatchMapper.allList(start,rows));
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
        int start = pg.getInt("start");
        int rows = pg.getInt("rows");
        String selectType = pg.getString("selectType");
        String selectValue = pg.getString("selectValue");
        PageData result = new PageData();
        result.put("lists",repositoryBatchMapper.partList(start,rows,selectType,selectValue));
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
        return null;
    }

    /**
     * 通过id删除
     *
     * @param object
     * @return
     */
    @Override
    public boolean del(RepositoryBatch object) {
        return false;
    }

    /**
     * 增加
     *
     * @param object
     * @return
     */
    @Override
    public boolean add(RepositoryBatch object) {
        return repositoryBatchMapper.insert(object)>0;
    }

    /**
     * 修改
     *
     * @param object
     * @return
     */
    @Override
    public boolean update(RepositoryBatch object) {
        return false;
    }

    /**
     * 通过某个值查询
     *
     * @param object
     * @return
     */
    @Override
    public RepositoryBatch select(RepositoryBatch object) {
        return repositoryBatchMapper.select(object);
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
