package classandobject;

public class Mobile {
	String owner;
	String brand;
	String color;
	int camera;
	
	Mobile(String owner,String brand,String color,int camera){
		this.owner = owner;
		this.brand = brand;
		this.color = color;
		this.camera = camera;
	}
	
	public static void main(String[] args) {
		Mobile m1 = new Mobile("Abhishek","iPhone","Gold",8);
		Mobile m2 = new Mobile("Rahul","Samsung", "White", 13);
		Mobile m3 = new Mobile("Ravi", "Nexus", "Black", 8);
		
		System.out.println(m1.owner + " own "+m1.brand+" "+m1.color+" smartphone having "+m1.camera+"MP camera");
		System.out.println(m2.owner + " own "+m2.brand+" "+m2.color+" smartphone having "+m2.camera+"MP camera");
		System.out.println(m3.owner + " own "+m3.brand+" "+m3.color+" smartphone having "+m3.camera+"MP camera");
	}
}
