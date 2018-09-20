package com.zking.dao;

import com.zking.pojo.Producer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProducerMapper {
    int deleteByPrimaryKey(Integer producerId);

    int insert(Producer record);

    int insertSelective(Producer record);

    Producer selectByPrimaryKey(Integer producerId);

    int updateByPrimaryKeySelective(Producer record);

    int updateByPrimaryKey(Producer record);

    List<Producer> producerList(@Param("start") Integer start, @Param("rows") Integer rows);

    List<Producer> producerPartList(@Param("start") Integer start,
                                  @Param("rows") Integer rows,
                                  @Param("selectType") String selectType,
                                  @Param("selectValue") String selectValue);

    int producerListSum(@Param("selectType") String selectType,@Param("selectValue") String selectValue);

    List<Producer> selectByName(@Param("producerName") String producerName);
}