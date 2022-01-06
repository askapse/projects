package packageex.school;

public class ClassRoom {
	private int noOfBench;
	private int noOfDesk;
	
	public ClassRoom(int b,int d){
		this.noOfBench = b;
		this.noOfDesk = d;
	}
	
	public void printDeskBench() {
		System.out.println("Bench :"+this.noOfBench+"\nDesk :"+this.noOfDesk);		
	}
}
