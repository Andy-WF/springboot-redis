package com.wf.myredis.springbootredisjedisclient.controller;

import com.wf.myredis.springbootredisjedisclient.configuration.JedisClient;
import com.wf.myredis.springbootredisjedisclient.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author： <a href="mailto:18322710712@163.com">wangfei</a>
 * @date: 2018/6/28
 * @version: 1.0.0
 */
@Controller
public class IndexController {

    @Autowired
    private JedisClient jedisClient;


    //测试缓存
    @Autowired
    private IndexService indexService;


    @RequestMapping("test3")
    @ResponseBody
    public String test3(){

        jedisClient.STRINGS.set("test6","600");

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
