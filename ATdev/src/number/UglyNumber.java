package number;

public class UglyNumber {
	static int divisible(int num,int d) {
		while(num%d==0) {
			num/=d;
		}
		return num;
	}
	public static void main(String[] args) {
		int num = 235;
		num = divisible(num,2);
		num =divisible(num, 3);
		num =divisible(num,5);
		
		if(num==1) {
			System.out.println("It is an ugly number");
		}else {
			System.out.println("It is not an ugly number");
		}		
	}
}
