package io_operations;

import java.io.File;
import java.util.Date;

public class LastModifiedTime {
	public static void main(String[] args) {
		File f = new File("C://users/askap/Downloads/Cafe.jar");
		System.out.println("Last modified date fo the given file : "+new Date(f.lastModified()));
	}
}
