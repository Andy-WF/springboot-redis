package com.wf.myredis.springbootredisownconfig.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author： <a href="mailto:18322710712@163.com">wangfei</a>
 * @date: 2018/6/28
 * @version: 1.0.0
 */
@Configuration
@EnableAutoConfiguration
public class RedisConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "order.redis.pool")
    public JedisPoolConfig getRedisConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        return config;
    }

    @Bean
    @ConfigurationProperties(prefix = "order.redis")
    public JedisConnectionFactory getConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setUsePool(true);
        JedisPoolConfig config = getRedisConfig();
        factory.setPoolConfig(config);
        return factory;
    }

    //或 实测可行，一般我以后就都用这个
    //效果：redis客户端里看到的都是直接可读的内容；
    @Bean(name = "redisTemplate")
    public RedisTemplate<Serializable, Serializable> serializableRedisTemplate() {
        final RedisTemplate<Serializable, Serializable> template = new RedisTemplate<Serializable, Serializable>();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        template.setEnableTransactionSupport(false);
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setDefaultSerializer(jdkSerializationRedisSerializer);
        template.setConnectionFactory(getConnectionFactory());
        return template;
    }

//     或 实测可行
//    @Bean(name = "redisTemplate")   //实测，name = "redisTemplate"不能省，@ConditionalOnMissingBean需要这里指明name ；
//    public RedisTemplate<?, ?> getRedisTemplate() {
//        RedisTemplate<?, ?> template = new StringRedisTemplate(getConnectionFactory());
//        return template;
//    }

    @Bean(name = "cacheManager")
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);

        //全局默认失效时间设置  实测可行
//        cacheManager.setDefaultExpiration(10L);

        //逐个失效时间设置 实测可行
        Map<String, Long> expiresMap = new HashMap<>();
        expiresMap.put("value-prefix777",20L);
        expiresMap.put("value-prefix666",10L);
        cacheManager.setExpires(expiresMap);

        cacheManager.setUsePrefix(true);
        return cacheManager;
    }
}
