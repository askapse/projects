package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "tbl")
public class Tbl {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	String name;
	long secid;
	char flag;

//	@ManyToOne()
//	Section section;
//	
//	public Section getSection() {
//		return section;
//	}
//
//	public void setSection(Section section) {
//		this.section = section;
//	}

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

	public long getSecid() {
		return secid;
	}

	public void setSecid(long secid) {
		this.secid = secid;
	}
	
	public char getFlag() {
		return flag;
	}

	public void setFlag(char flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "Table [id=" + id + ", name=" + name + ", secid=" + secid + ", flag=" + flag + "]";
	}
	
}
