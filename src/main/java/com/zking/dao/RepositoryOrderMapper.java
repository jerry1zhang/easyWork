package com.zking.dao;

import com.zking.pojo.RepositoryOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryOrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(RepositoryOrder record);

    int insertSelective(RepositoryOrder record);

    RepositoryOrder selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(RepositoryOrder record);

    int updateByPrimaryKey(RepositoryOrder record);

    List<RepositoryOrder> allList(@Param("start") int start, @Param("rows") int rows);

    List<RepositoryOrder> partList(@Param("start") int start,
                              @Param("rows") int rows,
                              @Param("selectType") String selectType,
                              @Param("selectValue") String selectValue);

    int listPage(@Param("selectType") String selectType,@Param("selectValue") String selectValue);

}