package datetime;

import java.util.GregorianCalendar;

public class LeapYear {
	public static void main(String[] args) {
		int year = 2020;
		if (new GregorianCalendar().isLeapYear(year))
			System.out.println(year + " Is Leap Year");
		else
			System.out.println(" Is Not Leap Year");
	}
}
