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
		
		String host = "localhost";//默认客户端IP
		String port = "6379";//默认端口
		
		for(int i=0;i<args.length;i++){
			//如果取到-h 就取它后一个值作为客户端IP
			if(args[i].equals("-h")){
				host = args[i+1];
			}
			
			//如果取到-p就取它后一个值作为端口
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
			command.replaceAll(" +"," ").trim();//替换成正确的命令格式
					
			/**
			 * 服务器端常用命令
			 */
			//ping 检查链接是否存活  返回pang存活
			if(command.startsWith("ping")){
				System.out.println(jedis.ping());
			}
			
			//echo 在命令行打印内容
			if(command.startsWith("echo ")){
				String value = command.split(" ")[1];
				System.out.println(value);
			}
			
			//quit 退出客户端
			if(command.startsWith("quit")){
				System.exit(0);
			}
			
			//select 选择数据库		
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
			
			//dbsize	返回当前数据库中key的数目
			if(command.startsWith("dbsize")){
				long count = jedis.dbSize();
				System.out.println(count);
			}
			
			//flushdb	删除当前数据库中的所有key
			if(command.startsWith("flushdb")){
				jedis.flushDB();
				System.out.println("ok");
			}
			
			//flushall	删除所有数据库中的所有key
			if(command.startsWith("flushall")){
				jedis.flushAll();
				System.out.println("ok");
			}
			
			/**
			 * string类型
			 */
			//set 键 值
			if(command.startsWith("set ")){
				String key = command.split(" ")[1];
				String value = command.split(" ")[2];
				jedis.set(key, value);
				System.out.println("ok");
			}
			
			//get 键
			if(command.startsWith("get ")){
				String key = command.split(" ")[1];
				String value = jedis.get(key);
				System.out.println(value);
			}
			
			//append	追加字符串
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
			
			//incr	自增（+1）
			if(command.startsWith("incr")){
				String key = command.split(" ")[1];
				String value = jedis.get(key);	
				System.out.println("(integer)"+jedis.incr(key));				
			}
				
			//decr	自减（-1）
			if(command.startsWith("decr")){
				String key = command.split(" ")[1];
				String value = jedis.get(key);	
				System.out.println("(integer)"+jedis.decr(key));				
			}
			
			//strlen	返回key的值的长度
			if(command.startsWith("strlen")){
				String key = command.split(" ")[1];
				System.out.println("(integer)"+jedis.strlen(key));
			}
			
			/**
			 * hash 类型 hset user:1 name zs
			 */
			//hset
			if(command.startsWith("hset")){
				String key = command.split(" ")[1];
				String field = command.split(" ")[2];
				String value = command.split(" ")[3];
				System.out.println("(integer)"+jedis.hset(key, field, value));
			}
			
			
			//hexists	判断key中是否存在filed
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
			
			//hlen	获取key中filed的数量
			if(command.startsWith("hlen")){
				String key = command.split(" ")[1];
				System.out.println("(integer)"+jedis.hlen(key));				
			}
			
			//hdel 删除key中的filed字段
			if(command.startsWith("hdel")){
				String key = command.split(" ")[1];
				String[] fields = command.replace("hdel "+key, "").split(" ");				
				System.out.println("(integer)"+jedis.hdel(key, fields));
			}
			
			//hgetall	获取key中所有的field和value
			if(command.startsWith("hgetall")){
				String key = command.split(" ")[1];
				System.out.println(jedis.hgetAll(key));
			}
			
			//hmget	同时获取多个field的值
			if(command.startsWith("hmget")){
				String key = command.split(" ")[1];
				String[] fields = command.replace("hmget "+key+" ", "").split(" ");
				System.out.println(jedis.hmget(key, fields));
			}
			
			//hmset	同时设置多个field和value  hmset user:1 a 1 b 2 c 3
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
			 * list类型
			 */
			//lpush	在list头部添加值
			if(command.startsWith("lpush")){
				String key = command.split(" ")[1];
				String str = command.split(" ")[2];				
				System.out.println("(integer)"+jedis.lpush(key, str));				
			}
			
			//rpush	在list尾部添加值
			if(command.startsWith("rpush")){
				String key = command.split(" ")[1];
				String str = command.split(" ")[2];
				System.out.println("(integer)"+jedis.rpush(key, str));				
			}
			
			//lrange	获取指定位置的数据
			if(command.startsWith("lrange")){
				String key = command.split(" ")[1];
				long start = Long.valueOf(command.split(" ")[2]);
				long end = Long.valueOf(command.split(" ")[3]);
				System.out.println(jedis.lrange(key,start,end));			
			}
			
			//lpop	从头部弹出key的值（删除）
			if(command.startsWith("lpop")){
				String key = command.split(" ")[1];
				System.out.println(jedis.lpop(key));			
			}
			
			//rpop	从尾部弹出key的值（删除）
			if(command.startsWith("rpop")){
				String key = command.split(" ")[1];
				System.out.println(jedis.rpop(key));			
			}
			
			/**
			 * set 类型
			 */
			
			//sadd	添加值
			if(command.startsWith("sadd")){
				String key = command.split(" ")[1];
				String[] str = command.replace("sadd "+key+" ","").split(" ");
				System.out.println(jedis.sadd(key,str));			
			}
			
			//smembers	遍历集合
			if(command.startsWith("smembers")){
				String key = command.split(" ")[1];
				System.out.println(jedis.smembers(key));			
			}
			
			//scard	获取key的成员数量
			if(command.startsWith("scard")){
				String key = command.split(" ")[1];
				System.out.println(jedis.scard(key));			
			}
			
			//srem	删除指定成员
			if(command.startsWith("srem")){
				String key = command.split(" ")[1];
				String[] str = command.replace("srem "+key+" ","").split(" ");
				System.out.println(jedis.srem(key,str));			
			}
			
			//spop	随机弹出一个值（删除）
			if(command.startsWith("spop")){
				String key = command.split(" ")[1];
				System.out.println(jedis.spop(key));			
			}
			
			/**
			 * sorted set类型
			 */
			
			//zadd	添加成员
			if(command.startsWith("zadd")){
				String key = command.split(" ")[1];
				double score = Double.valueOf(command.split(" ")[2]);
				String member = command.split(" ")[3];
				System.out.println(jedis.zadd(key,score,member));			
			}
			
			//zcard	获取成员数量
			if(command.startsWith("zcard")){
				String key = command.split(" ")[1];			
				System.out.println(jedis.zcard(key));			
			}
			
			//zcount	获取指定分数之间的成员数量
			if(command.startsWith("zcount")){
				String key = command.split(" ")[1];
				double min = Double.valueOf(command.split(" ")[2]);
			    double max = Double.valueOf(command.split(" ")[3]);
				System.out.println(jedis.zcount(key,min,max));			
			}
			
			//zrange	遍历指定下标之间的成员[及分数](分数从小到大排列）
			if(command.startsWith("zrange")){
				String key = command.split(" ")[1];
				long start = Long.valueOf(command.split(" ")[2]);
			    long end = Long.valueOf(command.split(" ")[3]);
				System.out.println(jedis.zrange(key, start, end));			
			}
			
			//zrevrange	遍历指定成员[及分数]（分数从大到小排列）
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
			 * subscribe 订阅频道
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
			 * publish 发布频道
			 */
			if(command.startsWith("publish")){
				String channel = command.split(" ")[1];
				String message = command.split(" ")[2];
				System.out.println(jedis.publish(channel, message));
			}
		}
	}

}
