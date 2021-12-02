package orders.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "dishorder")
public class DishOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long ordid;
	String orddish;
	long hotelid;
	long customerid;
	Date datetime;

	String status;

	String reason;
	char canceledby;
		
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public char getCanceledby() {
		return canceledby;
	}

	public void setCanceledby(char canceledby) {
		this.canceledby = canceledby;
	}

	public long getOrdid() {
		return ordid;
	}

	public void setOrdid(long ordid) {
		this.ordid = ordid;
	}

	public String getOrddish() {
		return orddish;
	}

	public void setOrddish(String orddish) {
		this.orddish = orddish;
	}

	public long getHotelid() {
		return hotelid;
	}

	public void setHotelid(long hotelid) {
		this.hotelid = hotelid;
	}

	public long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(long customerid) {
		this.customerid = customerid;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "DishOrder [ordid=" + ordid + ", orddish=" + orddish + ", hotelid=" + hotelid + ", customerid="
				+ customerid + ", datetime=" + datetime + ", status=" + status + ", reason=" + reason + ", canceledby="
				+ canceledby + "]";
	}
}
