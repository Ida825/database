package redisTest.day170905.work;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import redis.clients.jedis.Jedis;

/**
 * 4��ʹ��javaʵ��һ�����jdbc��redisʵ�ֵĻ���ϵͳ �������£�24��
   ���ݿ�� emp����Ա��ţ���Ա���ƣ�ְλ��нˮ�� �������ݿ�
   Java���ṩ ��ѯ����(ͨ����Ա��Ų�ѯ��ȡ����Ӧ�Ĺ�Ա��Ϣ ����
Emp�����Խ�ʵ���� �ֶκ����ݿ���ͬ��)
Public Emp query(String empNo){
   //��������д��� ֱ�Ӵӻ����л�ȡ����
   //��������в����� ʹ��jdbc��ѯ���ݿⲢ���뻺�� ������
}
 */

public class Query2 {
	
	public static void main(String[] args) {				
		String empno = "7369";
		
		query(empno);
		
	}

	static final Jedis jedis = new Jedis();
	private static Emp query(String empno) {
		
		//���Redis���о�ֱ�ӷ���
		if(jedis.exists(empno)){
			//return jedis.get(empno);
			
		}
		
		//�ȶ�ȡCSV�ļ�
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("C:/ProgramData/MySQL/MySQL Server 5.5/Data/test/emp.csv"), "utf-8"));
			String line = null;
			try {
				while((line=br.readLine())!=null){
					String[] str = line.split(",");
					String value = str[0].replace("\"","")+":"+
							str[1].replace("\"","")+
							str[2].replace("\"","")+
							str[3].replace("\"","");
					String key = str[0].replace("\"","");
					/*if(name.equals(key)){
						//��Ҫ�ҵ����ݴ���Redis
						jedis.set(key, value);
					}	*/
					//return value;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
