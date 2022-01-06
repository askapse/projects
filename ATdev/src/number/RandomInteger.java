package number;

public class RandomInteger {
	public static void main(String[] args) {
		int min = 10;
		int max = 99;
		System.out.println((int)(Math.random()*(max-min)+min));
	}
}
