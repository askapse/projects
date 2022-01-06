package classandobject;

public class Student {

	String name;
	String courseEnrolled;
	
	Student(String name,String courseEnrolled){
		this.name = name;
		this.courseEnrolled = courseEnrolled;
	}
	
	String getCourseEnrolled() {
		return courseEnrolled;
	}
	
	
	public static void main(String[] args) {
		Student s1 = new Student("Ram", "Java");
		Student s2 = new Student("Vicky", "HTML");
		Student s3 = new Student("Reena", "Spring");
			
		System.out.println(s1.getCourseEnrolled());
		System.out.println(s2.getCourseEnrolled());
		System.out.println(s3.getCourseEnrolled());
	}
}
