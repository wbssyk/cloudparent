package com.example.demo.controller;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2018-11-23
 * @Time: 10:45
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/current")
    public String getCurrentUser(Principal principal) {
        System.out.println(principal);
        return "111";
    }

    /**
     *客户端根据token获取用户
     */
    @RequestMapping("/me")
    public Principal user2(OAuth2Authentication principal) {
        System.out.println(principal);
        return principal;
    }
}
