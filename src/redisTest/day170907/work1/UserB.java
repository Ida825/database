package redisTest.day170907.work1;

import java.util.Scanner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class UserB {
	public static final String CHANNEL_A = "chan_a";
	static Jedis jedis = new Jedis();
	
	public static void main(String[] args) {
		MyThread2 t = new MyThread2();
		t.setDaemon(true);
		t.start();
		//订阅频道A
		jedis.subscribe(new JedisPubSub() {
			@Override
			public void onMessage(String channel, String message) {
				System.out.println("收到消息："+message);
				super.onMessage(channel, message);
			}
		},CHANNEL_A);
	}
}
class MyThread2 extends Thread{
	public static final String CHANNEL_B = "chan_b";//UserB的频道名
	static Jedis jedis = new Jedis();
	
	@Override
	public void run() {
		while(true){
			Scanner sc = new Scanner(System.in);
			System.out.println("请输入发送的消息：");
			String test = sc.nextLine();
			//发布频道B
			jedis.publish(CHANNEL_B,test);
		}
		
	}
	
}