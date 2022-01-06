package classandobject;

public class Accountant {
	String name;
	String emailid;
	String contactNo;
	
	Accountant(String name,String emial,String mob){
		this.name = name;
		this.emailid = emailid;
		this.contactNo = mob;
	}
	
	void viewAccountInfo() {
		System.out.println("Accountant "+this.name+" has mail id"+this.emailid+" and contact No "+this.contactNo);
	}
	
	public static void main(String[] args) {
		Accountant a1 = new Accountant("Ravi","ravi@gmail.com","9867785694");
		Accountant a2 = new Accountant("Rutuja","rutuja@gmail.com","9987565634");
			
		
		a1.viewAccountInfo();
		a2.viewAccountInfo();
	}
}
