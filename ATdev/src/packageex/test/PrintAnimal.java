package packageex.test;

import packageex.animal.Domestic;
import packageex.animal.Wild;

public class PrintAnimal {
	public static void main(String[] args) {
		Domestic d1 = new Domestic("CAT");
		Domestic d2 = new Domestic("DOG");
		
		Wild w1 = new Wild("TIGER");
		Wild w2 = new Wild("LION");
		
		System.out.println("Domestic animls :");
		d1.printName();
		d2.printName();
		System.out.println("Wild anils :");
		w1.printName();
		w2.printName();
	}
}
