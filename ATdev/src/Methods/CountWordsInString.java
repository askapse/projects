package Methods;

public class CountWordsInString {
	 int CountWords(String str) {
		return str.split(" ").length;
	}
	public static void main(String[] args) {
		String str = "I am very happy today";
		System.out.println("No of words in input string \'"+str+"\' is "+new CountWordsInString().CountWords(str));
	}
}
