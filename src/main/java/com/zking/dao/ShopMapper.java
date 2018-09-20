package com.zking.dao;

import com.zking.pojo.Shop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopMapper {
    int deleteByPrimaryKey(Integer shopId);

    int insert(Shop record);

    int insertSelective(Shop record);

    Shop selectByPrimaryKey(Integer shopId);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);
    List<Shop> allList(@Param("start") int start, @Param("rows") int rows);

    List<Shop> partList(@Param("start") int start,
                              @Param("rows") int rows,
                              @Param("selectType") String selectType,
                              @Param("selectValue") String selectValue);

    int listPage(@Param("selectType") String selectType,@Param("selectValue") String selectValue);

}