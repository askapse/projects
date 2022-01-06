//1+1/2+1/3+1/4.....+1/9
package controlloops;

public class SumOfTheSeries {
	public static void main(String[] args) {
		float sum =0;
		for(int i=1;i<10;i++) {
			sum +=((float)1/i);			
		}
		System.out.println(sum);
	}
}
