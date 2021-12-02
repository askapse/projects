package cafe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.hibernate.internal.build.AllowSysOut;

public class Files {
	// logger for errors
	public static void logger(String log) {
		File logfile = new File(System.getProperty("user.dir") + "/log.txt");
		if (!logfile.exists()) {
			try {
				logfile.createNewFile();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			try {
				FileWriter fw = new FileWriter(logfile);
				fw.append("\n" + new Date() + " :: " + log);
				fw.flush();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String[] getDt() throws IOException {
		File f = new File(System.getProperty("user.home")+"/db.data");
		String data="";
		if(f.exists()) {
			
			FileInputStream fin = new FileInputStream(f);

			int i = fin.read();
			while(i  != -1){
				data +=(char)i;
				i = fin.read();
			}
			fin.close();
			return data.split("\n");
		}else {	
			return null;
		}		
	}
	
	
	public static void setDt(String user,String pass,String db,Integer tax) throws IOException {
		String [] dt = getDt();
		File f = new File(System.getProperty("user.home")+"/db.data");
		if(dt == null) {	
			f.createNewFile();
			dt = new String[4];
			dt[0]=user;
			dt[1]=pass;
			dt[2]=db;
			dt[3]=tax==null?"":tax.toString();
		}else {
			dt = dt.length!=4?new String[4]:dt;
			f.delete();
			f.createNewFile();
			dt[0]=user.equals("")?dt[0]==null?"":dt[0]:user;
			dt[1]=pass.equals("")?dt[1]==null?"":dt[1]:pass;
			dt[2]=db.equals("")?dt[2]==null?"":dt[2]:db;
			dt[3]=tax==null?dt[3]==null?"":dt[3]:tax.toString();
		}
		FileOutputStream fout = new FileOutputStream(f);
		String data="";
		for(int i=0;i<4;i++) {			
			data+=dt[i];
			if(i!=dt.length) {
				data+='\n';
			}
		}
		
		fout.write(data.getBytes());
		fout.flush();
		fout.close();
	}
	// setting storage

	
	
//	public static void main(String[] args) throws IOException {		
////		saveDB("", "", "");
//	}
}
