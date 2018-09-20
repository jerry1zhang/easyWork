package com.zking.service.impl;

import com.zking.dao.RepositoryMapper;
import com.zking.pojo.Repository;
import com.zking.service.BaseService;
import com.zking.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * RepositoryServiceImpl
 *
 * @anthor zhanghy9
 * @date 2018-05-02 20:42
 */
@Service("repository")
public class RepositoryServiceImpl implements BaseService<Repository> {
    @Resource
    RepositoryMapper repositoryMapper;
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
        result.put("repositorys",repositoryMapper.allList(start,rows));
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
        result.put("repositorys",repositoryMapper.partList(start,rows,selectType,selectValue));
        return result;
    }

    /**
     * 通过id删除
     *
     * @param object
     * @return
     */
    @Override
    public boolean del(Repository object) {
        return repositoryMapper.deleteByPrimaryKey(object.getRepositoryId())>0;
    }

    /**
     * 增加
     *
     * @param object
     * @return
     */
    @Override
    public boolean add(Repository object) {
        return repositoryMapper.insertSelective(object)>0;
    }

    /**
     * 修改
     *
     * @param object
     * @return
     */
    @Override
    public boolean update(Repository object) {
        return repositoryMapper.updateByPrimaryKeySelective(object)>0;
    }

    /**
     * 通过某个值查询
     *
     * @param object
     * @return
     */
    @Override
    public Repository select(Repository object) {
        return repositoryMapper.selectByPrimaryKey(object.getRepositoryId());
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
        return repositoryMapper.listPage(selectType,selectValue);
    }
}
