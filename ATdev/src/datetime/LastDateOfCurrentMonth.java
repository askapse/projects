package datetime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class LastDateOfCurrentMonth {
	public static void main(String[] args) {
		GregorianCalendar g = new GregorianCalendar();
		Date d = g.getTime();
		d.setDate(g.getActualMaximum(5)); //g,getActualMaximum() method gives the max value can in the provided field		
		System.out.println(new SimpleDateFormat("EEEE").format(d));			
	}
}
