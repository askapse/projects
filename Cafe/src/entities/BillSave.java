package entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity(name = "bill")
public class BillSave {
	@Id
	@GeneratedValue
	long id;
	
	Date date;
	String dcpn;
	float tbill;
	float discount;	
	String section;
	String tble;
	String cname;
	float tax;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "bid")
	List<BillDish> dishes;	
	
	
	

	public float getTax() {
		return tax;
	}

	public void setTax(float tax) {
		this.tax = tax;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getTble() {
		return tble;
	}

	public void setTble(String tble) {
		this.tble = tble;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDcpn() {
		return dcpn;
	}

	public void setDcpn(String dcpn) {
		this.dcpn = dcpn;
	}

	public float getTbill() {
		return tbill;
	}

	public void setTbill(float tbill) {
		this.tbill = tbill;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public List<BillDish> getDishes() {
		return dishes;
	}

	public void setDishes(List<BillDish> dishes) {
		this.dishes = dishes;
	}

	@Override
	public String toString() {
		return "BillSave [id=" + id + ", date=" + date + ", dcpn=" + dcpn + ", tbill=" + tbill + ", discount="
				+ discount + ", section=" + section + ", tble=" + tble + ", cname=" + cname + ", tax=" + tax
				+ ", dishes=" + dishes + "]";
	}
}
