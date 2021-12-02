package databaselogic;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import hotel.entities.HotelAddress;
import hotel.entities.HotelAuth;
import hotel.entities.HotelEntity;
import entities.TmpRegDataStorage;

public class HotelUser {
	public static boolean addUser(TmpRegDataStorage regdata) {
		
			HotelEntity hotel=new HotelEntity();
			HotelAuth hotelauth=new HotelAuth();
			HotelAddress hoteladd=new HotelAddress();
			
			//hotel table info seting
			hotel.setMobile(regdata.getMobile());
			hotel.setFname(regdata.getFname());
			hotel.setLname(regdata.getLname());
			hotel.setDob(regdata.getDob());
		
			//hotel address 
			hoteladd.setDist(regdata.getDist());
			hoteladd.setSubdist(regdata.getSubdist());
			hoteladd.setCity(regdata.getCity());
			hoteladd.setArea(regdata.getArea());
			hoteladd.setHotelname(regdata.getHome());
			hoteladd.setZipcode(regdata.getZipcode());
			
			
			//hotel suthantication info
			hotelauth.setEmail(regdata.getEmail());
			hotelauth.setPass(regdata.getPass());						
			
			//add embeded address boject to the main hotel object
			hotel.setAddress(hoteladd);
			
			
			//add relational table object hotelauth
			hotel.setAuth(hotelauth);
			
			hotelauth.setHotel(hotel);
			
			try {
					Configuration con=new Configuration().configure("Hotel.cfg.xml")
							.addAnnotatedClass(HotelEntity.class).addAnnotatedClass(HotelAuth.class);													
					
						SessionFactory sf=con.buildSessionFactory();
							Session session=sf.openSession();
								session.beginTransaction();
							
									session.save(hotel);
									session.save(hotelauth);

								session.getTransaction().commit();
							session.close();
						sf.close();
					return true;				
			}catch(Exception e) {	
				System.out.println(e);
				return false;
				
			}	
	}
	
	
	public static boolean updateUser(TmpRegDataStorage regdata,long userid) {
		
		HotelEntity hotel=new HotelEntity();
		HotelAddress hoteladd=new HotelAddress();
		
		//set userid to hotel entity
		hotel.setUserid(userid);
		
		//hotel table info seting
		hotel.setMobile(regdata.getMobile());
		hotel.setFname(regdata.getFname());
		hotel.setLname(regdata.getLname());
		hotel.setDob(regdata.getDob());
	
		//hotel address 
		hoteladd.setDist(regdata.getDist());
		hoteladd.setSubdist(regdata.getSubdist());
		hoteladd.setCity(regdata.getCity());
		hoteladd.setArea(regdata.getArea());
		hoteladd.setHotelname(regdata.getHome());
		hoteladd.setZipcode(regdata.getZipcode());
	
		//add embeded address boject to the main hotel object
		hotel.setAddress(hoteladd);
		
		try {
				Configuration con=new Configuration().configure("Hotel.cfg.xml")
						.addAnnotatedClass(HotelEntity.class).addAnnotatedClass(HotelAuth.class);												
				
					SessionFactory sf=con.buildSessionFactory();
						Session session=sf.openSession();
							session.beginTransaction();
						
								session.update(hotel);

							session.getTransaction().commit();
						session.close();
					sf.close();
				return true;				
		}catch(Exception e) {
			System.out.println(e);
			return false;
			
		}	
	}		
	
}
