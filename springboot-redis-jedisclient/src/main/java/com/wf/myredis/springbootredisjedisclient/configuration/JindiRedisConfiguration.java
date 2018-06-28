package com.wf.myredis.springbootredisjedisclient.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * 类: JindiRedisConfiguration <br/>
 * 描述: <br/>
 * 时间: 2017年4月10日 下午2:45:14 <br/>
 * 方法: 
 * <ul>
 * 	<li></li>
 * </ul>
 * @author wangxw
 */
@Configuration
public class JindiRedisConfiguration {


	//1、redis的增删改查，使用jedisClient客户端操作
	@Bean
	public JedisPoolConfig poolConfig(
			@Value("${redis.maxTotal}") Integer maxTotal,
			@Value("${redis.maxIdle}") Integer maxIdle,
			@Value("${redis.minIdle}") Integer minIdle,
			@Value("${redis.maxWaitMillis}") Long maxWaitMillis,
			@Value("${redis.testOnBorrow}") Boolean testOnBorrow,
			@Value("${redis.testWhileIdle}") Boolean testWhileIdle,
			@Value("${redis.numTestsPerEvictionRun}") Integer numTestsPerEvictionRun,
			@Value("${redis.timeBetweenEvictionRunsMillis}") Long timeBetweenEvictionRunsMillis
			){
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(maxTotal);
		poolConfig.setMaxIdle(maxIdle);
		poolConfig.setMinIdle(minIdle);
		poolConfig.setMaxWaitMillis(maxWaitMillis);
		
		poolConfig.setTestOnBorrow(testOnBorrow);
		poolConfig.setTestWhileIdle(testWhileIdle);
		poolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
		poolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		return poolConfig;
	}
	
	@Bean(destroyMethod="destroy")
	public JedisClient jedisClient(JedisPoolConfig poolConfig,
			@Value("${redis.ip}") String host,
			@Value("${redis.port}") Integer port,
			@Value("${redis.password}") String password,
			@Value("${redis.database}") Integer database
			){
		JedisClient jedisClient = new JedisClient(poolConfig, host, port, Protocol.DEFAULT_TIMEOUT, password, database);

		return jedisClient;
	}


//	2、@Cacheable用redis做存储部分，使用RedisTemplete 客户端
//	必须配置RedisTemplate一套，用于cacheManager


	@Bean
	public JedisConnectionFactory getConnectionFactory(JedisPoolConfig poolConfig,
													   @Value("${redis.ip}") String host,
													   @Value("${redis.port}") Integer port,
													   @Value("${redis.password}") String password,
													   @Value("${redis.database}") Integer database) {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setUsePool(true);
		factory.setHostName(host);
		factory.setPort(port);
		factory.setPassword(password);
		factory.setDatabase(database);
		factory.setPoolConfig(poolConfig);
		return factory;
	}


	//或 实测可行，一般我以后就都用这个
	//效果：redis客户端里看到的都是直接可读的内容；
	@Bean(name = "redisTemplate")
	public RedisTemplate<Serializable, Serializable> serializableRedisTemplate(JedisConnectionFactory factory) {
		final RedisTemplate<Serializable, Serializable> template = new RedisTemplate<Serializable, Serializable>();
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		template.setEnableTransactionSupport(false);
		template.setKeySerializer(stringRedisSerializer);
		template.setHashKeySerializer(stringRedisSerializer);
		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.setDefaultSerializer(jdkSerializationRedisSerializer);
		template.setConnectionFactory(factory);
		return template;
	}

	@Bean(name = "cacheManager")
	public CacheManager cacheManager(RedisTemplate redisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		cacheManager.setUsePrefix(true);

//        设置全部过期时间  30min
        cacheManager.setDefaultExpiration(10);

		//或 逐个设置过期时间
//		Map<String, Long> expiresMap = new HashMap<>();
//		expiresMap.put("value-prefix777",20L);
//		expiresMap.put("value-prefix666",10L);
//		cacheManager.setExpires(expiresMap);

		return cacheManager;
	}









}

