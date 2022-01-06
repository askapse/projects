package datatypes;

public class ChangeInitialValue {
	boolean v = true;
	public static void main(String[] args) {
		ChangeInitialValue o = new ChangeInitialValue();
		o.v = false;
		System.out.println(o.v);
	}
}
