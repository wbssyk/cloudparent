package com.example.demo.auth.config;

import com.example.demo.mobile.SmsCodeAuthenticationSecurityConfig;
import com.example.demo.mobile.SmsCodeFilter;
import com.example.demo.userandpwd.FormLoginCodeAuthenticationSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @Description: 资源提供端的配置  目前和授权认证端处于同一应用
 * @author: zhoum
 * @Date: 2018-11-22
 * @Time: 16:58
 */
@Configuration
@EnableResourceServer //开启资源提供服务的配置  是默认情况下spring security oauth2的http配置   会被WebSecurityConfigurerAdapter的配置覆盖
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private FormLoginCodeAuthenticationSecurityConfig formLoginCodeAuthenticationSecurityConfig;
    @Override
    public void configure(HttpSecurity http) throws Exception {

        //认证前 先通过 验证码判断
        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
        http.addFilterBefore(smsCodeFilter,UsernamePasswordAuthenticationFilter.class);

        http
//                .authorizeRequests()
//                .antMatchers("/oauth/token")
//                .permitAll()
//                .and()
//                 手机验证码登录
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .authorizeRequests()
                //手机验证码登录地址
                .antMatchers("/mobile/token")
                .permitAll()

                //用户名密码登录
                .and()
                .apply(formLoginCodeAuthenticationSecurityConfig)
                .and()
                .authorizeRequests()
                .antMatchers("/login/token")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .logoutUrl("/oauth/logout")
                .logoutSuccessHandler(customLogoutSuccessHandler())   //退出成功的业务处理
                //禁止跨域
                .and()
                .csrf().disable();



//        http.formLogin().loginProcessingUrl("/form/token").successHandler(smsAuthenticationSuccessHandler);
    }

    @Autowired
    private TokenStore tokenStore;

    /**
     * token过期设置
     *
     * @param resource
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resource) throws Exception {
        resource.tokenStore(tokenStore);
    }

//    @Bean
//    public AuthenticationEntryPoint customAuthenticationEntryPoint(){
//        return new CustomAuthenticationEntryPoint();
//    }

    @Bean
    public LogoutSuccessHandler customLogoutSuccessHandler(){
        return new CustomLogoutSuccessHandler();
    }
}
