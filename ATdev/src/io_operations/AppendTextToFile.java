package io_operations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AppendTextToFile {
	public static void main(String[] args) throws IOException {
		File f = new File("./test.txt");
		if(!f.exists()) f.createNewFile();
		FileReader fr = new FileReader(f);
		BufferedReader bfr = new BufferedReader(fr);
		String st[];
		String str;
		System.out.println("Existing text of the file .............................................................");
		while((str = bfr.readLine()) != null) {
			System.out.println(str);
		}
		System.out.println();
		bfr.close();
		fr.close();
		bfr = null;
		fr = null;
		
		FileWriter fw = new FileWriter(f,true);
		BufferedWriter bfw = new BufferedWriter(fw);
		bfw.newLine();
		bfw.write("ok here is new line added");
		bfw.close();
		
		fr = new FileReader(f);
		bfr = new BufferedReader(fr);
		
		System.out.println("After appending text of the file .............................................................");
		while((str = bfr.readLine()) != null) {
			System.out.println(str);
		}
		System.out.println();
		
	}
}
