package com.shi.controller;

import com.shi.feginservice.FeginServiceHi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 超男 on 2019/3/27.
 */
@RestController
public class TestController {

    @Autowired
    private FeginServiceHi feginServiceHi;

    @GetMapping("/hi")
    public String home(@RequestParam(value = "name", defaultValue = "forezp") String name) {
        String fegin = feginServiceHi.sayHiFromClientOne("fegin");
        return fegin;
    }
}
