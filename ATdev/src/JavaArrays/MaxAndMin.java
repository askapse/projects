package JavaArrays;

public class MaxAndMin {
	public static void main(String[] args) {
		int [] arr = {2,5,20,23,25,17,98,90};
		int min=arr[0],max=arr[0];
		for(int i=0;i<arr.length;i++) {
			if(max<arr[i]) {
				max=arr[i];
			}
			if(min>arr[i]) {
				min=arr[i];
			}
		}
		System.out.println("Min :"+min+" Max :"+max);
	}
}
