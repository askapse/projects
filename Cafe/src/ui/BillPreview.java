package ui;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.PrinterException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import entities.Dish;

public class BillPreview extends JFrame implements WindowListener {
	// constructor
	public BillPreview(List<Dish> billdishes, List<Integer> dsqnt, float totalbill, String section, String table,
			int dsper, long aplcpn, long gencpn, String cname,float discount,float tax) {
		this.billdishes = billdishes;
		this.dsqnt = dsqnt;
		this.totalbill = totalbill;
		this.section = section;
		this.table = table;
		this.dsper = dsper;
		this.aplcpn = aplcpn;
		this.gencpn = gencpn;	
		this.cname = cname;
		this.discount = discount;
		this.tax = tax;
	 	init();
	}

	// initialization
	private void init() {
		setSize(600, 500);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		setResizable(false);
//		setUndecorated(true);
//		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setIconImage(new ImageIcon(System.getProperty("user.dir") + "/coffee.png").getImage());

		billtext = " ------------------------------------------------ Cafe Mocktail ---------------------------------------------------- "
				+ "\n Section : " + section + "   Table : " + table + "             Date :" + new Date()
				+ "\n Customer Name : "+cname
				+ "\n ===================================================================================";
		Iterator<Dish> ditr = billdishes.iterator();
		Iterator<Integer> qitr = dsqnt.iterator();

		while (ditr.hasNext() && qitr.hasNext()) {
			int q = qitr.next();
			Dish d = ditr.next();
			billtext += "\n " + String.format("%-30s", d.getName()) + " " + String.format("%-10d", q) + " "
					+ String.format("%-10.2f", d.getPrice() * q);
		}

		billtext +="\n\n";
		if (gencpn > 0) {
			billtext += "\n You got new Cupon for next visit : " + gencpn + "";
		}
		
		if(dsper != -1) {
			if (aplcpn > 0) {
				billtext += "\n Applied Cupon : " + aplcpn + "" + "\n Discount percentage : " + dsper
						+"\n Discount for cupon : " + (totalbill * dsper) / 100;
			}
			billtext += "\n Bill Amount    : "
					+ (totalbill - ((totalbill * dsper) / 100))+ "\nSevice tax     : "+(int)(((float)tax/(totalbill - ((totalbill * dsper) / 100)))*100)+" %"
					+"\n Service Tax Amount : "+tax
					+"\n\n Total Bill Amount :"+((totalbill-((totalbill * dsper) / 100))+tax);
		}else {
			if(aplcpn > 0 ) {
				billtext +="\n Applied Cupon       : " + aplcpn+""
						+ "\n Discount for cupon  : "+discount;						
			}
			billtext += "\n Bill Amount    : "+totalbill+""
					+ "\nSevice tax       : "+(int)(((float)tax/totalbill)*100)+" %"
					+  "\n Service Tax Amount : "+tax
					+"\n\n Total Bill Amount  :"+(totalbill+tax);
			
		}
		
				billtext += "\n==================================================================================="
				+ "\n-----------------------------------------THANK YOU FOR VISITING---------------------------------------------";

		print = new JTextArea(billtext);
		add(new JScrollPane(print), BorderLayout.CENTER);
		print.setEditable(false);
		billprint = new JButton("Print Bill");
		billprint.addActionListener((e) -> {
			try {
				if (print.print()) {
					JOptionPane.showMessageDialog(this, "Printing done ...", "Printer",
							JOptionPane.INFORMATION_MESSAGE);
					this.dispose();
				} else {
					JOptionPane.showMessageDialog(this, "Error printing ...", "Printer", JOptionPane.ERROR_MESSAGE);
				}
			} catch (PrinterException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Printer", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		});
		add(billprint, BorderLayout.SOUTH);
		setVisible(true);
	}

	// variable declaration
	String billtext;
	List<Dish> billdishes;
	List<Integer> dsqnt;

	float totalbill;

	JTextArea print;

	JButton billprint;

	String section;
	String table;
	int dsper;
	float discount;

	// cupon
	long aplcpn;
	long gencpn;
	float tax;
	
	String cname;

	// end of variable declaration

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		Cafe.billprnt.remove(this);

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}