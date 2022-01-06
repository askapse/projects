package io_operations;

import java.io.File;

public class CheckDirectoryOrFile {
	public static void main(String[] args) {
		File f = new File("C://users/askap/Downloads");
		
		System.out.println(f.isDirectory()?"Directory":"File");
	}
}
