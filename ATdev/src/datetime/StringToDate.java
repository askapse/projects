package datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDate {
	public static void main(String[] args) throws ParseException {
		Date d = (new SimpleDateFormat("dd/mm/yyyy hh:mm:ss")).parse("02/02/2004 02:30:30");
		System.out.println(d);
//		new SimpleDateFormat().set2DigitYearStart(d);
		System.out.println(d);
	}
}
