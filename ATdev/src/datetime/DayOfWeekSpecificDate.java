package datetime;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class DayOfWeekSpecificDate {
	public static void main(String[] args) {
		GregorianCalendar g = new GregorianCalendar(2020,11,31);
		System.out.println(new SimpleDateFormat("EEEE").format(g.getTime()));
	}
}
