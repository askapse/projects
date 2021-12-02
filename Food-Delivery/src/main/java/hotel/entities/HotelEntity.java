package hotel.entities;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name = "hotel")
public class HotelEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long userid;

	// name of the user.....
	String fname;
	String lname;

	// date of birth....
	String dob;

	// mobile.........
	String mobile;

	// one to one email and password store at other table for auth ......
	@OneToOne(mappedBy = "hotel")
	HotelAuth auth;

	// Address for Hotel user.....
	@Embedded
	HotelAddress address;

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

	public HotelAuth getAuth() {
		return auth;
	}

	public void setAuth(HotelAuth auth) {
		this.auth = auth;
	}

	public HotelAddress getAddress() {
		return address;
	}

	public void setAddress(HotelAddress address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "HotelEntity [userid=" + userid + ", fname=" + fname + ", lname=" + lname + ", dob=" + dob + ", mobile="
				+ mobile + ", auth=" + auth + ", address=" + address + "]";
	}

}
