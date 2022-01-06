package constuctor;

public class AddAmount {
	static float amount = 50;
	AddAmount() {
		amount += 0;
	} 
	
	AddAmount(float amount){
		AddAmount.amount += amount;
	}
	
	public static void main(String[] args) {
		AddAmount adm = new AddAmount();
		AddAmount adm1 = new AddAmount(20);
		
		System.out.println(AddAmount.amount);
		
	}
}
