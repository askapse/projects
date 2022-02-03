package userInterface;

import java.io.IOException;

@SuppressWarnings("serial")
public class FileMissingWhileRuntime extends IOException {
	public FileMissingWhileRuntime() {
		super("file missing ...");
		try {
			FeeReport.freereport.finalize();
			System.err.println("Quiting application ....");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public FileMissingWhileRuntime(String str) {
		super(str);
		try {
			FeeReport.freereport.finalize();
			System.err.println("Quiting application ....");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
