package com.ehr.springboottestsecond.service.impl;

import com.ehr.springboottestsecond.exception.EhrException;
import com.ehr.springboottestsecond.exception.ExceptionEnum;
import com.ehr.springboottestsecond.model.dao.UserMapper;
import com.ehr.springboottestsecond.model.pojo.User;
import com.ehr.springboottestsecond.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述: TODO
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User find(String username) {
        User user = userMapper.findByName(username);
        if (user == null) {
            throw new EhrException(ExceptionEnum.NEED_LOGIN);
        }
        return user;
    }

}
