package controlloops;

//Given two int values, return their sum. Unless the two values are the same, then return double their sum.
//Sample input and expected output.
//sumDouble(1, 2) → 3
//sumDouble(3, 2) → 5
//sumDouble(2, 2) → 8
public class SumOfGivenValue {
	static int sumDouble(int a,int b){
		if(a==b) {
			return (a+b)*2;
		}else {
			return a+b;
		}		
	}
	public static void main(String[] args) {
		System.out.println(sumDouble(1, 2));
		System.out.println(sumDouble(3, 2));
		System.out.println(sumDouble(2, 2));
		
	}
}
