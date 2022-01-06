package Methods;

public class MiddleCharacter {
	static void MiddleCharacterPrint(String str){
		if(str.length()%2 == 0) {
			System.out.println("No Middle character");
		}else {
			System.out.println(str.charAt(str.length()/2));
		}
	}
	public static void main(String[] args) {
		MiddleCharacterPrint("Rakhi");
	}
}
