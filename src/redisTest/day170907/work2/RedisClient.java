package redisTest.day170907.work2;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class RedisClient {
	/**
	 * redis-cli -h localhost -p 6379
	 */
	public static Jedis jedis;
	
	public static void main(String[] args) {	
		
		String host = "localhost";//Ĭ�Ͽͻ���IP
		String port = "6379";//Ĭ�϶˿�
		
		for(int i=0;i<args.length;i++){
			//���ȡ��-h ��ȡ����һ��ֵ��Ϊ�ͻ���IP
			if(args[i].equals("-h")){
				host = args[i+1];
			}
			
			//���ȡ��-p��ȡ����һ��ֵ��Ϊ�˿�
			if(args[i].equals("-p")){
				port = args[i+1];
			}
		}
		
		jedis = new Jedis(host,Integer.parseInt(port));
		
		Scanner sc = new Scanner(System.in);
		String space = "";
		
		while(true){
			System.out.print(host+":"+port+space+">");
			String command = sc.nextLine();			
			command.replaceAll(" +"," ").trim();//�滻����ȷ�������ʽ
					
			/**
			 * �������˳�������
			 */
			//ping ��������Ƿ���  ����pang���
			if(command.startsWith("ping")){
				System.out.println(jedis.ping());
			}
			
			//echo �������д�ӡ����
			if(command.startsWith("echo ")){
				String value = command.split(" ")[1];
				System.out.println(value);
			}
			
			//quit �˳��ͻ���
			if(command.startsWith("quit")){
				System.exit(0);
			}
			
			//select ѡ�����ݿ�		
			if(command.startsWith("select ")){
				int index = Integer.parseInt(command.split(" ")[1]);
	
				if(index<=15){
					space = "["+index+"]";
					jedis.select(index);
					System.out.println("ok");
				}else{
					System.out.println("(error) ERR invalid DB index");
				}
								
			}
			
			//dbsize	���ص�ǰ���ݿ���key����Ŀ
			if(command.startsWith("dbsize")){
				long count = jedis.dbSize();
				System.out.println(count);
			}
			
			//flushdb	ɾ����ǰ���ݿ��е�����key
			if(command.startsWith("flushdb")){
				jedis.flushDB();
				System.out.println("ok");
			}
			
			//flushall	ɾ���������ݿ��е�����key
			if(command.startsWith("flushall")){
				jedis.flushAll();
				System.out.println("ok");
			}
			
			/**
			 * string����
			 */
			//set �� ֵ
			if(command.startsWith("set ")){
				String key = command.split(" ")[1];
				String value = command.split(" ")[2];
				jedis.set(key, value);
				System.out.println("ok");
			}
			
			//get ��
			if(command.startsWith("get ")){
				String key = command.split(" ")[1];
				String value = jedis.get(key);
				System.out.println(value);
			}
			
			//append	׷���ַ���
			if(command.startsWith("append")){
				String key = command.split(" ")[1];
				String value = command.split(" ")[2];
				if(command.split(" ").length>3){
					System.out.println("(error) ERR wrong number of arguments for 'append' command");
					continue;
				}
				jedis.append(key, value);
				System.out.println("(integer)"+jedis.get(key).length());				
			}
			
			//incr	������+1��
			if(command.startsWith("incr")){
				String key = command.split(" ")[1];
				String value = jedis.get(key);	
				System.out.println("(integer)"+jedis.incr(key));				
			}
				
			//decr	�Լ���-1��
			if(command.startsWith("decr")){
				String key = command.split(" ")[1];
				String value = jedis.get(key);	
				System.out.println("(integer)"+jedis.decr(key));				
			}
			
			//strlen	����key��ֵ�ĳ���
			if(command.startsWith("strlen")){
				String key = command.split(" ")[1];
				System.out.println("(integer)"+jedis.strlen(key));
			}
			
			/**
			 * hash ���� hset user:1 name zs
			 */
			//hset
			if(command.startsWith("hset")){
				String key = command.split(" ")[1];
				String field = command.split(" ")[2];
				String value = command.split(" ")[3];
				System.out.println("(integer)"+jedis.hset(key, field, value));
			}
			
			
			//hexists	�ж�key���Ƿ����filed
			if(command.startsWith("hexists")){
				String key = command.split(" ")[1];
				String field = command.split(" ")[2];
				boolean bool = jedis.hexists(key, field);
				if(bool==true){
					System.out.println("(integer)1");
				}else{
					System.out.println("(integer)0");
				}
			}
			
			//hlen	��ȡkey��filed������
			if(command.startsWith("hlen")){
				String key = command.split(" ")[1];
				System.out.println("(integer)"+jedis.hlen(key));				
			}
			
			//hdel ɾ��key�е�filed�ֶ�
			if(command.startsWith("hdel")){
				String key = command.split(" ")[1];
				String[] fields = command.replace("hdel "+key, "").split(" ");				
				System.out.println("(integer)"+jedis.hdel(key, fields));
			}
			
			//hgetall	��ȡkey�����е�field��value
			if(command.startsWith("hgetall")){
				String key = command.split(" ")[1];
				System.out.println(jedis.hgetAll(key));
			}
			
			//hmget	ͬʱ��ȡ���field��ֵ
			if(command.startsWith("hmget")){
				String key = command.split(" ")[1];
				String[] fields = command.replace("hmget "+key+" ", "").split(" ");
				System.out.println(jedis.hmget(key, fields));
			}
			
			//hmset	ͬʱ���ö��field��value  hmset user:1 a 1 b 2 c 3
			if(command.startsWith("hmset")){
				String key = command.split(" ")[1];
				Map<String,String> hash = new HashMap<String, String>();
				String[] str = command.replace("hmset "+key+" ","").split(" ");
				for(int i=0;i<str.length;i+=2){
					hash.put(str[i], str[i+1]);
				}
				System.out.println(jedis.hmset(key, hash));
			}
			
			/**
			 * list����
			 */
			//lpush	��listͷ�����ֵ
			if(command.startsWith("lpush")){
				String key = command.split(" ")[1];
				String str = command.split(" ")[2];				
				System.out.println("(integer)"+jedis.lpush(key, str));				
			}
			
			//rpush	��listβ�����ֵ
			if(command.startsWith("rpush")){
				String key = command.split(" ")[1];
				String str = command.split(" ")[2];
				System.out.println("(integer)"+jedis.rpush(key, str));				
			}
			
			//lrange	��ȡָ��λ�õ�����
			if(command.startsWith("lrange")){
				String key = command.split(" ")[1];
				long start = Long.valueOf(command.split(" ")[2]);
				long end = Long.valueOf(command.split(" ")[3]);
				System.out.println(jedis.lrange(key,start,end));			
			}
			
			//lpop	��ͷ������key��ֵ��ɾ����
			if(command.startsWith("lpop")){
				String key = command.split(" ")[1];
				System.out.println(jedis.lpop(key));			
			}
			
			//rpop	��β������key��ֵ��ɾ����
			if(command.startsWith("rpop")){
				String key = command.split(" ")[1];
				System.out.println(jedis.rpop(key));			
			}
			
			/**
			 * set ����
			 */
			
			//sadd	���ֵ
			if(command.startsWith("sadd")){
				String key = command.split(" ")[1];
				String[] str = command.replace("sadd "+key+" ","").split(" ");
				System.out.println(jedis.sadd(key,str));			
			}
			
			//smembers	��������
			if(command.startsWith("smembers")){
				String key = command.split(" ")[1];
				System.out.println(jedis.smembers(key));			
			}
			
			//scard	��ȡkey�ĳ�Ա����
			if(command.startsWith("scard")){
				String key = command.split(" ")[1];
				System.out.println(jedis.scard(key));			
			}
			
			//srem	ɾ��ָ����Ա
			if(command.startsWith("srem")){
				String key = command.split(" ")[1];
				String[] str = command.replace("srem "+key+" ","").split(" ");
				System.out.println(jedis.srem(key,str));			
			}
			
			//spop	�������һ��ֵ��ɾ����
			if(command.startsWith("spop")){
				String key = command.split(" ")[1];
				System.out.println(jedis.spop(key));			
			}
			
			/**
			 * sorted set����
			 */
			
			//zadd	��ӳ�Ա
			if(command.startsWith("zadd")){
				String key = command.split(" ")[1];
				double score = Double.valueOf(command.split(" ")[2]);
				String member = command.split(" ")[3];
				System.out.println(jedis.zadd(key,score,member));			
			}
			
			//zcard	��ȡ��Ա����
			if(command.startsWith("zcard")){
				String key = command.split(" ")[1];			
				System.out.println(jedis.zcard(key));			
			}
			
			//zcount	��ȡָ������֮��ĳ�Ա����
			if(command.startsWith("zcount")){
				String key = command.split(" ")[1];
				double min = Double.valueOf(command.split(" ")[2]);
			    double max = Double.valueOf(command.split(" ")[3]);
				System.out.println(jedis.zcount(key,min,max));			
			}
			
			//zrange	����ָ���±�֮��ĳ�Ա[������](������С�������У�
			if(command.startsWith("zrange")){
				String key = command.split(" ")[1];
				long start = Long.valueOf(command.split(" ")[2]);
			    long end = Long.valueOf(command.split(" ")[3]);
				System.out.println(jedis.zrange(key, start, end));			
			}
			
			//zrevrange	����ָ����Ա[������]�������Ӵ�С���У�
			if(command.startsWith("zrevrange")){
				String key = command.split(" ")[1];
				long start = Long.valueOf(command.split(" ")[2]);
			    long end = Long.valueOf(command.split(" ")[3]);
				System.out.println(jedis.zrevrange(key, start, end));			
			}
			
			/**
			 * save
			 */
			if(command.startsWith("save")){
				jedis.save();
			}
			
			/**
			 * subscribe ����Ƶ��
			 */
			if(command.startsWith("subscribe")){
				String channels = command.split(" ")[1];
				
				jedis.subscribe(new JedisPubSub() {
					@Override
					public void onMessage(String channel, String message) {
						System.out.println(message);
						super.onMessage(channel, message);
					}
				}, channels);
			}
			
			/**
			 * publish ����Ƶ��
			 */
			if(command.startsWith("publish")){
				String channel = command.split(" ")[1];
				String message = command.split(" ")[2];
				System.out.println(jedis.publish(channel, message));
			}
		}
	}

}
