package com.example.demo.auth.config;

import com.example.demo.auth.EncodePassword;
import com.example.demo.auth.MyUserDetaiService;
import com.example.demo.mobile.SmsCodeAuthenticationFilter;
import com.example.demo.mobile.SmsCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Description: //是默认情况下spring security的http配置 优于ResourceServerConfigurerAdapter的配置
 * @author: zhoum
 * @Date: 2018-11-22
 * @Time: 17:06
 */
@Configuration              //开启三种可以在方法上面加权限控制的注解
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityAdaptConfig extends WebSecurityConfigurerAdapter {

    /**
     * 获取用户的验证配置类
     */
    @Autowired
    private MyUserDetaiService userDetailsService;

    /**
     * 密码加密
     */
    @Autowired
    private EncodePassword passwordEncoder;

    /**
     * 自定义手机验证码认证
     */


    /**
     * 配置资源服务端的http设置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
//        http.addFilterBefore(smsCodeFilter,UsernamePasswordAuthenticationFilter.class);

        http
                .authorizeRequests()//定义哪些url需要被保护  哪些不需要保护
//                .antMatchers("/oauth/token", "oauth/check_token")
//                .permitAll()//定义这两个链接不需要登录可访问
                .antMatchers("/oauth/logout")
                .permitAll()
//                .and()
//
//                //手机验证码资源
//                .apply(smsCodeAuthenticationSecurityConfig)
//                .and()
//                .authorizeRequests()
//                .antMatchers("userandpwd/token")
//                .permitAll()
//
//                .and()
//                .apply(formLoginCodeAuthenticationSecurityConfig)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/login/token")
//                .permitAll()
                .anyRequest().authenticated() //其他的都需要登录

//                .and()
//                .formLogin().loginPage("/login")//如果未登录则跳转登录的页面   这儿可以控制登录成功和登录失败跳转的页面
//                .usernameParameter("username").passwordParameter("password").permitAll()//定义号码与密码的parameter

                //防止跨站请求  spring security中默认开启
                .and()
                .csrf().disable();
    }

    /**
     * 权限管理器  AuthorizationServerConfigurerAdapter认证中心需要的AuthenticationManager需要
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //配置登录user验证处理器  以及密码加密器  好让认证中心进行验证
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

    }

    /**
     * 需要配置这个支持password模式
     * support password grant type
     *
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationManager();
    }
}
