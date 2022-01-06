package caracterclass;

import java.util.Scanner;

public class InputCHaracterToUppercase {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		while (true) {
			System.out.print("Input : ");
			char ch = (char)in.next().charAt(0);
			System.out.println("Output : "+Character.toUpperCase(ch));
		}
	}
}
