package entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Student implements Comparable<Student>{
	@Id
	@GeneratedValue
	int id;
	String name;
	LocalDate dbo;
	int classid;
	String email;
	char gender;
	int roll;
	String mobile;
	String address;
	String pass;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDbo() {
		return dbo;
	}

	public void setDbo(LocalDate dbo) {
		this.dbo = dbo;
	}

	public int getClassid() {
		return classid;
	}

	public void setClassid(int classid) {
		this.classid = classid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public int getRoll() {
		return roll;
	}

	public void setRoll(int roll) {
		this.roll = roll;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", dbo=" + dbo + ", classid=" + classid + ", email=" + email
				+ ", gender=" + gender + ", roll=" + roll + ", mobile=" + mobile + ", address=" + address + ", pass="
				+ pass + "]";
	}

	@Override
	public int compareTo(Student o) {
		if(this.roll == o.roll) return 0;
		if(this.roll > o.roll) return 1;
		else return -1;		
	}
}
