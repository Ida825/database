package redisTest.day170906.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisTest {
	/**
	 * hash类型
	 */
	
	//@Test
	public void test1(){
		Jedis jedis = new Jedis();
		jedis.hset("user:1","username","zs");//hset给key中的字段赋值
		jedis.hset("user:1","age","20");
		
		String name = jedis.hget("user:1","username");//hget获取key中的指定字段的值
		System.out.println(name);
		
		boolean bool1 = jedis.hexists("user:1","age");//hexists判断key中是否存在某字段
		System.out.println(bool1);//true
		boolean bool2 = jedis.hexists("user:1","sex");
		System.out.println(bool2);//false
		
		long len = jedis.hlen("user:1");//获取key中字段的数量
		System.out.println(len);
		
		jedis.hdel("user:1","age");//删除key中的filed字段
		System.out.println(jedis.hexists("user:1","age"));
		
		jedis.hset("user:1","age","30");
		System.out.println(jedis.hgetAll("user:1"));//获取key中所有的filed和value值
	}
	
	//@Test
	public void test(){
		Jedis jedis = new Jedis();
		jedis.select(1);
		Map<String,String> map = new HashMap<String, String>();
		map.put("username","张三");
		map.put("password","123456");
		map.put("age","18");
		jedis.hmset("user:1",map);//hmset 同时设置多个
		//System.out.println(jedis.hgetAll("user:1"));//获取key中所有的filed和value
		
		//System.out.println(jedis.hmget("user:1","username","password","age"));//同时获取多个field的值
		
		jedis.hsetnx("user:1","grade","1611");//如果字段不存在就添加并赋值，否则不操作
		Map m = jedis.hgetAll("user:1");
		System.out.println(m);
		
		System.out.println(jedis.hincrBy("user:1","age",3));
		
		System.out.println(jedis.hkeys("user:1"));//获取所有的key
		
		System.out.println(jedis.hvals("user:1"));//获取所有的value
		
		
	}
	
	/**
	 * list类型
	 */
	
	//@Test
	public void test3(){
		Jedis jedis = new Jedis();
		jedis.lpush("grade1611","zs");//在list头部添加值
		jedis.lpush("grade1611","ls");
		jedis.lpush("grade1611","ww");
		jedis.lpush("grade1611","zl");
		System.out.println(jedis.lrange("grade1611",0,100));
		jedis.del("grade1611");

		jedis.select(2);
		jedis.rpush("grade1607","张三");//在list尾部添加值
		jedis.rpush("grade1607","李四");
		jedis.rpush("grade1607","王五");
		jedis.rpush("grade1607","赵六");
		System.out.println(jedis.lrange("grade1607",0,100));//获取指定位置的数据
		
		String str1 = jedis.lpop("grade1607");//从头部弹出key 的值
		System.out.println(str1);
		
		String str2 = jedis.rpop("grade1607");//从尾部弹出key的值
		System.out.println(str2);
		
	}
	
	//@Test
	public void test4(){
		Jedis jedis = new Jedis();
		jedis.select(3);
		jedis.lpush("user:1","aa");
		jedis.lpush("user:1","bb");
		jedis.lpush("user:1","cc");
		jedis.lpush("user:1","dd");
		System.out.println(jedis.lrange("user:1",0,10));
		
		jedis.lpush("user:2","AA");
		jedis.lpush("user:2","BB");
		jedis.lpush("user:2","CC");
		System.out.println(jedis.lrange("user:2",0,10));
		
		/*jedis.del("user:1");
		jedis.del("user:2");*/
		
		jedis.rpoplpush("user:1","user:2");//从一个list尾部弹出插入到另一个list的头部
		List<String> str1 = jedis.lrange("user:1",0,10);
		System.out.println(str1);
		List<String> str2 = jedis.lrange("user:2",0,10);
		System.out.println(str2);
		
	}
	
	//@Test
	public void test5(){
		Jedis jedis = new Jedis();
		jedis.select(4);
		jedis.lpush("grade1","a");
		jedis.lpush("grade1","b");
		long len = jedis.llen("grade1");//llen 返回key的长度
		System.out.println(len);
		
		jedis.lrem("grade1",1,"a");	//lrem删除前面几个值为value的元素	
		List<String> str = jedis.lrange("grade1",0,10);
		System.out.println(str);
		
		jedis.lset("grade1",1,"x");//lset按下标赋值
		List<String> str2 = jedis.lrange("grade1",0,10);
		System.out.println(str2);
	}
	
	/**
	 * set类型
	 */
	
	@Test
	public void test6(){
		Jedis j = new Jedis();
		j.select(5);
		j.sadd("u1","a","b","c","d","e");//sadd 添加值 无序且不重复
		Set<String> str = j.smembers("u1");//smembers遍历集合
		System.out.println(str);
		
		long counts = j.scard("u1");//scard 获取key的成员数量
		System.out.println(counts);
		
		j.srem("u1","c");//srem删除指定成员
		Set<String> str1 = j.smembers("u1");
		System.out.println(str1);
		
		boolean bool = j.sismember("u1","c");//sismember 判断成员是否存在
		System.out.println(bool);
		
		j.spop("u1");//spop随机弹出一个值（删除）
		Set<String> str2 = j.smembers("u1");
		System.out.println(str2);
	}
	
	
}
