package io_operations;

import java.io.File;

public class CheckFileOrDirectoryExists {
	public static void main(String[] args) {
		File f = new File("C://users//askap/Downloads/");
		if(f.exists()) {
			System.out.println("Given File Or Directory Is Present");
		}else System.out.println("Given File Or Directory Is Not Present");		
	}
}
