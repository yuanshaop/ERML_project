package com.ehr.springboottestsecond.config;

import com.ehr.springboottestsecond.filter.UserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述: TODO
 */
@Configuration
public class UserFilterConfig {
    @Bean
    public UserFilter userFilter(){
        return new UserFilter();
    }

    @Bean(name = "userFilterConf")
    public FilterRegistrationBean userFilterConf(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(userFilter());
        filterRegistrationBean.addUrlPatterns("/personal/*");
        filterRegistrationBean.addUrlPatterns("/position/*");
        filterRegistrationBean.addUrlPatterns("/department/*");
        filterRegistrationBean.setName("userFilterConf");
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }
}
