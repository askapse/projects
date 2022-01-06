package JavaArrays;

public class RemoveDuplicateAndReturnLen {
	public static void main(String[] args) {
		int [] arr = {20, 20, 30, 40, 50, 50, 50};
		String str = "";
		
		for(int i =0; i<arr.length;i++) {
			if(!str.contains(arr[i]+"")) {
				str+=arr[i]+" ";
			}
		}
		
		String s[] = str.trim().split(" ");
		arr = new int[s.length];
		for(int i = 0 ; i<arr.length;i++) {
			arr[i]=Integer.parseInt(s[i]);
		}
		System.out.println(arr.length);
	}
}
