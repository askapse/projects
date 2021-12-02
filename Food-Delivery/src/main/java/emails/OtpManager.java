package emails;

import java.util.Random;

public class OtpManager {
	public static int getOtp(){
		Random random=new Random();
			int tmp=(int)(Math.random()*(9999-1001)+1000);			
			return tmp;			
	}
}
