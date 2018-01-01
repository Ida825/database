package redisTest.day170906.work;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectUtils {
	/**
	 * ������ת��Ϊ�ֽ�����
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public static byte[] objectToByte(Object obj) throws IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(obj);
		oos.close();
		return bos.toByteArray();		
	}
	
	/**
	 * 
	 * ���ֽ�����ת��Ϊ����
	 * @param bs
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static Object byteToObject(byte[] bs) throws ClassNotFoundException, IOException{
		ByteArrayInputStream bis = new ByteArrayInputStream(bs);
		ObjectInputStream ois = new ObjectInputStream(bis);
		return ois.readObject();
	}
}
