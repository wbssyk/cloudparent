package com.shi.feginservice;

import com.shi.hystric.HiHystric;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by 超男 on 2019/3/27.
 */

/**
 * feign方式调用，和平时http调用一样，方法参数支持 json 格式 ，但是要和远程调用的接口参数方式一致
 */

@FeignClient(value = "service-hi",fallback = HiHystric.class)
public interface FeginServiceHi {
    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    String sayHiFromClientOne(@RequestParam(value = "name") String name);
}
