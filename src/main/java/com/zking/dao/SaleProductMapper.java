package com.zking.dao;

import com.zking.pojo.SaleProduct;
import com.zking.pojo.SaleProductKey;

public interface SaleProductMapper {
    int deleteByPrimaryKey(SaleProductKey key);

    int insert(SaleProduct record);

    int insertSelective(SaleProduct record);

    SaleProduct selectByPrimaryKey(SaleProductKey key);

    int updateByPrimaryKeySelective(SaleProduct record);

    int updateByPrimaryKey(SaleProduct record);
}