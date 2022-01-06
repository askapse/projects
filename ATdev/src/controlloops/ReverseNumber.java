package controlloops;

public class ReverseNumber {
	public static void main(String[] args) {
		int num = 12345;
		int sum = 0;
		while(num>0) {
			int d = num%10;
			sum =sum*10+d;
			num/=10;
		}
		System.out.println("Reverse number is :"+sum);
	}
}
