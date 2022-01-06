package controlloops;

public class primenumber {
	public static void main(String[] args) {
		int num=29;
		int tmp=num/2;
		boolean flag =false;
		while(tmp>=2) {		
			if(num%tmp == 0) {
				flag=true;
				break;
			}
			tmp--;			
		}		
		if(flag) System.out.println(num +" is not a prime number");
		else System.out.println(num +" is a prime number");
	}
}
