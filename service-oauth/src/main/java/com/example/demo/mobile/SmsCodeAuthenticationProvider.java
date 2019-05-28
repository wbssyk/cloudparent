package com.example.demo.mobile;

import com.example.demo.userandpwd.FormLoginCodeAuthenticationToken;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lvhaibao
 * @description
 * @date 2019/1/2 0002 10:34
 */
@Data
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

//    private RedisTemplate<Object, Object> redisTemplate;
    private String aa ;
    // 注意这里的userdetailservice ，因为SmsCodeAuthenticationProvider类没有@Component
    // 所以这里不能加@Autowire，只能通过外面设置才行
    private UserDetailsService userDetailsService;

    private HttpServletRequest httpServletRequest ;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 在这里认证用户信息
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
//        String userandpwd = (String) authenticationToken.getPrincipal();
        String mobile = authentication.getName();
//        String smsCode = (String) authenticationToken.getCredentials();

        //从redis中获取该手机号的验证码
//        String smsCodeFromRedis = aa;
//        if(!smsCode.equals(smsCodeFromRedis)){
//            throw new InternalAuthenticationServiceException("手机验证码不正确");
//        }

        String grant_type = httpServletRequest.getParameter("grant_type");

        SmsCodeAuthenticationToken authenticationResult = null;

        if(grant_type==null||!grant_type.equals("refresh_token")){
            UserDetails user = userDetailsService.loadUserByUsername(mobile);
            if (user == null) {
                throw new InternalAuthenticationServiceException("无法获取用户信息");
            }
            authenticationResult = new SmsCodeAuthenticationToken(user, user.getAuthorities());
            authenticationResult.setDetails(authenticationToken.getDetails());
        }else {
            authenticationResult = new SmsCodeAuthenticationToken(null, null);
            authenticationResult.setDetails(authenticationToken.getDetails());
        }
        return authenticationResult;
    }

    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
