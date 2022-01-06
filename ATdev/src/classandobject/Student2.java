package classandobject;

public class Student2 {
	String name;
	int roll;
	String fathername;
	String contactno;
	String address;
	
	Student2(){
		this.name = "Mr. Abhishek";
		this.roll = 123;
		this.fathername = "Mr. Sulekh";
		this.contactno = "+1-8745733445";
		this.address = "#321, South Street, No-3, Ontario";				
	}
	
	public static void main(String[] args) {
		Student2 s = new Student2();
	
		System.out.println("Student Name is : "+s.name);
		System.out.println("Roll Number is : "+s.roll);
		System.out.println("Father's Name is : "+s.fathername);
		System.out.println("Contact Number is : "+s.contactno);
		System.out.println("Student Address is : "+s.address);
	}
}
