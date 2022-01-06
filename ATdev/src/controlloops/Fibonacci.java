package controlloops;

import java.util.Scanner;

public class Fibonacci {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter a number :");
		int n = in.nextInt();
		
		int a=0,j=1,sum =0;
		for(int i=0;i<n;i++) {			
			sum = sum+a;
			a=j;
			j=sum;
			System.out.print(sum+" ");
		}
	}
}
