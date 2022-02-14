import dao.Database;
import entities.InClass;

public class Test {
	public static void main(String[] args) {
		InClass c = new InClass();
		c.setBatch("2020-22");
		c.setName("MCA");
		System.out.println(Database.cls.getClass(2));
		
	}
}
