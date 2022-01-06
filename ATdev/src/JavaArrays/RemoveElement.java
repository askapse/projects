package JavaArrays;

public class RemoveElement {
	public static void main(String[] args) {
		int [] arr = {2,5,20,23,25,17,98,90};
		int n = 20;
		for(int i = 0;i<arr.length;i++) {
			if(arr[i]==n) {
				for(int j=i;j<arr.length-1;j++) {
					arr[j]=arr[j+1];
				}
				arr[arr.length-1]=0;
			}
		}
		
		for(int i:arr) {
			System.out.print(i+" ");
		}
	
	}
}
