package database;

import entities.Student;

public interface AccountantDataMap {
	boolean checkUser(String user);
	boolean checkPass(String user,String pass);
	boolean saveStudent(Student s);
	Student[] getStudent();
	Student getStudent(int roll);
	boolean checkStudent(int roll);
	float[] duePaidFee(int roll);
	boolean updateDueFee(int roll,float paid,float due);
	boolean updateStudent(int roll,String field,String value);
}
