package customer.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import databaselogic.CustomerUser;

@Entity(name = "customerauth")
public class CustomerAuth {
	@Id
	long id;
	String email;
	String pass;

	@OneToOne
	@JoinColumn(name = "id")
	@MapsId
	CustomerEntity customer;
	
	
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
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "CustomerAuth [id=" + id + ", email=" + email + ", pass=" + pass + ", customer=" + customer + "]";
	}

}
