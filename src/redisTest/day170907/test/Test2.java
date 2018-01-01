package redisTest.day170907.test;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class Test2 {

	@Test
	public void test1(){
		Jedis jedis = new Jedis();
		jedis.flushAll();
		jedis.zadd("1611",55,"zs");//zadd 添加成员
		jedis.zadd("1611",66,"ls");
		jedis.zadd("1611",73,"ww");
		jedis.zadd("1611",62,"zl");
		long count = jedis.zcard("1611");//zcard 获取成员数量
		System.out.println(count);
		
		long c = jedis.zcount("1611",50,70);// 获取指定分数之间的成员数量
		System.out.println(c);
		
		double score = jedis.zincrby("1611",30,"zs");//zincrby 给成员增加指定分数
		System.out.println(score);
		
		System.out.println(jedis.zrange("1611",0,2));//zrange 遍历指定下标之间的成员[及分数](分数从小到大排列）
		
		System.out.println(jedis.zrangeByScore("1611",65,80)); //遍历指定分数之间的成员（及分数）

		long index = jedis.zrank("1611","zs"); //返回成员的下标（分数从小到大排列）
		System.out.println(index);
		
		long mem = jedis.zrem("1611","zl");
		System.out.println(mem);
		
		System.out.println(jedis.zrevrange("1611",0,3));//遍历指定成员[及分数]（分数从大到小排列）

		long len = jedis.zrevrank("1611","ww");//zrevrank 返回成员下标（分数从大到小）
		System.out.println(len);
		
		double scor = jedis.zscore("1611","zs");//获取指定成员的分数
		System.out.println(scor);
		
		System.out.println(jedis.zrevrangeByScore("1611",90,70));//zrevrangeByScore 获取在指定分数之间的成员[及分数]（分数从高到低）
		
		jedis.zremrangeByRank("1611",1,2); //删除下标之间的成员

		jedis.zremrangeByScore("1611",55,60);
		
		System.out.println(jedis.zrange("1611",0,10));
	}
}
