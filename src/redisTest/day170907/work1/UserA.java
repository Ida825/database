package redisTest.day170907.work1;

import java.util.Scanner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class UserA {
	public static final String CHANNEL_B = "chan_b";//����Ƶ��B
	static Jedis jedis = new Jedis();
	
	public static void main(String[] args) {
		MyThread1 mt = new MyThread1();
		mt.setDaemon(true);
		mt.start();
		//����Ƶ��B
		jedis.subscribe(new JedisPubSub() {
			@Override
			public void onMessage(String channel, String message) {
				System.out.println("�յ���Ϣ��"+message);
				super.onMessage(channel, message);
				
			}
		}, CHANNEL_B);		
	}
	
}

class MyThread1 extends Thread{
	public static final String CHANNEL_A = "chan_a";//��UserA����һ��Ƶ����
	static Jedis jedis = new Jedis();
	
	@Override
	public void run() {
		while(true){
			Scanner sc = new Scanner(System.in);
			System.out.println("�����뷢�͵���Ϣ");
			String test = sc.nextLine();
			//����Ƶ��A
			jedis.publish(CHANNEL_A,test);	
		}		
	}
}
