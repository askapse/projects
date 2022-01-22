package entities;

public class Student {
	private String name;
	private String email;
	private String contact;
	private String course;
	private String address;
	private String city;
	private String state;
	private String contry;
	private int roll;
	
	private float tfee;
	private float paid;
	private float due;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getRoll() {
		return roll;
	}
	public void setRoll(int roll) {
		this.roll = roll;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getContry() {
		return contry;
	}
	public void setContry(String contry) {
		this.contry = contry;
	}
	public float getTfee() {
		return tfee;
	}
	public void setTfee(float tfee) {
		this.tfee = tfee;
	}
	public float getPaid() {
		return paid;
	}
	public void setPaid(float paid) {
		this.paid = paid;
	}
	public float getDue() {
		return due;
	}
	public void setDue(float due) {
		this.due = due;
	}
	@Override
	public String toString() {
		return "Student [name=" + name + ", email=" + email + ", contact=" + contact + ", course=" + course
				+ ", address=" + address + ", city=" + city + ", state=" + state + ", contry=" + contry + ", roll="
				+ roll + ", tfee=" + tfee + ", paid=" + paid + ", due=" + due + "]";
	}
	
}
