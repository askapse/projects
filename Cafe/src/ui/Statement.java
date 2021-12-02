package ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.plaf.basic.BasicComboBoxEditor;

import databaselogic.Billing;

public class Statement extends JInternalFrame {
	// constructor
	public Statement() {
		stid = new ArrayList<Long>();
		init();
	}

	// intitialization
	private void init() {
		setSize(600, 550);
		setIconifiable(true);
		setClosable(true);
		setFrameIcon(null);
		setResizable(false);
		setLocation(30, 20);
		setTitle("Statments ...");
		setLayout(null);

		// event
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
				Cafe.stment = null;
				System.gc();
				super.internalFrameClosed(e);
			}
		});

		// adding elements

		// select year
		dd = new JCheckBox(); // dd assignment
		mm = new JCheckBox(); // mm assignment
		year = new JComboBox<Integer>();
		for (int i = 2021; i <= 2121; i++) {
			year.addItem(i);
		}
		year.setBounds(120, 5, 75, 25);
		year.addActionListener((e) -> {
			if (mm.isSelected()) {
				if (dd.isSelected()) {
					daySet();
				}
			}
		});
		year.setSelectedItem(new GregorianCalendar().get(Calendar.YEAR));
		add(year);

		// select month
		mm.setBounds(210, 5, 15, 25);
		mm.addActionListener((e) -> {
			if (mm.isSelected()) {
				month.setEnabled(true);
				dd.setEnabled(true);
			} else {
				month.setEnabled(false);
				if (dd.isSelected()) {
					dd.doClick();
				}
				dd.setEnabled(false);
			}
		});
		add(mm);

		month = new JComboBox<String>();
		month.setEnabled(false);
		for (int i = 1; i < 13; i++) {
			month.addItem(String.format("%02d", i));
		}
		month.addActionListener((e) -> {
			if (dd.isSelected()) {
				daySet();
			}
		});
		month.setSelectedItem(String.format("%02d", new GregorianCalendar().get(Calendar.MONTH) + 1));
		month.setBounds(235, 5, 55, 25);
		add(month);

		dd.addActionListener((e) -> {
			if (dd.isSelected()) {
				day.setEnabled(true);
				day.removeAllItems();
				daySet();
			} else {
				day.removeAllItems();
				day.setEnabled(false);
			}
		});
		dd.setEnabled(false);
		dd.setBounds(305, 5, 15, 25);
		add(dd);

		day = new JComboBox<String>();
		day.setEnabled(false);
		day.setBounds(330, 5, 55, 25);
		add(day);

		show = new JButton(" Show ");
		show.setBounds(395, 5, 80, 25);

		// action listener for show statement of selected date month year
		show.addActionListener((e) -> {
			String date;
			if (dd.isSelected()) {
				date = year.getSelectedItem() + "-" + month.getSelectedItem() + "-" + day.getSelectedItem() + " %";
			} else {
				if (mm.isSelected()) {
					date = year.getSelectedItem() + "-" + month.getSelectedItem() + "-%";
				} else {
					date = year.getSelectedItem() + "-%";
				}
			}

			stment = Billing.getStatement(date);
			if (stment != null) {
				for (int i = 0; i < stid.size(); i++) {
					stid.remove(i);
				}
				cnm.removeAllElements();
				dtim.removeAllElements();
				amt.removeAllElements();
				showbill.setEnabled(false);

				for (int i = 0; i < ((List) stment.get(3)).size(); i++) {
					cnm.addElement(((List) stment.get(0)).get(i).toString());
					dtim.addElement(((List) stment.get(1)).get(i).toString());
					amt.addElement(((List) stment.get(2)).get(i).toString());
					stid.add(Long.parseLong(((List) stment.get(3)).get(i).toString()));
				}											
				//date save in the statement date variable for the statement print function use
				stdt =date.charAt(date.length()-2) == ' '?date.split(" %")[0]:date.split("-%")[0];
				printst.setEnabled(true);
				
			} else {
				JOptionPane.showMessageDialog(this, "No transaction your selected date...");
			}
		});
		add(show);

		cntr = new JPanel();
		lists = new JScrollPane(cntr, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		cntr.setLayout(new BoxLayout(cntr, BoxLayout.X_AXIS));
		lists.setBounds(50, 33, 500, 455);

		// customer name
		cnm = new DefaultListModel<String>();
		cstname = new JList<String>(cnm);
		cstname.setMaximumSize(new Dimension(260, Integer.MAX_VALUE));
		cstname.addListSelectionListener((e) -> {
			showbill.setEnabled(true);
			datetime.setSelectedIndex(cstname.getSelectedIndex());
			tbill.setSelectedIndex(cstname.getSelectedIndex());												
		});	
		cstname.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent e){
		        if(e.getClickCount()==2){
		           showbill.doClick();
		        }
		    }
		});
		cntr.add(cstname);
		add(lists);

		// date time
		dtim = new DefaultListModel<String>();
		datetime = new JList<String>(dtim);
		datetime.setMaximumSize(new Dimension(150, Integer.MAX_VALUE));
		datetime.addListSelectionListener((e) -> {
			cstname.setSelectedIndex(datetime.getSelectedIndex());
			tbill.setSelectedIndex(datetime.getSelectedIndex());
		});
		datetime.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent e){
		        if(e.getClickCount()==2){
		           showbill.doClick();
		        }
		    }
		});
		cntr.add(datetime);

		// total bill of the order
		amt = new DefaultListModel<String>();
		tbill = new JList<String>(amt);
		tbill.setMaximumSize(new Dimension(70, Integer.MAX_VALUE));
		tbill.addListSelectionListener((e) -> {
			cstname.setSelectedIndex(tbill.getSelectedIndex());
			datetime.setSelectedIndex(tbill.getSelectedIndex());
		});
		tbill.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent e){
		        if(e.getClickCount()==2){
		           showbill.doClick();
		        }
		    }
		});
		cntr.add(tbill);

		// show bill when select the record
		showbill = new JButton("Show Bill");
		showbill.setBounds(450, 491, 100, 25);
		showbill.setEnabled(false);
		showbill.addActionListener((e) -> {
			List bilst = Billing.getBill(stid.get(tbill.getSelectedIndex()));
			long aplcpn;
			if(bilst.get(0).toString().trim().equals("")) {
				aplcpn = 0;
			}else {
				aplcpn = Long.parseLong(bilst.get(0).toString());
			}
			Cafe.billprnt.add(new BillPreview(((List<entities.Dish>) bilst.get(5)), ((List<Integer>) bilst.get(6)),
					Float.parseFloat(tbill.getSelectedValue().trim()), bilst.get(2).toString(), bilst.get(3).toString(),
					-1, aplcpn, 0, cstname.getSelectedValue(), ((float)bilst.get(1)),(float)bilst.get(4)));
		});
		add(showbill);

		printst = new JButton("Print");
		printst.setEnabled(false);
		printst.setBounds(50, 491, 100, 25);	
		printst.addActionListener((e)->{
			printStatement();
		});
		add(printst);
		
		setVisible(true);

	}

	private void daySet() {
		int mn = Integer.parseInt(month.getSelectedItem().toString());
		if (mn == 1 || mn == 3 || mn == 5 || mn == 7 || mn == 8 || mn == 10 || mn == 12) {
			day.removeAllItems();
			for (int i = 1; i <= 31; i++) {
				day.addItem(String.format("%02d", i));
			}
		} else if (mn == 2) {
			if ((int) year.getSelectedItem() % 4 == 0) {
				if (!((int) year.getSelectedItem() % 100 == 0)) {
					day.removeAllItems();
					for (int i = 1; i <= 29; i++) {
						day.addItem(String.format("%02d", i));
					}
				} else {
					day.removeAllItems();
					for (int i = 1; i <= 28; i++) {
						day.addItem(String.format("%02d", i));
					}
				}
			} else {
				day.removeAllItems();
				for (int i = 1; i <= 28; i++) {
					day.addItem(String.format("%02d", i));
				}
			}
		} else if (mn == 4 || mn == 6 || mn == 9 || mn == 11) {
			day.removeAllItems();
			for (int i = 1; i <= 30; i++) {
				day.addItem(String.format("%02d", i));
			}
		}
	}

	
	private void printStatement() {
		JTextArea stmentarea = new JTextArea();
		String stm = " ------------------------------------------------ Cafe Mocktail ---------------------------------------------------- "
				+ "\n\n Date : "+stdt
					+ "\n ===================================================================================\n";
				
		float ttl = 0;
		for (int i = 0; i < ((List) stment.get(3)).size(); i++) {
			stm +=" \n"+String.format("%-40s",((List) stment.get(0)).get(i).toString()) + String.format("%-25s",((List) stment.get(1)).get(i).toString()) + String.format("%20s",((List) stment.get(2)).get(i).toString());
			ttl += Float.parseFloat(((List) stment.get(2)).get(i).toString().trim());
		}											

		stm += "\n\n\n Total  : "+ ttl 
				+ "\n==================================================================================="
				+ "\n-----------------------------------------THANK YOU FOR VISITING---------------------------------------------";		
		printst.setEnabled(true);

		
		
		stmentarea.setText(stm);
		try {
			if (stmentarea.print()) {
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
		
	}
	
	// variable declaration
	JCheckBox dd;
	JCheckBox mm;
	
	JComboBox<String> day;
	JComboBox<String> month;
	JComboBox<Integer> year;

	JButton show;
	
	List stment;
	String stdt;
	
	JScrollPane lists;
	JPanel cntr;

	List<Long> stid;

	JButton printst;
	JButton showbill;

	DefaultListModel<String> cnm;
	DefaultListModel<String> dtim;
	DefaultListModel<String> amt;
	JList<String> cstname;
	JList<String> datetime;
	JList<String> tbill;
	List<Long> id;

	// end of the variable declaration
}
