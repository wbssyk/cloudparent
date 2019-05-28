package com.example.demo.userandpwd;

import com.example.demo.auth.EncodePassword;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author lvhaibao
 * @description
 * @date 2019/1/2 0002 10:34
 */
@Data
public class FormLoginCodeAuthenticationProvider implements AuthenticationProvider {

    private EncodePassword passwordEncoder;
    // 注意这里的userdetailservice ，因为SmsCodeAuthenticationProvider类没有@Component
    // 所以这里不能加@Autowire，只能通过外面设置才行
    private UserDetailsService userDetailsService;

    private HttpServletRequest httpServletRequest ;

    /**
     * 在这里认证用户信息
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        FormLoginCodeAuthenticationToken authenticationToken = (FormLoginCodeAuthenticationToken) authentication;
//        String userandpwd = (String) authenticationToken.getPrincipal();
        String username = authentication.getName();
        String password = (String) authenticationToken.getCredentials();

        String grant_type = httpServletRequest.getParameter("grant_type");
//        String refresh_token = httpServletRequest.getParameter("refresh_token");

        FormLoginCodeAuthenticationToken authenticationResult = null;

        if(grant_type==null||!grant_type.equals("refresh_token")){
            String encode = passwordEncoder.encode(password);
            UserDetails user = userDetailsService.loadUserByUsername(username);
            if (user == null) {
                throw new InternalAuthenticationServiceException("无法获取用户信息");
            }
            if(!encode.equals(user.getPassword())){
                throw new InternalAuthenticationServiceException("密码不正确");
            }
            authenticationResult = new FormLoginCodeAuthenticationToken(user,password, user.getAuthorities());
            authenticationResult.setDetails(authenticationToken.getDetails());
        }else {
            authenticationResult = new FormLoginCodeAuthenticationToken(null,null, null);
            authenticationResult.setDetails(authenticationToken.getDetails());
        }
        return authenticationResult;
    }

    public boolean supports(Class<?> authentication) {
        return FormLoginCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
