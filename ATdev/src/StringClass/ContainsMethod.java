package StringClass;

public class ContainsMethod {
	public static void main(String[] args) {
		String s1 = "PHP Exercises and Python Exercises";
		String s = "Python";
		
		System.out.println("The String "+s1+" contains "+(s1.contains(s)?s:""));
	}
}
