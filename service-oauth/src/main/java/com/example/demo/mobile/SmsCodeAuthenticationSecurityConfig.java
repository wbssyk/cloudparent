package com.example.demo.mobile;

import com.example.demo.handler.MyAuthenticationFailHandler;
import com.example.demo.handler.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lvhaibao
 * @description
 * @date 2019/1/2 0002 10:39
 */
@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailHandler myAuthenticationFailHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private HttpServletRequest httpServletRequest;

//    @Autowired
//    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
        smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(myAuthenticationFailHandler);

        SmsCodeAuthenticationProvider smsCodeDaoAuthenticationProvider = new SmsCodeAuthenticationProvider();
        smsCodeDaoAuthenticationProvider.setUserDetailsService(userDetailsService);
        smsCodeDaoAuthenticationProvider.setAa("123456");
        smsCodeDaoAuthenticationProvider.setHttpServletRequest(httpServletRequest);
        http.authenticationProvider(smsCodeDaoAuthenticationProvider)
                .addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
