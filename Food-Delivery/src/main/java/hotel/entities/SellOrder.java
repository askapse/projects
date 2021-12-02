package hotel.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "sellorder")
public class SellOrder {
	
	@Id
	long orderid;
	long custid;
	long hotelid;
	Date datetime;
	float tbill;
	
	
	
	public float getTbill() {
		return tbill;
	}
	public void setTbill(float tbill) {
		this.tbill = tbill;
	}
	public long getHotelid() {
		return hotelid;
	}
	public void setHotelid(long hotelid) {
		this.hotelid = hotelid;
	}
	public long getOrderid() {
		return orderid;
	}
	public void setOrderid(long orderid) {
		this.orderid = orderid;
	}
	public long getCustid() {
		return custid;
	}
	public void setCustid(long custid) {
		this.custid = custid;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	@Override
	public String toString() {
		return "SellOrder [orderid=" + orderid + ", custid=" + custid + ", hotelid=" + hotelid + ", datetime="
				+ datetime + ", tbill=" + tbill + "]";
	}
	
}
