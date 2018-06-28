package com.wf.myredis.springbootrediszjj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * 启动类里进行项目说明：
 *
 * springboot-redis-yml里默认约定字段配置redis信息，spring:redis:xxx；
 * 各种工厂、bean都不需要自己写，因为默认规则的配置信息将直接被spring-autoconfig项目里@Bean拿去用，相当于自己写了@Bean
 *
 */
//@SpringBootApplication
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})//为了不用mysql数据库，而且不报错
public class SpringbootRedisZjjApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRedisZjjApplication.class, args);
    }
}
