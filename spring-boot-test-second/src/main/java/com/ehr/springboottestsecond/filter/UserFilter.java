package com.ehr.springboottestsecond.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ehr.springboottestsecond.common.Constant;
import com.ehr.springboottestsecond.model.pojo.User;
import com.ehr.springboottestsecond.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 描述: TODO
 */
public class UserFilter implements Filter {
    public static User currentUser;
    @Autowired
    UserService userService;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            filterChain.doFilter(servletRequest,servletResponse);
        }
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //用户登录校验
        HttpSession session = request.getSession();
        currentUser = (User) session.getAttribute(Constant.EHR_USER);
        String token = request.getHeader(Constant.JWT_TOKEN);
        if (token == null) {
            token = request.getParameter("token");
        }
        if (token == null) {
            PrintWriter out = new HttpServletResponseWrapper
                    ((HttpServletResponse) servletResponse).getWriter();
            out.write("{\n" +
                    "    \"code\": 5000,\n" +
                    "    \"msg\": \"未携带Token，禁止访问接口\",\n" +
                    "    \"timestamp\": \"1679723984444\"\n" +
                    "}");
            out.flush();
            out.close();
        }
        //JWT校验器
        Algorithm algorithm = Algorithm.HMAC256(Constant.SALT);
        JWTVerifier verifier = JWT.require(algorithm).build();
        try {
            DecodedJWT jwt = verifier.verify(token);
            currentUser = new User();
            currentUser.setUsername(jwt.getClaim(Constant.USER_NAME).asString());
        } catch (TokenExpiredException e) {
            throw new IOException();
        } catch (JWTDecodeException e) {
            throw new IOException();
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
