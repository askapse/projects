package ui;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import cafe.Files;
import cafe.The_Cafe;
import databaselogic.DishManager;
import databaselogic.TableSectionManager;

public class Settings extends JInternalFrame {
	// constructor
	public Settings() {
		init();
	}

	// initialization here
	private void init() {
		setSize(550, 350);
		setFrameIcon(null);
		setIconifiable(true);
		setClosable(true);
		setTitle("Settings");
		setLocation((int) getToolkit().getScreenSize().getWidth() - 560, 10);
		setResizable(false);
		setLayout(null);

		// event
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
				Cafe.settings = null;
				System.gc();
				super.internalFrameClosed(e);
			}
		});

		dhead = new JLabel("... Databse Configuration ...");
		dhead.setBounds(30, 2, 200, 25);
		add(dhead);

		du = new JLabel("Usename ");
		du.setBounds(5, 35, 75, 25);
		add(du);

		dbuser = new JTextField();
		dbuser.setBounds(80, 35, 170, 25);
		dbuser.setText((The_Cafe.user == null) ? "" : The_Cafe.user);
		add(dbuser);

		dp = new JLabel("Password");
		dp.setBounds(5, 65, 75, 25);
		add(dp);

		dbpass = new JPasswordField();
		dbpass.setBounds(80, 65, 170, 25);
		dbpass.setText((The_Cafe.pass == null) ? "" : The_Cafe.pass);
		dbpass.setNextFocusableComponent(database);
		add(dbpass);

		showpass = new JCheckBox("Show / hide password");
		showpass.setBounds(80, 87, 170, 20);
		showpass.setFocusable(false);
		showpass.addActionListener((e) -> {
			dbpass.setEchoChar(showpass.isSelected() ? '\u0000' : (Character) UIManager.get("PasswordField.echoChar"));
		});
		add(showpass);

		dbl = new JLabel("Database");
		dbl.setBounds(5, 110, 75, 25);
		add(dbl);

		database = new JTextField();
		database.setBounds(80, 110, 170, 25);
		database.setText((The_Cafe.db == null) ? "" : The_Cafe.db);
		add(database);

		check = new JButton("Check");
		check.setBounds(165, 160, 80, 22);
		check.addActionListener((e) -> {
			String user = dbuser.getText();
			String pass = String.copyValueOf(dbpass.getPassword());
			String db = database.getText();

			if (user.equals("") || pass.equals("") || db.equals("")) {
				JOptionPane.showMessageDialog(this, "You miss something...", "Validate...", JOptionPane.ERROR_MESSAGE);
			} else {
				// database chacking
				try {
					Configuration con = new Configuration().configure("Cafe.cfg.xml");
					con.setProperty("hibernate.connection.user", user);
					con.setProperty("hibernate.connection.password", pass);
					con.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/" + db);
					SessionFactory sf = con.buildSessionFactory();
					int chkr = JOptionPane.showConfirmDialog(this, "Connection Successfull , you want to store ?",
							"Connection Confirm ...", JOptionPane.YES_NO_OPTION);
					sf.close();
					con = null;
					System.gc();
					if (chkr == 0) {
						// save for future use configuration
						Files.setDt(user, pass, db, null);
						The_Cafe.user = user;
						The_Cafe.pass = pass;
						The_Cafe.db = db;
						if(reloadSysrem()) JOptionPane.showMessageDialog(this, "System data reloaded with new configuration");
					}
				} catch (Exception ex) {
					System.out.println(ex);
					JOptionPane.showMessageDialog(this, "You have enter wrong inputs ...", "Connection Error...",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		add(check);

		txl = new JLabel("Tax percent ");
		txl.setBounds(305, 25, 75, 25);
		add(txl);

		erltx = new JLabel();
		erltx.setBounds(305, 49, 170, 20);
		erltx.setForeground(new Color(255, 0, 0));
		add(erltx);

		tax = new JTextField();
		tax.setBounds(380, 25, 170, 25);
		tax.setText(The_Cafe.tax == 0 ? "" : String.valueOf(The_Cafe.tax));
		tax.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (tax.getText().matches("[0-9]{2}")) {
					if (!svtax.isEnabled())
						svtax.setEnabled(true);
					erltx.setText("");
				} else {
					if (svtax.isEnabled())
						svtax.setEnabled(false);
					erltx.setText("Invalid Input");
				}
				super.keyReleased(e);
			}
		});
		add(tax);

		svtax = new JButton("Save");
		svtax.setBounds(465, 80, 70, 22);
		if (!tax.getText().matches("[0-9]{2}")) {
			svtax.setEnabled(false);
		}
		svtax.addActionListener((e) -> {
			try {
				Files.setDt("", "", "", Integer.parseInt(tax.getText()));
				The_Cafe.tax = Integer.parseInt(tax.getText());
			} catch (NumberFormatException | IOException e1) {
				e1.printStackTrace();
			}
		});
		add(svtax);

		setVisible(true);
	}

	private boolean reloadSysrem() {
		try {
			The_Cafe.sections = TableSectionManager.getAllSection();
			The_Cafe.tables = TableSectionManager.getAllTable();
			The_Cafe.dishes = DishManager.getAllDish(); // getting all the dishes from the database
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// variable declaration
	JLabel dhead;
	JLabel du;
	JLabel dp;
	JTextField dbuser;
	JPasswordField dbpass;
	JCheckBox showpass;
	JLabel dbl;
	JTextField database;

	JButton save;
	JButton check;

	JLabel txl;
	JLabel erltx;
	JTextField tax;
	JButton svtax;

	// end of the variable declaration
}
