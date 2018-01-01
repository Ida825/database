package redisTest.day170907.work1;

import java.util.Scanner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class UserA {
	public static final String CHANNEL_B = "chan_b";//订阅频道B
	static Jedis jedis = new Jedis();
	
	public static void main(String[] args) {
		MyThread1 mt = new MyThread1();
		mt.setDaemon(true);
		mt.start();
		//订阅频道B
		jedis.subscribe(new JedisPubSub() {
			@Override
			public void onMessage(String channel, String message) {
				System.out.println("收到消息："+message);
				super.onMessage(channel, message);
				
			}
		}, CHANNEL_B);		
	}
	
}

class MyThread1 extends Thread{
	public static final String CHANNEL_A = "chan_a";//给UserA定义一个频道名
	static Jedis jedis = new Jedis();
	
	@Override
	public void run() {
		while(true){
			Scanner sc = new Scanner(System.in);
			System.out.println("请输入发送的消息");
			String test = sc.nextLine();
			//发布频道A
			jedis.publish(CHANNEL_A,test);	
		}		
	}
}
