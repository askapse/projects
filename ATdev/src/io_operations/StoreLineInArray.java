package io_operations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class StoreLineInArray {
	public static void main(String[] args) throws IOException {
		File f = new File("./test.txt");
		if(!f.exists()) f.createNewFile();
		FileReader fr = new FileReader(f);
		BufferedReader bfr = new BufferedReader(fr);
		String [] arr = new String[0];
		String str;
		while((str=bfr.readLine())!=null) {
			String [] s = Arrays.copyOf(arr,arr.length);
			arr = Arrays.copyOf(s,s.length+1);
			arr[arr.length-1] =str;			
		}
		bfr.close();
		fr.close();
		System.out.println(Arrays.deepToString(arr));		
	}
}
