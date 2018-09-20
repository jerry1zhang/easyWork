package com.zking.dao;

import com.zking.pojo.UserOther;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOtherMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserOther record);

    int insertSelective(UserOther record);

    UserOther selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserOther record);

    int updateByPrimaryKey(UserOther record);
}