package com.wf.myredis.springbootredisownconfig.controller;

import com.wf.myredis.springbootredisownconfig.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author： <a href="mailto:18322710712@163.com">wangfei</a>
 * @date: 2018/6/28
 * @version: 1.0.0
 */
@Controller
public class IndexController {


    //这里引入的就是自己配置的@Bean(name = "redisTemplate") ，这里用的就是加入自己指定各种配置的bean了，就是要这个效果；
    @Autowired
    private RedisTemplate redisTemplate;

    //或 实测，不配StringRedisTemplate的@Bean也能直接用，只不过是默认配置；
    @Autowired
    private  StringRedisTemplate stringRedisTemplate;

    //测试缓存
    @Autowired
    private IndexService indexService;


    @RequestMapping("test3")
    @ResponseBody
    public String test3(){
        System.out.println(stringRedisTemplate);
        System.out.println(redisTemplate);

        redisTemplate.opsForValue().set("test5", "500");

        //或
        stringRedisTemplate.opsForValue().set("test3", "300");//向redis里存入数据

        return "ok";
    }


    //测试springboot-redis做@Cacheable缓存
    @RequestMapping(value="test4")
    @ResponseBody
    public String test4(){
        //这个方法只要一被调用，就会往redis里放一个k-v对；
        indexService.saveCache("10");

        indexService.saveCache2("11");

        indexService.saveCache3("12");

        return "ok!";
    }


}
