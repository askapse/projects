package Methods;

import java.util.Scanner;

public class SmallestAmongThree {
	public static float smallestThree(float a,float b,float c){
		return (a<b?a:b)<c?(a<b?a:b):c;
	}
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("Input the first number : ");
		float a = in.nextFloat();
		System.out.print("Input the second number : ");
		float b = in.nextFloat();
		System.out.print("Input the third number : ");
		float c = in.nextFloat();
		
		System.out.println("The smallest value is "+smallestThree(a, b, c));
	}
}
