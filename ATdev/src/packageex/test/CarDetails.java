package packageex.test;

import packageex.vehicle.*;

public class CarDetails {
	public static void main(String[] args) {
		Car car = new Car();
		
		System.out.println("Color :"+car.color+" Model :"+car.model+" EnginType :"+car.engintype);
	}
}
