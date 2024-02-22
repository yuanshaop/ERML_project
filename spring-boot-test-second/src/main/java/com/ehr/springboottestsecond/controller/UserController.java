package com.ehr.springboottestsecond.controller;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ehr.springboottestsecond.common.Constant;
import com.ehr.springboottestsecond.common.Result;
import com.ehr.springboottestsecond.exception.EhrException;
import com.ehr.springboottestsecond.exception.ExceptionEnum;
import com.ehr.springboottestsecond.model.pojo.User;
import com.ehr.springboottestsecond.model.vo.UserVO;
import com.ehr.springboottestsecond.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.apache.shiro.crypto.hash.Md5Hash;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;

/**
 * 描述: 用户Controller
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    private static TimedCache<String, String> cache = CacheUtil.newTimedCache(60 * 1000);

    @PostMapping("/user/login")
    //登录接口
    public Result userLogin(@RequestBody @Valid UserVO user){
        String username = user.getUsername();
        String password = user.getPassword();
        String timestamp = user.getTimestamp();
        if (ObjectUtils.isEmpty(timestamp) || ObjectUtils.isEmpty(user.getCode())) {
            throw new EhrException(ExceptionEnum.NEED_LOGIN);
        }
        if (ObjectUtils.isEmpty(cache.get(timestamp))) {
            throw new EhrException(ExceptionEnum.NEED_LOGIN);
        }
        String code = cache.get(timestamp);
        if (!code.equals(user.getCode())) {
            throw new EhrException(ExceptionEnum.NEED_LOGIN);
        }
        User us = userService.find(username);
        Md5Hash md5Hash = new Md5Hash(password, Constant.SALT, 100);
        String toHex = md5Hash.toHex();
        if (toHex.equals(us.getPassword())) {
            Algorithm algorithm=Algorithm.HMAC256(Constant.SALT);
            String token= JWT.create().withClaim(Constant.USER_NAME,username)
                    .withExpiresAt(new Date(System.currentTimeMillis() + Constant.EXPIRE_TIME))
                    .sign(algorithm);
            return Result.success(token);
        }

        return Result.error(ExceptionEnum.NEED_LOGIN);
    }

    @GetMapping("/image")
    public void captcha(@RequestParam(value = "timestamp") String timestamp, HttpServletResponse response) throws IOException {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
        response.setHeader("Cache-Control", "post-check=0,pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("image/png");
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100, 4, 20);
        lineCaptcha.write(response.getOutputStream());
        cache.put(timestamp, lineCaptcha.getCode());
    }

    @GetMapping("/user/get")
    public Result get(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
        return Result.success("admin");
    }
}
