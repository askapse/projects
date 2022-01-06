package JavaArrays;

public class SecondLargest {
	public static void main(String[] args) {
		int [] arr = {2,5,20,23,25,17,23,23,40,95,5,5,5,20,98,2,20,90,97};
		int max=arr[0],secmax=0;
		
		for(int i=0;i<arr.length;i++) {
			if(max<arr[i]) {
				secmax=max;
				max=arr[i];
			}
			if(secmax<arr[i] && arr[i]<max) {
				secmax=arr[i];
			}
		}
		System.out.println("Second largest number : "+secmax);
	}
}
