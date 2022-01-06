package io_operations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadDataLineByLine {
	public static void main(String[] args) throws IOException {
		File f = new File("./test.txt");
			if(!f.exists()) f.createNewFile();
		FileReader fr = new FileReader(f);		
		BufferedReader bfr = new BufferedReader(fr);
		String str;
		while((str =bfr.readLine()) != null) {
			System.out.println(str);
		}
		
		bfr.close();
		fr.close();
	}
}
