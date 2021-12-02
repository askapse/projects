package hotel.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity(name = "hotelauth")
public class HotelAuth {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;

	String email;
	String pass;

	@OneToOne
	@JoinColumn(name = "id")
	@MapsId
	HotelEntity hotel;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public HotelEntity getHotel() {
		return hotel;
	}

	public void setHotel(HotelEntity hotel) {
		this.hotel = hotel;
	}

	@Override
	public String toString() {
		return "HotelAuth [id=" + id + ", email=" + email + ", pass=" + pass + ", hotel=" + hotel + "]";
	}

}
