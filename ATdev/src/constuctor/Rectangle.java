package constuctor;

public class Rectangle {
	float length;
	float breath;
	
	Rectangle(){
		this.length = 0;
		this.breath = 0;		
	}
	
	Rectangle(float length,float breath){
		this.length = length;
		this.breath = breath;
	}
	
	Rectangle(float num){
		this.length = num;
		this.breath = num;
	}
	
	float area() {
		return this.length*this.breath;
	}
	
	public static void main(String[] args) {
		Rectangle r1 = new Rectangle();
		Rectangle r2 = new Rectangle(20);
		Rectangle r3 = new Rectangle(20,21);
		
		System.out.println(r1.area());
		System.out.println(r2.area());
		System.out.println(r3.area());
	}
}
