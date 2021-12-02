package hotel.entities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable
public class HotelAddress {
	String dist;
	String subdist;
	String city;
	String area;
	String zipcode;
	String hotelname;

	public String getDist() {
		return dist;
	}

	public void setDist(String dist) {
		this.dist = dist;
	}

	public String getSubdist() {
		return subdist;
	}

	public void setSubdist(String subdist) {
		this.subdist = subdist;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getHotelname() {
		return hotelname;
	}

	public void setHotelname(String hotelname) {
		this.hotelname = hotelname;
	}

	@Override
	public String toString() {
		return "HotelAddress [dist=" + dist + ", subdist=" + subdist + ", city=" + city + ", area=" + area
				+ ", zipcode=" + zipcode + ", hotelname=" + hotelname + "]";
	}

}
