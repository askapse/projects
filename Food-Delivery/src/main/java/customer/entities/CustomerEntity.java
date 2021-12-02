package customer.entities;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity(name = "customer")
public class CustomerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long userid;

	// name of the user.....
	String fname;
	String lname;

	// date of birth....
	String dob;

	// mobile.........
	String mobile;

	// one to one email and password store at other table for auth ......
	@OneToOne(mappedBy = "customer")
	CustomerAuth auth;

	// Address for Hotel user.....
	@Embedded
	CustomerAddress address;	
	
	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	
	public CustomerAuth getAuth() {
		return auth;
	}

	public void setAuth(CustomerAuth auth) {
		this.auth = auth;
	}

	public CustomerAddress getAddress() {
		return address;
	}

	public void setAddress(CustomerAddress address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "CustomerEntity [userid=" + userid + ", fname=" + fname + ", lname=" + lname + ", dob=" + dob
				+ ", mobile=" + mobile + ", auth=" + auth + ", address=" + address + "]";
	}

}
