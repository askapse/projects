package packageex.test;

import packageex.school.ClassRoom;

public class PrintDeskBench {
	public static void main(String[] args) {
		ClassRoom cl5 = new ClassRoom(23,32);
		ClassRoom cl6 = new ClassRoom(14,28);
		ClassRoom cl7 = new ClassRoom(13,21);
		
		System.out.println("\nClass 5 ---");
		cl5.printDeskBench();
		System.out.println("\nClass 6 ---");
		cl6.printDeskBench();
		System.out.println("\nClass 7 ---");
		cl7.printDeskBench();
	}
}
