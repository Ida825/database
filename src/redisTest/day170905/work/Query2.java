package redisTest.day170905.work;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import redis.clients.jedis.Jedis;

/**
 * 4、使用java实现一个结合jdbc和redis实现的缓存系统 需求如下（24）
   数据库表 emp表（雇员编号，雇员名称，职位，薪水） 不限数据库
   Java中提供 查询方法(通过雇员编号查询获取到对应的雇员信息 返回
Emp对象（自建实体类 字段和数据库相同）)
Public Emp query(String empNo){
   //如果缓存中存在 直接从缓存中获取返回
   //如果缓存中不存在 使用jdbc查询数据库并放入缓存 并返回
}
 */

public class Query2 {
	
	public static void main(String[] args) {				
		String empno = "7369";
		
		query(empno);
		
	}

	static final Jedis jedis = new Jedis();
	private static Emp query(String empno) {
		
		//如果Redis中有就直接返回
		if(jedis.exists(empno)){
			//return jedis.get(empno);
			
		}
		
		//先读取CSV文件
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
						//将要找的数据存入Redis
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
