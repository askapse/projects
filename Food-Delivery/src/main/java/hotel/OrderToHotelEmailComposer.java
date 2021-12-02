package hotel;

import java.text.Format;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import customer.entities.CustomerEntity;
import databaselogic.CustomerUser;
import databaselogic.HotelRecords;
import emails.Email;
import hotel.entities.Dish;

public class OrderToHotelEmailComposer {
	private List<Long> dishid;
	private List<Long> quantities;
	private long hid;
	private long customer;
	
	
	public OrderToHotelEmailComposer(List<Long> dids,List<Long> qnt,long hid,long cust) {
		this.dishid=dids;
		this.quantities=qnt;
		this.hid=hid;
		this.customer=cust;
	}
	
	
	public void sendOrderInfo() {
		List<Dish> dishlist=new ArrayList<>();
		
		Iterator itr=dishid.iterator();
			
		
//		get all dishes info 
		while(itr.hasNext()) {
			dishlist.add(HotelRecords.getDishNoImg((long)itr.next()));		
		}
		
		//get customer info 
		CustomerEntity cust=CustomerUser.getCustomer(customer);
		
		String emailstring="You have new Order from "+cust.getFname()+" "+cust.getLname()
				+ "\n Customer Mobile :"+cust.getMobile()
				+"\n Address :"+cust.getAddress().getHome()+" ,"+cust.getAddress().getArea()+" ,"
				+cust.getAddress().getCity()+" ,\n"+cust.getAddress().getSubdist()+" ,"+cust.getAddress().getDist()
				+" ,\nZipCode :"+cust.getAddress().getZipcode()
				+"\n\n\nOrderd Dished by the customer :"
				+ "\n\n";
		
		Iterator dobj=dishlist.iterator();
		Iterator q=quantities.iterator();
		
		while(dobj.hasNext()) {
			Dish dish=(Dish)dobj.next();
			emailstring +=String.format("%-30s", dish.getDishname())+" :"+String.format("%-8d",q.next())+" Price :"+dish.getPrice()+"\n";												
		}
				
		emailstring+="\n\n\nPlease login and verify the order for further process"
				+ "\n\n\nThank You...";
		
		
		System.out.println(emailstring);
		Email e_mail=new Email();
		e_mail.setSubject("New Order Arrived...");
		e_mail.setMsg(emailstring);
		e_mail.setTo(HotelRecords.getEmail(hid));
		
		e_mail.send();
	}			
}
