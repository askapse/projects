package JavaArrays;

public class ContainElement {
	public static void main(String[] args) {
		int [] arr = {2,5,20,23,25,17,98,90};
		int n = 90;
		boolean flag=false;
		for(int i :arr) {
			if(i==n) {
				flag = true;
				break;
			}
		}
		System.out.println(flag);
	}
}