package com.wf.myredis.springbootredisownconfig.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author： <a href="mailto:18322710712@163.com">wangfei</a>
 * @date: 2018/6/28
 * @version: 1.0.0
 */
@Service
public class IndexService {

    //这个方法只要一被调用，就会往redis里放一个k-v对；
    @Cacheable(value= "value-prefix666",key = "#id")
    public Object saveCache(String id){
        String s = new String("cache-value666");
        return  s;
    }


    @Cacheable(value= "value-prefix666",key = "#id")
    public Object saveCache2(String id){
        String s = new String("cache-value666");
        return  s;
    }

    @Cacheable(value= "value-prefix777",key = "#id")
    public Object saveCache3(String id){
        String s = new String("cache-value777");
        return  s;
    }
}
