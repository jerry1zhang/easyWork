package com.zking.dao;

import com.zking.pojo.SaleBatch;

public interface SaleBatchMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(SaleBatch record);

    int insertSelective(SaleBatch record);

    SaleBatch selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(SaleBatch record);

    int updateByPrimaryKey(SaleBatch record);
}