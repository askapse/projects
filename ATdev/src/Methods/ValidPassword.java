package Methods;

public class ValidPassword {
	boolean validatePass(String str) {
		if(str.length()>8) {
			if(str.matches("[A-Za-z0-9]+")) {
				int d =0;
				for(int i = 0;i<str.length();i++) if((str.charAt(i)+"").matches("[0-9]")) d++;
				if(d>=2) return true;
				else return false;
			}else return false;
		}else return false;
	}
	public static void main(String[] args) {
		System.out.println(new ValidPassword().validatePass("AbhiKaps5e8d"));
	}
}
