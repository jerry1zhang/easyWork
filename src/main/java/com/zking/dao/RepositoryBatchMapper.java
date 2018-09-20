package com.zking.dao;

import com.zking.pojo.RepositoryBatch;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryBatchMapper {
    int deleteByPrimaryKey(String batchId);

    int insert(RepositoryBatch record);

    int insertSelective(RepositoryBatch record);

    RepositoryBatch selectByPrimaryKey(String batchId);

    RepositoryBatch select(RepositoryBatch record);

    int updateByPrimaryKeySelective(RepositoryBatch record);

    int updateByPrimaryKey(RepositoryBatch record);

    List<RepositoryBatch> allList(@Param("start") int start, @Param("rows") int rows);

    List<RepositoryBatch> partList(@Param("start") int start,
                                   @Param("rows") int rows,
                                   @Param("selectType") String selectType,
                                   @Param("selectValue") String selectValue);

    int listPage(@Param("selectType") String selectType,@Param("selectValue") String selectValue);

}