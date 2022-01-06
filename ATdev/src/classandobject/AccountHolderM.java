package classandobject;

public class AccountHolderM {
	int age;
	float accountbalance;
	String name;
	
	static int minage = 25;
	static float minacb = 20000;
	
	AccountHolderM(String name,int age,float accountbalance){
		this.name = name;
		this.age = age;
		this.accountbalance = accountbalance;
	}
	
	void testEligibilityForCC(){
		if(age >=minage && accountbalance >= minacb) {		
			System.out.println(name+" is Eligible for credit card.");
		}else {
			System.out.println(name+" is Not Eligible for credit card.");
		}
	}
	
	public static void main(String[] args) {
		AccountHolderM tom = new AccountHolderM("Tom", 22, 50000);
		AccountHolderM henry = new AccountHolderM("Henry", 27, 24500);
		AccountHolderM sarah = new AccountHolderM("Sarah", 26, 25000);
		
		AccountHolderM.minage = 22;
		AccountHolderM.minacb = 25000;
		
//		System.out.println(minage);
		tom.testEligibilityForCC();
		
		henry.testEligibilityForCC();
		sarah.testEligibilityForCC();		
	}
}