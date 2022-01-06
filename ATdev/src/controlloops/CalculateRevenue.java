package controlloops;

import java.util.Scanner;

public class CalculateRevenue {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		System.out.print("Enter Unir Price : ");
		int price = in.nextInt();
		
		System.out.println("\nEnter Quantity :");
		int quantity = in.nextInt();
		
		int discount = 0;
		if(quantity>=100) {
			discount=10;
		}
		if(quantity>120) {
			discount = 15;
		}
		
		System.out.println("The revenue from sale: "+((float)(quantity*price)-((quantity*price)*discount)/100)+"$");
		System.out.println("After discount: "+((float)((quantity*price)*discount)/100)+"$("+(float)discount+"%)");
	}
}
