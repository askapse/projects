package cafe;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.hibernate.cfg.Configuration;

import databaselogic.DishManager;
import databaselogic.TableSectionManager;
import entities.Dish;
import entities.Section;
import entities.Tbl;
import ui.Cafe;

public class The_Cafe {

	// constructor
	public The_Cafe() throws InterruptedException {
		init();
	}

	// initilizer
	public void init() throws InterruptedException {
		// loader screen of the application
		loader = new JFrame();
		loader.setSize(500, 300);
		loader.setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - 300, 100);
		loader.setUndecorated(true);
		loader.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		loader.setLayout(null);
		loader.setIconImage(new ImageIcon(System.getProperty("user.dir") + "/coffee.png").getImage());
		JLabel img = new JLabel(new ImageIcon(System.getProperty("user.dir") + "/loader.png"));
		loader.add(img);
		img.setBounds(0, 0, 500, 280);
		hints = new JLabel("Loading ...");
		hints.setBounds(2, 281, 500, 25);
		loader.add(hints);
		loader.setVisible(true);
		String[] dt = null; 
		try {
			dt = Files.getDt();
			if (dt != null) {
				user = dt[0];
				pass = dt[1];
				db = dt[2];
				try { tax = Integer.parseInt(dt[3]); }catch(Exception ex) {tax = 0; }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (dt != null && getConnection() != null) {
			hints.setText("Loading dishes from database ...");
			dishes = DishManager.getAllDish(); // getting all the dishes from the database
			hints.setText("Dishes loaded suuccessfully ...");
			Thread.sleep(500);
			hints.setText("Loading sections from databases");
			sections = TableSectionManager.getAllSection();
			hints.setText("Sections loaded successfully");
			Thread.sleep(500);
			hints.setText("Loading tables from database");
			tables = TableSectionManager.getAllTable();
			hints.setText("Tables loaded successfully");
			hints.setText("Loading application ...");
			cafe = new Cafe();
			Thread.sleep(500);
			loader.dispose();
			loader = null;
			
			System.gc();
			cafe.setVisible(true);
		}else {
			loader.dispose();
			loader = null;
			hints = null;
			System.gc();
			dishes = new ArrayList<Dish>();
			sections = new ArrayList<Section>();
			tables = new ArrayList<Tbl>();
			cafe = new Cafe();
			cafe.setVisible(true);
			JOptionPane.showMessageDialog(cafe, "You not have set database connection ","Error",JOptionPane.ERROR_MESSAGE);					
		}							
	}

	public static Configuration getConnection() {
		if (user == null || pass == null || db == null) {
			return null;
		}
		Configuration con = new Configuration().configure("Cafe.cfg.xml");
		con.setProperty("hibernate.connection.user", user);
		con.setProperty("hibernate.connection.password", pass);
		con.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/" + db);

		return con;
	}

	// main method
	public static void main(String[] args) throws InterruptedException {
		new The_Cafe();

//	 Cafe cafe = new Cafe();

	}
		 
	// variable declaration
	private JFrame loader;
	private JLabel hints;

	public static Cafe cafe;

	public static List<Dish> dishes;
	public static List<Tbl> tables;
	public static List<Section> sections;

	public static int billtax;

	public static String user;
	public static String pass;
	public static String db;
	public static int tax;
	// end of variable declaration
}
