package com.zking.dao;

import com.zking.pojo.ListStatic;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListStaticMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ListStatic record);

    int insertSelective(ListStatic record);

    ListStatic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ListStatic record);

    int updateByPrimaryKey(ListStatic record);

    List<ListStatic> selectByListType(String listType);


}