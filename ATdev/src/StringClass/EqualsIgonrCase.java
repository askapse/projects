package StringClass;

public class EqualsIgonrCase {
	public static void main(String[] args) {
		System.out.println("\"Stephen Edwin King\" equals \"Walter Winchell\"?"+("Stephen Edwin King".equalsIgnoreCase("Walter Winchell")));
		System.out.println("\"Stephen Edwin King\" equals \"stephen edwin king\"?"+("Stephen Edwin King".equalsIgnoreCase("stephen edwin king")));
	}
}
