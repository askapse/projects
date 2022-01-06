package JavaArrays;

public class NumberOfEvenOdd {
	public static void main(String[] args) {
		int [] arr = {2,5,20,23,25,17,23,23,40,95,5,5,5,20,98,2,20,90,97};
		int even=0;
		int odd=0;
		for(int i:arr) {
			if(i%2==0) even++;
			else odd++;
		}
		
		System.out.println("Even number in array :"+even+"\nOdd numbers in array :"+odd);
	}
}
