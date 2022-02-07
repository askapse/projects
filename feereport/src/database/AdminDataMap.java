package database;

import entities.Accountant;

public interface AdminDataMap {
	boolean checkUser(String user);
	boolean checkPass(String user,String pass);
	boolean checkACName(String name);
	boolean saveAccountant(Accountant ac);
	Accountant[] getAccountantList();
}
