package JavaArrays;

public class DuplicateValues {
	public static void main(String[] args) {
		int [] arr = {2,5,20,23,25,17,23,23,5,5,5,20,98,2,20,90};
		String str = "";
		for(int i = 0;i<arr.length-1;i++) {
			for(int j=i+1;j<arr.length;j++) {
				if(arr[i]==arr[j]) {
					if(!str.contains(arr[i]+"")) {
						System.out.print(arr[i]+" ");
						str +=arr[i]+" ";
					}					
				}
			}
		}
	}
}
