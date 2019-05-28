package com.shi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @Description: 配置资源服务器
 * @author: zhoum
 * @Date: 2018-11-26
 * @Time: 15:08
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ResourceServiceConfig extends ResourceServerConfigurerAdapter {

//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .exceptionHandling()
//                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
//                .and()
//                .authorizeRequests()
//                .antMatchers("/**").permitAll()
//                //跨域配置
//                .and().cors().configurationSource(request -> {
//            CorsConfiguration corsConfiguration = new CorsConfiguration();
//            corsConfiguration.addAllowedHeader("*");
//            corsConfiguration.addAllowedOrigin(request.getHeader("Origin"));
//            corsConfiguration.addAllowedMethod("*");
//            corsConfiguration.setAllowCredentials(true);
//            corsConfiguration.setMaxAge(3600L);
//            return corsConfiguration;
//        });
//    }
//


    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()//禁用了csrf（跨站请求伪造）功能
                .authorizeRequests()//限定签名成功的请求
                //必须认证过后才可以访问;注意：hasAnyRole 会默认加上ROLE_前缀，而hasAuthority不会加前缀
                .antMatchers("/user/**","/govern/**").hasAnyRole("GG") // 在角色过滤的时候需要注意user角色需要加角色前缀
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/servicehi/**").authenticated();
//                 免验证请求
//                .antMatchers("/serviceoauth/**").permitAll();
    }

    /**
     * token过期设置
     * @param resource
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resource) throws Exception {

        //这里把自定义异常加进去
        resource.authenticationEntryPoint(new AuthExceptionEntryPoint())
                .accessDeniedHandler(new CustomAccessDeniedHandler());
    }
}
