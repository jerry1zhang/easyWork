package com.zking.dao;

import com.zking.pojo.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper {
    int deleteByPrimaryKey(Integer productId);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer productId);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> productList(@Param("start") Integer start, @Param("rows") Integer rows);

    List<Product> productPartList(@Param("start") Integer start,
                                  @Param("rows") Integer rows,
                                  @Param("selectType") String selectType,
                                  @Param("selectValue") String selectValue);

    int productListSum(@Param("selectType") String selectType,@Param("selectValue") String selectValue);
}