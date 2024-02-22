package com.ehr.springboottestsecond.model.dao;

import com.ehr.springboottestsecond.model.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 描述: TODO
 */
@Repository
public interface UserMapper {
    User findByName(@Param("username") String username);
}
