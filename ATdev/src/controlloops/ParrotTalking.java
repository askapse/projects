package controlloops;

public class ParrotTalking {
	static boolean parrotTrouble(boolean talk,int hour){
		if(talk && (hour<7 || hour>20))
			return true;	
		return false;
	}
	public static void main(String[] args) {
		System.out.println(parrotTrouble(true, 6));
		System.out.println(parrotTrouble(true, 7));
		System.out.println(parrotTrouble(false, 6));
	
	}
}
