package io_operations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LongestWordInTextFile {
	public static void main(String[] args) throws IOException {
		File f = new File("./test.txt");
		if(!f.exists()) f.createNewFile();
		FileReader fr = new FileReader(f);
		BufferedReader bfr = new BufferedReader(fr);
		String longword="";
		String str;
		while((str=bfr.readLine()) != null) {
			String [] s =str.split(" ");
			for(int i=0;i<s.length;i++) {
				if(longword.length()<s[i].length()) longword=s[i];
			}
		}
		
		System.out.println("longest word in the given file : "+longword);
	}
}
