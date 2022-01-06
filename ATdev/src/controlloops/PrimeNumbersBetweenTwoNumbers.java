package controlloops;

public class PrimeNumbersBetweenTwoNumbers {
	public static void main(String[] args) {
		for(int i =1;i<=100;i++) {
			int num = i;
			int t = num/2;
			boolean flag = false;
			while(t>1) {
				if(num%t == 0) {
					flag = true;
					break;
				}
				t--;
			}
			if(!flag) {
				System.out.println(num);
			}
		}
	}
}
