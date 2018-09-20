package com.zking.dao;

import com.zking.pojo.GoodList;
import com.zking.pojo.GoodListKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodListMapper {
    int deleteByPrimaryKey(GoodListKey key);

    int insert(GoodList record);

    int insertSelective(GoodList record);

    GoodList selectByPrimaryKey(GoodListKey key);

    int updateByPrimaryKeySelective(GoodList record);

    int updateByPrimaryKey(GoodList record);
    List<GoodList> allList(@Param("start") int start, @Param("rows") int rows);

    List<GoodList> partList(@Param("start") int start,
                            @Param("rows") int rows,
                            @Param("selectType") String selectType,
                            @Param("selectValue") String selectValue);

    int listPage(@Param("selectType") String selectType,@Param("selectValue") String selectValue);
}