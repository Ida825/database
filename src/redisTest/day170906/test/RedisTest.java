package redisTest.day170906.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisTest {
	/**
	 * hash����
	 */
	
	//@Test
	public void test1(){
		Jedis jedis = new Jedis();
		jedis.hset("user:1","username","zs");//hset��key�е��ֶθ�ֵ
		jedis.hset("user:1","age","20");
		
		String name = jedis.hget("user:1","username");//hget��ȡkey�е�ָ���ֶε�ֵ
		System.out.println(name);
		
		boolean bool1 = jedis.hexists("user:1","age");//hexists�ж�key���Ƿ����ĳ�ֶ�
		System.out.println(bool1);//true
		boolean bool2 = jedis.hexists("user:1","sex");
		System.out.println(bool2);//false
		
		long len = jedis.hlen("user:1");//��ȡkey���ֶε�����
		System.out.println(len);
		
		jedis.hdel("user:1","age");//ɾ��key�е�filed�ֶ�
		System.out.println(jedis.hexists("user:1","age"));
		
		jedis.hset("user:1","age","30");
		System.out.println(jedis.hgetAll("user:1"));//��ȡkey�����е�filed��valueֵ
	}
	
	//@Test
	public void test(){
		Jedis jedis = new Jedis();
		jedis.select(1);
		Map<String,String> map = new HashMap<String, String>();
		map.put("username","����");
		map.put("password","123456");
		map.put("age","18");
		jedis.hmset("user:1",map);//hmset ͬʱ���ö��
		//System.out.println(jedis.hgetAll("user:1"));//��ȡkey�����е�filed��value
		
		//System.out.println(jedis.hmget("user:1","username","password","age"));//ͬʱ��ȡ���field��ֵ
		
		jedis.hsetnx("user:1","grade","1611");//����ֶβ����ھ���Ӳ���ֵ�����򲻲���
		Map m = jedis.hgetAll("user:1");
		System.out.println(m);
		
		System.out.println(jedis.hincrBy("user:1","age",3));
		
		System.out.println(jedis.hkeys("user:1"));//��ȡ���е�key
		
		System.out.println(jedis.hvals("user:1"));//��ȡ���е�value
		
		
	}
	
	/**
	 * list����
	 */
	
	//@Test
	public void test3(){
		Jedis jedis = new Jedis();
		jedis.lpush("grade1611","zs");//��listͷ�����ֵ
		jedis.lpush("grade1611","ls");
		jedis.lpush("grade1611","ww");
		jedis.lpush("grade1611","zl");
		System.out.println(jedis.lrange("grade1611",0,100));
		jedis.del("grade1611");

		jedis.select(2);
		jedis.rpush("grade1607","����");//��listβ�����ֵ
		jedis.rpush("grade1607","����");
		jedis.rpush("grade1607","����");
		jedis.rpush("grade1607","����");
		System.out.println(jedis.lrange("grade1607",0,100));//��ȡָ��λ�õ�����
		
		String str1 = jedis.lpop("grade1607");//��ͷ������key ��ֵ
		System.out.println(str1);
		
		String str2 = jedis.rpop("grade1607");//��β������key��ֵ
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
		
		jedis.rpoplpush("user:1","user:2");//��һ��listβ���������뵽��һ��list��ͷ��
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
		long len = jedis.llen("grade1");//llen ����key�ĳ���
		System.out.println(len);
		
		jedis.lrem("grade1",1,"a");	//lremɾ��ǰ�漸��ֵΪvalue��Ԫ��	
		List<String> str = jedis.lrange("grade1",0,10);
		System.out.println(str);
		
		jedis.lset("grade1",1,"x");//lset���±긳ֵ
		List<String> str2 = jedis.lrange("grade1",0,10);
		System.out.println(str2);
	}
	
	/**
	 * set����
	 */
	
	@Test
	public void test6(){
		Jedis j = new Jedis();
		j.select(5);
		j.sadd("u1","a","b","c","d","e");//sadd ���ֵ �����Ҳ��ظ�
		Set<String> str = j.smembers("u1");//smembers��������
		System.out.println(str);
		
		long counts = j.scard("u1");//scard ��ȡkey�ĳ�Ա����
		System.out.println(counts);
		
		j.srem("u1","c");//sremɾ��ָ����Ա
		Set<String> str1 = j.smembers("u1");
		System.out.println(str1);
		
		boolean bool = j.sismember("u1","c");//sismember �жϳ�Ա�Ƿ����
		System.out.println(bool);
		
		j.spop("u1");//spop�������һ��ֵ��ɾ����
		Set<String> str2 = j.smembers("u1");
		System.out.println(str2);
	}
	
	
}
