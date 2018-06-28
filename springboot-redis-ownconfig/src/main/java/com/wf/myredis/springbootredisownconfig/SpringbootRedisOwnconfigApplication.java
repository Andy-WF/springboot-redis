package com.wf.myredis.springbootredisownconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 启动类里进行项目说明：
 *
 * 一、springboot集成redis ，yml里的配置信息定死使用自定义格式，不能使用spring.redis.xx，怎么集成？==？本项目就是
 * springboot-redis-yml里自定义格式字段配置redis信息；
 * 自己写@Configuration和@bean
 *
 * 二、然后进行springboot-redis-@Cacheable集成，redis做spring-@Cacheable缓存的存放之处，怎么集成？本项目已经实现
 */
//@SpringBootApplication
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})//为了不用mysql数据库，而且不报错
@EnableCaching
public class SpringbootRedisOwnconfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRedisOwnconfigApplication.class, args);
    }
}
