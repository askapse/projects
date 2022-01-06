package datetime;

import java.util.Date;
import java.util.GregorianCalendar;

public class TenDayAfterBeforeDate {
	public static void main(String[] args) {
		GregorianCalendar g = new GregorianCalendar();
		Date d = g.getTime();
		d.setDate(d.getDate()-10);
		System.out.println("Before 10 days date : "+d);
		d = g.getTime();
		d.setDate(d.getDate()+10);
		System.out.println("After 10 days date : "+d);
	}
}
