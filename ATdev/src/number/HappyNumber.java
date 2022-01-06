package number;

public class HappyNumber {
	public static void main(String[] args) {
		int i = 0;
		int num = 1;
		while (i < 10) {
			int tmp = num;
			for (int j = 0; j < 100; j++) {
				int sum = 0;
				while (tmp > 0) {
					int d = tmp % 10;
					tmp /= 10;
					sum += (int)Math.pow(d,2);
				}
				if (sum == 1) {
					System.out.println(num);
					i++;
					break;
				}
				tmp = sum;
			}
			num++;
		}
	}
}
