package redisTest.day170907.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TransTest {
	static Jedis jedis = new Jedis();

	public static void main(String[] args) {
		Transaction trans = jedis.multi();
		trans.set("a","3");
		trans.set("b","6");
		trans.set("c","4");
		trans.discard();//»Ø¹ö
		//trans.exec();//Ö´ÐÐ
		String value = jedis.get("a");
		System.out.println(value);
	}
}
