package classandobject;

public class AccountHolder {
	int age;
	float accountbalance;
	String name;
	
	AccountHolder(String name,int age,float accountbalance){
		this.name = name;
		this.age = age;
		this.accountbalance = accountbalance;
	}
	
	void testEligibilityForCC(){
		if(age >=25 && accountbalance >= 25000) {
			System.out.println(name+" is Eligible for credit card.");
		}else System.out.println(name+" is Not Eligible for credit card.");
	}
	
	public static void main(String[] args) {
		AccountHolder tom = new AccountHolder("Tom", 22, 50000);
		AccountHolder henry = new AccountHolder("Henry", 27, 26500);
		AccountHolder sarah = new AccountHolder("Sarah", 26, 24000);
		
		tom.testEligibilityForCC();
		henry.testEligibilityForCC();
		sarah.testEligibilityForCC();		
	}
}