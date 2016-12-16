package com.finance.communication.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis工具类
 * 
 */
public final class RedisUtil {

	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8
	private static int MAX_IDLE = 200;

	// private static int TIMEOUT = 10000;
	private static int TIMEOUT = 3000;

	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
	// private static boolean TEST_ON_BORROW = true;
	private static boolean TEST_ON_BORROW = false;
	private static boolean TEST_ON_RETURN = false;

	private static JedisPool jedisPool = null;

	/**
	 * 初始化Redis连接池--阿里
	 */
	static {
		try {
			ConfigUtil configUtil = SpringContextHolder.getBean("configUtil");

			JedisPoolConfig config = new JedisPoolConfig();
			// 最大空闲连接数, 应用自己评估，不要超过AliCloudDB for Redis每个实例最大的连接数
			config.setMaxIdle(MAX_IDLE);
			// 最大连接数, 应用自己评估，不要超过AliCloudDB for Redis每个实例最大的连接数
			config.setMaxTotal(300);
			config.setTestOnBorrow(TEST_ON_BORROW);
			config.setTestOnReturn(TEST_ON_RETURN);
			if (jedisPool == null) {
				jedisPool = new JedisPool(config, configUtil.getRedisAddress(), configUtil.getRedisPort(), TIMEOUT,
						ConfigTools.decrypt(configUtil.getRedisAuth()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取Jedis实例
	 * 
	 * @return
	 */
	public synchronized static Jedis getJedis() {
		try {
			if (jedisPool != null) {
				Jedis resource = jedisPool.getResource();
				return resource;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	public static void returnResource(final Jedis jedis) {
		if (jedis != null) {
			// jedisPool.returnResource(jedis);
			jedis.close();
		}
	}
}
