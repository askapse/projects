package JavaArrays;

public class SortArray {
	public static void main(String[] args) {
		int[] arr = { 90, 3, 5, 8, 7, 2, 1, 8, 29, 23, 12, 1, 12, 3, 4, 3, 3, 4, 5, 6, 77, 2, 2, 2, 7, 8, 7, 6 };
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length - 1; j++) {
				if (arr[j] > arr[j + 1]) {
					int t = arr[j + 1];
					arr[j + 1] = arr[j];
					arr[j] = t;
				}
			}
		}
		for (int i : arr) {
			System.out.print(i + " ");
		}

		String[] str = { "sde", "bsde", "abhi", "abAhie", "Asdf", "asdfg" };
		for (int i = 0; i < str.length; i++) {
			for (int j = 0; j < str.length-1; j++) {
				if (str[j].compareToIgnoreCase(str[j+1]) > 0) {
					String tmp = str[j];
					str[j] = str[j+1];
					str[j+1] = tmp;
				}
			}
		}
		System.out.println();
		for (String ss : str) {
			System.out.print(ss + " ");
		}
	}
}