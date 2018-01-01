package redisTest.day170906.work;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class Client {
	//用户订单
	public static List<Map> list = new ArrayList<Map>();
	static final String ORDER_KEY ="orderList"; 
	/**
	 * 模拟有多个用户发送订单请求  先模拟三个淘宝
	 * @param args
	 */
	static Jedis jedis = new Jedis();
	static{
		jedis.flushAll();
		Map map1 = new HashMap();
		map1.put("cid",1);
		map1.put("count",3);
		map1.put("price",100);
		list.add(map1);
		
		Map map2 = new HashMap();
		map2.put("cid",2);
		map2.put("count",2);
		map2.put("price",200);
		list.add(map2);
		
		Map map3 = new HashMap();
		map3.put("cid",3);
		map3.put("count",1);
		map3.put("price",300);
		list.add(map3);
	}
	
	

	
	public static void main(String[] args) throws IOException {
		
		for(int i=0;i<list.size();i++){
			final Map map = list.get(i);//获取订单信息
			jedis.rpush(ORDER_KEY.getBytes(),ObjectUtils.objectToByte(map));//将订单信息存入Redis数据库（将对象转换成字节数组）
		}
	}
}
