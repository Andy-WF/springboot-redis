package com.wf.myredis.springbootredisjedisclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 启动类里进行项目说明：
 *
 * 一、springboot-redis-网找的自建的jedisClient（里面是自己使用jedis写的5套redis-CRUD）
 * 这是很老的一种用法，并不好，jedisClient和RedisTemplete地位想等，以后都用RedisTemplete
 * 为了妥协老代码，研究一下jedisClient配置；
 *
 *
 * 二、然后进行springboot-redis-@Cacheable集成，redis做spring-@Cacheable缓存的存放之处，怎么集成？
 * 想配置cacheManager必须要RedisTemplete，所以必须配出来一套RedisTemplete，结果就是：
 * 1、
 * redis的增删改查，使用jedisClient客户端操作
 * 2、
 * @Cacheable用redis做存储部分，使用RedisTemplete 客户端
 *
 * 两个都要配，实测可行
 *
 *
 *
 */
//@SpringBootApplication
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})//为了不用mysql数据库，而且不报错
@EnableCaching
public class SpringbootRedisJedisclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRedisJedisclientApplication.class, args);
    }
}
