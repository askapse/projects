package JavaArrays;

public class SwapFirstAndLast {
	static String swapEnds(int [] arr) {
		if(arr.length<1) {
			System.out.println("error");
			System.exit(0);
		}
		String str = "swapEnds([";
		for(int i=0;i<arr.length;i++) {
			if(i==arr.length-1) {
				str+=arr[i];
			}else {
				str+=arr[i]+" ,";
			}
		}
		str+="])->[";
		int t = arr[0];
		arr[0]=arr[arr.length-1];
		arr[arr.length-1]=t;
		for(int i=0;i<arr.length;i++) {
			if(i==arr.length-1) {
				str+=arr[i];
			}else {
				str+=arr[i]+" ,";
			}
		}
		str+="]";
		
		return str;
	}
	public static void main(String[] args) {
		System.out.println(swapEnds(new int[] {1,2,3,4}));
		System.out.println(swapEnds(new int[] {1,2,3,}));
		System.out.println(swapEnds(new int[] {8,6,7,9,5}));
	}
}
