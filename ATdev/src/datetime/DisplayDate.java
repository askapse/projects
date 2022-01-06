package datetime;

import java.util.Date;

public class DisplayDate {
	public static void main(String[] args) {
		Date d = new Date();
		System.out.println("Year: "+(d.getYear()+1900)+"\n"
		+"Month: "+(d.getMonth()+1)
		+"\nDay: "+d.getDate()
		+"\nHour: "+d.getHours()
		+"\nMinute: "+d.getMinutes());				
	}
}
