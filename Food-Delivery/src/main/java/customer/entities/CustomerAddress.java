package customer.entities;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable
public class CustomerAddress {
	String dist;
	String subdist;
	String city;
	String area;
	String zipcode;
	String home;

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

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	@Override
	public String toString() {
		return "CustomerAddress [dist=" + dist + ", subdist=" + subdist + ", city=" + city + ", area=" + area
				+ ", zipcode=" + zipcode + ", home=" + home + "]";
	}

}
