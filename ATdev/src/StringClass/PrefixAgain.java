package StringClass;

public class PrefixAgain {
	static boolean prefixAgain(String str,int n) {
		return str.substring(n).contains(str.substring(0, n));
	}
	public static void main(String[] args) {
		System.out.println(prefixAgain("abXYabc", 1));
		System.out.println(prefixAgain("abXYabc", 2));
		System.out.println(prefixAgain("abXYabc", 3));
	}
}
