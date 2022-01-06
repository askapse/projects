package JavaArrays;

public class RemovesDuplicateElement {
	public static void main(String[] args) {
		int [] arr = {2,5,20,23,25,17,23,23,5,5,5,20,98,2,20,90};
		String str="";
		
		for(int i=0;i<arr.length;i++) {
			if(!str.contains(arr[i]+"")) {
				str+=arr[i]+" ";
			}
		}
		int ar[] = new int[str.trim().split(" ").length];
		for(int i=0;i<str.trim().split(" ").length;i++) {
			ar[i]=Integer.parseInt(str.trim().split(" ")[i]);
		}
		
		for(int a :ar) {
			System.out.print(a+" ");
		}
	}
}
