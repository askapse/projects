//Given an int array, return a new array with double the length where its last element is the same as the original array,
//		and all the other elements are 0.
//		The original array will be length 1 or more. Note: by default, a new int array contains all 0's.
package JavaArrays;

public class DoubleLen {
	static String makeLast(int [] arr){
		int t = arr[arr.length-1];
		String str = "makeLast([";
		for(int i=0;i<arr.length;i++) {
			if(i==arr.length-1) {
				str+=arr[i];
			}else {
				str+=arr[i]+" ,";
			}
		}
		str+="])->[";
		arr = new int[arr.length*2];
		for(int i = 0;i<arr.length;i++) {
			arr[i]=0;
		}
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
		System.out.println(makeLast(new int[]{4, 5, 6}));
		System.out.println(makeLast(new int[] {1,2}));
		System.out.println(makeLast(new int[] {3}));
	}
}
