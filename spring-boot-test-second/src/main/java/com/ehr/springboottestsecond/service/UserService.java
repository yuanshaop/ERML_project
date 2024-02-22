package com.ehr.springboottestsecond.service;

import com.ehr.springboottestsecond.model.pojo.User;

/**
 * 描述: TODO
 */
public interface UserService {

    User find(String username);
}
