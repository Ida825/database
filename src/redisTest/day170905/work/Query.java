package redisTest.day170905.work;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import redis.clients.jedis.Jedis;

/**
 * 步骤1  读取csv文件 
 * 步骤2  根据传入name参数读取对应行 显示
 * 步骤3  应用redis 快速读取 （第一次 读取文件  缓存redis ）
 * @author Administrator
 *
 */
public class Query {
	public static void main(String[] args) throws IOException{
		String name="ls";	
		String str = query(name);
		System.out.println(str);
	}
	
	static final Jedis jedis = new Jedis();
	
	public static String query(String name) throws IOException{
		//如果jedis中有name 就直接返回
		if(jedis.keys(name).size()>0){
			return jedis.get(name);
		}
		FileInputStream fi = new FileInputStream("C:/ProgramData/MySQL/MySQL Server 5.5/Data/test/userinfo.csv");
		BufferedReader br = new BufferedReader(new InputStreamReader(fi, "UTF-8"));
		String line = null;
		while((line=br.readLine())!=null){
			String[] str = line.split(",");
			String value = str[0].replace("\"","")+":"+
					str[1].replace("\"","")+
					str[2].replace("\"","")+
					str[3].replace("\"","")+
					str[4].replace("\"","");
			String key = str[1].replace("\"","");
			jedis.set(key, value);
			if(name.equals(key)){
				return jedis.get(key);
			}
		}
		fi.close();
		br.close();
		jedis.close();
		return null;
	}
}


