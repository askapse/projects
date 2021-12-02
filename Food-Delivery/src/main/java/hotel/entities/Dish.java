package hotel.entities;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "dish")
public class Dish {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	long dishid;
	long hotelid;
	String dishname;
	float price;
	@Column(columnDefinition = "longblob")
	byte [] image;
	char flag;
	
	
	public char getFlag() {
		return flag;
	}
	public void setFlag(char flag) {
		this.flag = flag;
	}
	public long getDishid() {
		return dishid;
	}
	public void setDishid(long dishid) {
		this.dishid = dishid;
	}
	public long getHotelid() {
		return hotelid;
	}
	public void setHotelid(long hotelid) {
		this.hotelid = hotelid;
	}
	public String getDishname() {
		return dishname;
	}
	public void setDishname(String dishname) {
		this.dishname = dishname;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "Dish [dishid=" + dishid + ", hotelid=" + hotelid + ", dishname=" + dishname + ", price=" + price
				+ ", image=" + Arrays.toString(image) + ", flag=" + flag + "]";
	}
}
