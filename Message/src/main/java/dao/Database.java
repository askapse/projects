package dao;

public class Database {

	public static AdminOperation admin;
	public static ClassOperation cls;
	public static StudentOperation st;

	static {
		Database doi = new Database();
		admin = doi.getAOp();
		cls = doi.getClsOp();
		st = doi.getStudent();
	}

	// get the instance for the AdminOpration class
	private AdminOperation getAOp() {
		class Tmp extends AdminOperation {
			Tmp() {
				super();
			}
		}

		return new Tmp();
	}

	// get the instance for the class operation class
	private ClassOperation getClsOp() {
		class Tmp extends ClassOperation {
			Tmp() {
				super();
			}
		}

		return new Tmp();
	}

	// get the instance for the AdminOpration class
	private StudentOperation getStudent() {
		class Tmp extends StudentOperation {
			Tmp() {
				super();
			}
		}

		return new Tmp();
	}
}
