package controlloops;

public class PrimeNumberCheck {
	public static void main(String[] args) {
		int n=13;
		int t=n/2;
		boolean flag =false;
		while(t>1) {
			if(n%t == 0) {
				flag = true;
				break;
			}
			t--;
		}
		if(!flag) System.out.println("Prime Number");
		else System.out.println("Not Prime Number");
	}
}
