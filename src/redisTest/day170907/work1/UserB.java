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
		//����Ƶ��A
		jedis.subscribe(new JedisPubSub() {
			@Override
			public void onMessage(String channel, String message) {
				System.out.println("�յ���Ϣ��"+message);
				super.onMessage(channel, message);
			}
		},CHANNEL_A);
	}
}
class MyThread2 extends Thread{
	public static final String CHANNEL_B = "chan_b";//UserB��Ƶ����
	static Jedis jedis = new Jedis();
	
	@Override
	public void run() {
		while(true){
			Scanner sc = new Scanner(System.in);
			System.out.println("�����뷢�͵���Ϣ��");
			String test = sc.nextLine();
			//����Ƶ��B
			jedis.publish(CHANNEL_B,test);
		}
		
	}
	
}