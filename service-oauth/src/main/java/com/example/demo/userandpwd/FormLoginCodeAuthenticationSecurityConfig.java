package com.example.demo.userandpwd;


import com.example.demo.auth.EncodePassword;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lvhaibao
 * @description
 * @date 2019/1/2 0002 10:39
 */
@Component
public class FormLoginCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailHandler myAuthenticationFailHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private EncodePassword encodePassword;

    @Autowired
    private HttpServletRequest request;

//    private final HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

//    @Autowired
//    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        FormLoginCodeAuthenticationFilter formLoginCodeAuthenticationFilter = new FormLoginCodeAuthenticationFilter();
        formLoginCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        formLoginCodeAuthenticationFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        formLoginCodeAuthenticationFilter.setAuthenticationFailureHandler(myAuthenticationFailHandler);

        FormLoginCodeAuthenticationProvider formLoginCodeAuthenticationProvider = new FormLoginCodeAuthenticationProvider();
        formLoginCodeAuthenticationProvider.setUserDetailsService(userDetailsService);
        formLoginCodeAuthenticationProvider.setPasswordEncoder(encodePassword);
        formLoginCodeAuthenticationProvider.setHttpServletRequest(request);
        http.authenticationProvider(formLoginCodeAuthenticationProvider)
                .addFilterAfter(formLoginCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
