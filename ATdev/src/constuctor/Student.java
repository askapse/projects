package constuctor;

public class Student {
	String name;
	
	Student(String name){
		this.name = name;
	}
	
	Student(){
		this.name = "Unknown";
	}
	
	public static void main(String[] args) {
		Student s1 = new Student();
		Student s2 = new Student("Ravi Shastri");
		
		System.out.println(s1.name);
		System.out.println(s2.name);
				
	}
	
}
