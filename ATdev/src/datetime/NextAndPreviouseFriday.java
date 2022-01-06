package datetime;

import java.util.Date;
import java.util.GregorianCalendar;

public class NextAndPreviouseFriday {
	public static void main(String[] args) {
		GregorianCalendar g = new GregorianCalendar(2021, 11, 18);
		Date d = g.getTime();
		if(d.getDay()>5) {
			d.setDate(d.getDate()-(d.getDay()-5));
			System.out.println("Previouse Friday date : "+d);
			d = g.getTime();
			d.setDate(d.getDate()+(7-(d.getDay()-5)));
			System.out.println("Next Friday date : "+d);
		}else if(d.getDay()<5) {
			d.setDate(d.getDate()-d.getDay()-2);
			System.out.println("Previouse Friday date : "+d);
			d= g.getTime();
			d.setDate(d.getDate()+5-d.getDay());
			System.out.println("Next Friday date : "+d);
		}else if(d.getDay() == 5) {
			d.setDate(d.getDate()-7);
			System.out.println("Previouse Friday date : "+d);
			d = g.getTime();
			d.setDate(d.getDate()+7);
			System.out.println("Next Friday date : "+d);
		}
	}
}
