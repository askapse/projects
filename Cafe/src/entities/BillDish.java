package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "billdish")
public class BillDish {
	@Id
	@GeneratedValue
	long id;
	
	long dishid;
	int qnt;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getDishid() {
		return dishid;
	}
	public void setDishid(long dishid) {
		this.dishid = dishid;
	}
	public int getQnt() {
		return qnt;
	}
	public void setQnt(int qnt) {
		this.qnt = qnt;
	}
	@Override
	public String toString() {
		return "BillDish [id=" + id + ", dishid=" + dishid + ", qnt=" + qnt + "]";
	}
}
