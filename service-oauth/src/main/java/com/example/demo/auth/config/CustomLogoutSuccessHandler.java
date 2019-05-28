package com.example.demo.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 超男 on 2019/4/3.
 */
@Slf4j
@Component
public class CustomLogoutSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler implements LogoutSuccessHandler {

    @Autowired
    private TokenStore tokenStore;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info(" =================  成功退出系统 .... ");
        String authorization = request.getHeader("Authorization");
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", String.valueOf(new Date().getTime()));
        ObjectMapper mapper = new ObjectMapper();
        if (authorization == null) {
            map.put("code", 401);//401
            map.put("msg", "");
            map.put("path", request.getServletPath());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            mapper.writeValue(response.getOutputStream(), map);
//            response.getWriter().write(mapper.writeValueAsString(map));
        }
        if (!authorization.startsWith("Bearer ")) {
            map.put("code", 500);//401
            map.put("msg", "token格式错误");
            map.put("path", request.getServletPath());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(response.getOutputStream(), map);
//            response.getWriter().write(mapper.writeValueAsString(map));
        }
        String access_token = authorization.split(" ")[1];
        if (access_token != null) {
            OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(access_token);
            log.info("token =" + oAuth2AccessToken.getValue());
            tokenStore.removeAccessToken(oAuth2AccessToken);
        }
        map.put("code", 200);//401
        map.put("msg", "退出成功");
        map.put("path", request.getServletPath());
        map.put("timestamp", String.valueOf(new Date().getTime()));
        response.setStatus(HttpServletResponse.SC_OK);
        mapper.writeValue(response.getOutputStream(), map);
//        response.getWriter().write(mapper.writeValueAsString(map));
    }

}
