package com.zking.dao;

import com.zking.pojo.SaleOrder;

public interface SaleOrderMapper {
    int deleteByPrimaryKey(Integer batchId);

    int insert(SaleOrder record);

    int insertSelective(SaleOrder record);

    SaleOrder selectByPrimaryKey(Integer batchId);

    int updateByPrimaryKeySelective(SaleOrder record);

    int updateByPrimaryKey(SaleOrder record);
}