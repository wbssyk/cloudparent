package com.shi.hystric;

import com.shi.feginservice.FeginServiceHi;
import org.springframework.stereotype.Component;

/**
 * Created by 超男 on 2019/3/27.
 */
@Component
public class HiHystric implements FeginServiceHi {
    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry "+name;
    }
}
