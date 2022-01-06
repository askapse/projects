package controlloops;

public class ArmstrongNumber {
	public static void main(String[] args) {
		for(int i = 1;i<=500;i++) {
			int sum = 0;
			int num = i;
			int tmp = num;
			
			while(tmp>0) {
				int d = tmp%10;
				sum = sum+(d*d*d);
				tmp/=10;
			}
			if(sum == num) {
				System.out.print(sum+" ");
			}
		}
	}
}
