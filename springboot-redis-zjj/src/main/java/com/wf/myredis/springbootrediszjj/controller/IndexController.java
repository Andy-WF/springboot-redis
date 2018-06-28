package com.wf.myredis.springbootrediszjj.controller;

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
    private  StringRedisTemplate stringRedisTemplate;

    //或 同理
    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping("test1")
    @ResponseBody
    public String test1(){
        System.out.println(stringRedisTemplate);

        stringRedisTemplate.opsForValue().set("test1", "100");//向redis里存入数据

        redisTemplate.opsForValue().set("test2", "200");

        return "ok";
    }

}
