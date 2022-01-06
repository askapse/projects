package JavaArrays;

public class ReverseArray {
	public static void main(String[] args) {
		int [] arr = {2,5,20,23,25,17,98,90};	
		for(int i = 0;i<arr.length/2;i++) {
			int tmp = arr[i];
			arr[i] = arr[arr.length-i-1];
			arr[arr.length-i-1]=tmp;
		}
		for(int  i:arr) {
			System.out.print(i+" ");
		}
	}
}
