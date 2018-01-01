package redisTest.day170907.test;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class Test2 {

	@Test
	public void test1(){
		Jedis jedis = new Jedis();
		jedis.flushAll();
		jedis.zadd("1611",55,"zs");//zadd ��ӳ�Ա
		jedis.zadd("1611",66,"ls");
		jedis.zadd("1611",73,"ww");
		jedis.zadd("1611",62,"zl");
		long count = jedis.zcard("1611");//zcard ��ȡ��Ա����
		System.out.println(count);
		
		long c = jedis.zcount("1611",50,70);// ��ȡָ������֮��ĳ�Ա����
		System.out.println(c);
		
		double score = jedis.zincrby("1611",30,"zs");//zincrby ����Ա����ָ������
		System.out.println(score);
		
		System.out.println(jedis.zrange("1611",0,2));//zrange ����ָ���±�֮��ĳ�Ա[������](������С�������У�
		
		System.out.println(jedis.zrangeByScore("1611",65,80)); //����ָ������֮��ĳ�Ա����������

		long index = jedis.zrank("1611","zs"); //���س�Ա���±꣨������С�������У�
		System.out.println(index);
		
		long mem = jedis.zrem("1611","zl");
		System.out.println(mem);
		
		System.out.println(jedis.zrevrange("1611",0,3));//����ָ����Ա[������]�������Ӵ�С���У�

		long len = jedis.zrevrank("1611","ww");//zrevrank ���س�Ա�±꣨�����Ӵ�С��
		System.out.println(len);
		
		double scor = jedis.zscore("1611","zs");//��ȡָ����Ա�ķ���
		System.out.println(scor);
		
		System.out.println(jedis.zrevrangeByScore("1611",90,70));//zrevrangeByScore ��ȡ��ָ������֮��ĳ�Ա[������]�������Ӹߵ��ͣ�
		
		jedis.zremrangeByRank("1611",1,2); //ɾ���±�֮��ĳ�Ա

		jedis.zremrangeByScore("1611",55,60);
		
		System.out.println(jedis.zrange("1611",0,10));
	}
}
