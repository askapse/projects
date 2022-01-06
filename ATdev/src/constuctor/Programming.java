package constuctor;

public class Programming {
	Programming(){
		System.out.println("I Love Programming Languages");
	}
	
	Programming(String n){
		System.out.println("I Love "+n);
	}
	
	public static void main(String[] args) {
		new Programming();
		new Programming("Java");
		
	}
}
