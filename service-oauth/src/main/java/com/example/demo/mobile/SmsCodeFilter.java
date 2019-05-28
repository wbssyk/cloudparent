package com.example.demo.mobile;


import com.example.demo.exception.ValidateCodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author lvhaibao
 * @description
 * @date 2019/1/2 0002 10:42
 */
@Slf4j
public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean {


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = httpServletRequest.getRequestURI();
        httpServletResponse.setCharacterEncoding("UTF-8");

        if ("/mobile/token".equals(requestURI)) {
            String smsCode = httpServletRequest.getParameter("smsCode");
            // TODO: 2019/3/6 验证码验证 
            if (smsCode == null) {
               throw new ValidateCodeException("验证码为空");
            } else if (!"4444".equals(smsCode)) {
                throw new ValidateCodeException("验证码错误");
            } else {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
