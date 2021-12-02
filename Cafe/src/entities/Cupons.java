package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "cupons")
public class Cupons {
	@Id
	long cnum;
	char flag;
	int per;
	
	public long getCnum() {
		return cnum;
	}
	public void setCnum(long cnum) {
		this.cnum = cnum;
	}
	public char getFlag() {
		return flag;
	}
	public void setFlag(char flag) {
		this.flag = flag;
	}
	public int getPer() {
		return per;
	}
	public void setPer(int per) {
		this.per = per;
	}
	@Override
	public String toString() {
		return "Cupons [cnum=" + cnum + ", flag=" + flag + ", per=" + per + "]";
	}
}
