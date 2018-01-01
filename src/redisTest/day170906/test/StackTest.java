package redisTest.day170906.test;

public class StackTest {
	/**
	 * Exception in thread "main" java.lang.StackOverflowError
	 * ÄÚ´æÒç³ö
	 */
	public static void main(String[] args) {
		a();
	}
	
	public static void a(){
		b();
	}
	
	public static void b(){
		a();
	}
}
