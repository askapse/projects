package number;

public class CubeNumber {
	public static void main(String[] args) {
			int num = 64;
			int n = (int)Math.pow(num,(float)1/3);
			if(Math.pow(n,3) == num) {
				System.out.println("Number "+num+" is a cube");
			}else {
				System.out.println("Number "+num+" is not a cube");
			}
	}
}
