package com.example.demo.auth;

import com.example.demo.auth.config.UserDetail;
import com.example.demo.domain.entity.User;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2018-11-22
 * @Time: 15:07
 */
@Component
public class MyUserDetaiService implements UserDetailsService {

    @Autowired
    private IUserService userService;

    /**
     * 启动刷新token授权类型，会判断用户是否还是存活的
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User currentUser = userService.getByName(s);
        if ( currentUser == null ) {
            throw new UsernameNotFoundException("用户没用找到");
        }

        return new UserDetail(currentUser);
    }
}
