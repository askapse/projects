package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "section")
public class Section {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	String name;	
	char flag;	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char getFlag() {
		return flag;
	}
	public void setFlag(char flag) {
		this.flag = flag;
	}
	
	@Override
	public String toString() {
		return "Section [id=" + id + ", name=" + name + ", flag=" + flag + "]";
	}
	
}
