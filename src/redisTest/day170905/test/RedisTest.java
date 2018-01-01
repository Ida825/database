package redisTest.day170905.test;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisTest {
	//@Test
	@SuppressWarnings("resource")
	public void test1(){
		Jedis jedis = new Jedis();
		jedis.set("name", "zs");
		String s = "sss";
		System.out.println(s);
	}
	
	@Test
	public void test2(){
		Jedis jedis = new Jedis();
		jedis.select(0);//进入第一个数据库
		long size = jedis.dbSize();
		System.out.println(size);
		long name = jedis.append("name", "1611");//追加
		System.out.println(name);
		String str = jedis.get("name");//获取值
		System.out.println(str);
		jedis.flushAll();
	}
	
	//@Test
	public void test3(){
		Jedis jedis = new Jedis();
		jedis.select(3);
		jedis.setnx("grade","1610");//如果不存在就赋值，否则不操作
		jedis.get("grade");
		jedis.setnx("grade", "1611");
		
	}
	
	@Test
	public void test4(){
		Jedis jedis = new Jedis();
		System.out.println(jedis.ping());
		jedis.select(0);
		jedis.set("name","lily" );
		System.out.println(jedis.get("name"));
		jedis.append("name", "ssss");
		System.out.println(jedis.get("name"));
		
		
	}
	
	
}
