package com.qm.dao;

import com.qm.domain.entity.BdUser;

public interface BdUserDao {
    int deleteByPrimaryKey(String id);

    int insert(BdUser record);

    int insertSelective(BdUser record);

    BdUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BdUser record);

    int updateByPrimaryKey(BdUser record);
    
    BdUser getUser(BdUser user);
}