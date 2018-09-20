package com.zking.dao;

import com.zking.pojo.RepositoryProduct;
import com.zking.pojo.RepositoryProductKey;

public interface RepositoryProductMapper {
    int deleteByPrimaryKey(RepositoryProductKey key);

    int insert(RepositoryProduct record);

    int insertSelective(RepositoryProduct record);

    RepositoryProduct selectByPrimaryKey(RepositoryProductKey key);

    int updateByPrimaryKeySelective(RepositoryProduct record);

    int updateByPrimaryKey(RepositoryProduct record);
}