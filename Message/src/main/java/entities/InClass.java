package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class InClass {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;

	String name;
	String batch;

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

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	@Override
	public String toString() {
		return "InClass [id=" + id + ", name=" + name + ", batch=" + batch + "]";
	}
}
