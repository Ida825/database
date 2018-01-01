package redisTest.day170906.work;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class Order {
	static Jedis jedis = new Jedis();
	static final String ORDER_KEY ="orderList"; 
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter("src/redisTest/day170906/work/order.csv"));
		boolean bool = true;
		while(bool){
			//��Redis�е����ݴ���߿�ʼȡ��
			byte[] bs = jedis.lpop(ORDER_KEY.getBytes());
			if(bs==null){
				bool = false;
				continue;
			}
			//���ֽ�����ת���ɶ���
			Map map = (Map)ObjectUtils.byteToObject(bs);		
		
			Set<String> keys = map.keySet();
			for(Iterator<String> iter = keys.iterator();iter.hasNext();){
				String key = iter.next();
				String v = map.get(key).toString();
				System.out.println(key+"--"+v);
			}
			String str = map.get("cid").toString()+","+map.get("count").toString()+","+map.get("price").toString()+"\r\n";					
			bw.write(str);	//д��CSV�ļ���		
			bw.flush();
		}
		bw.close();
	}
}
