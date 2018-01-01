package redisTest.day170905.work;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtils {
	//����Properties�������洢�����ļ���Ϣ
	static Properties p = new Properties();
	static{
		InputStream is = DbUtils.class.getResourceAsStream("scott.properties");
		try {
			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException{
		String url = p.getProperty("url");
		String driverClass = p.getProperty("driverClass");
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String username = p.getProperty("username");
		String password = p.getProperty("password");
		Connection con = DriverManager.getConnection(url, username, password);
		return con;
	}
}
