package com.zking.dao;

import com.zking.pojo.Repository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@org.springframework.stereotype.Repository
public interface RepositoryMapper {
    int deleteByPrimaryKey(Integer repositoryId);

    int insert(Repository record);

    int insertSelective(Repository record);

    Repository selectByPrimaryKey(Integer repositoryId);

    int updateByPrimaryKeySelective(Repository record);

    int updateByPrimaryKey(Repository record);

    List<Repository> allList(@Param("start") int start,@Param("rows") int rows);

    List<Repository> partList(@Param("start") int start,
                              @Param("rows") int rows,
                              @Param("selectType") String selectType,
                              @Param("selectValue") String selectValue);

    int listPage(@Param("selectType") String selectType,@Param("selectValue") String selectValue);

}