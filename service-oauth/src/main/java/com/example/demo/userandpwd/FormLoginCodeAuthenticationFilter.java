package com.example.demo.userandpwd;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lvhaibao
 * @description
 * @date 2019/1/2 0002 10:36
 */
public class FormLoginCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private boolean postOnly = true;

    // 用户名和手机号参数变量
    private String username = "username";
    private String password = "password";

    FormLoginCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login/token", "POST"));
    }


    /**
     * 添加未认证用户认证信息，然后在provider里面进行正式认证
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     */
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {
        if (postOnly && !httpServletRequest.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + httpServletRequest.getMethod());
        }

        String username = obtainUsername(httpServletRequest);
        String password = obtainPassword(httpServletRequest);

        if (username == null) {
            username = "";
        }

        FormLoginCodeAuthenticationToken authRequest = new FormLoginCodeAuthenticationToken(username, password);
        // Allow subclasses to set the "details" property
        setDetails(httpServletRequest, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        if(this.logger.isDebugEnabled()) {
//            this.logger.debug("Authentication success. Updating SecurityContextHolder to contain: " + authResult);
//        }
//
//        SecurityContextHolder.getContext().setAuthentication(authResult);
//        this.rememberMeServices.loginSuccess(request, response, authResult);
//        if(this.eventPublisher != null) {
//            this.eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
//        }
//
//        this.successHandler.onAuthenticationSuccess(request, response, authResult);
//    }

    /**
     * 用户名和密码
     */
    private String obtainUsername(HttpServletRequest request) {
        return request.getParameter(username);
    }

    private String obtainPassword(HttpServletRequest request) {
        return request.getParameter(password);
    }

    private void setDetails(HttpServletRequest request, FormLoginCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setUsername(String username) {
        Assert.hasText(username, "Username parameter must not be empty or null");
        this.username = username;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
