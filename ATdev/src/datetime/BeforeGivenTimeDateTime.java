package datetime;

import java.util.Date;
import java.util.GregorianCalendar;

public class BeforeGivenTimeDateTime {
	public static void main(String[] args) {
		int h = 2;
		int m = 26;
		GregorianCalendar g = new GregorianCalendar();
		Date d = g.getTime();
		d.setMinutes(d.getMinutes()-((h*60)-m));
		System.out.println("Date and Time before given time : "+d);
	}
}
