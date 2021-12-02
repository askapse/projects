package ui;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.hibernate.internal.build.AllowSysOut;

import cafe.The_Cafe;
import databaselogic.Billing;
import entities.BillDish;
import entities.BillSave;
import entities.Cupons;
import entities.Dish;
import entities.Section;
import entities.Tbl;

public class Bill extends JInternalFrame implements InternalFrameListener {
	// coontructor
	public Bill() {
		billdish = new ArrayList<Dish>();
		dishqnt = new ArrayList<Integer>();
		totalbill = 0;
		dscpnpre = 0;
		init();
	}

	private void init() {
		setBounds((int) (getToolkit().getScreenSize().getWidth() / 2 - 350), 5, 700, 600);
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		setIconifiable(true);
		setClosable(true);
		try {
			setSelected(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		setResizable(false);
		setFrameIcon(null);
		setTitle("Bill...");
		setLayout(null);

		// section combobox assignment
		sections = new JComboBox<String>();
		sections.addItem("Select Section");
		for (Section s : The_Cafe.sections) {
			sections.addItem(s.getName());
		}

		sections.addActionListener((e) -> {
			if (sections.getSelectedIndex() == 0) {
				tables.removeAllItems();
				tables.setEnabled(false);
				dishesEnable(false);
			} else {
				tables.removeAllItems();
				tables.setEnabled(true);
				secid = The_Cafe.sections.get(sections.getSelectedIndex() - 1).getId();
			}

			// addiing tables oof selected section
			setTitle("Bill /" + sections.getSelectedItem().toString());
			tables.addItem("Select Table");

			if (sections.getSelectedIndex() > 0) {
				for (Tbl t : The_Cafe.tables) {
					if (t.getSecid() == The_Cafe.sections.get(sections.getSelectedIndex() - 1).getId()) {
						tables.addItem(t.getName());
					}
				}
			}
		});

		sections.setBounds(150, 5, 175, 25);
		add(sections);

		// tables combobox assignment
		tables = new JComboBox<String>();
		tables.addItem("Select Table");
		tables.setEnabled(false);
		tables.addActionListener((e) -> {
			for (Tbl t : The_Cafe.tables) {
				if (t.getSecid() == secid) {
					tblid = t.getId();
					break;
				}
			}
			if (tables.getSelectedIndex() != 0) {
				setTitle(sections.getSelectedItem() + " / " + tables.getSelectedItem());
				dishesEnable(true);
			} else {
				dishesEnable(false);
			}
		});

		tables.setBounds(375, 5, 175, 25);
		add(tables);

		// dishes in the system show here
		dishc = new JPanel();

		dishc.setLayout(new BoxLayout(dishc, BoxLayout.Y_AXIS));
		dishcscrpane = new JScrollPane(dishc);
		dishcscrpane.setBounds(510, 33, 180, 534);
		dshsel = new ArrayList<JCheckBox>();

		Iterator<Dish> ditr = The_Cafe.dishes.iterator();
		while (ditr.hasNext()) {
			Dish d = ditr.next();
			dshsel.add(new JCheckBox(d.getName(), false));
			dishc.add(dshsel.get(dshsel.size() - 1));
			dshsel.get(dshsel.size() - 1).addActionListener((e) -> {
				if (((JCheckBox) e.getSource()).isSelected()) {
					for (Dish dsh : The_Cafe.dishes) {
						if (dsh.getName().equals(((JCheckBox) e.getSource()).getText())) {
							billdish.add(dsh);
							dishqnt.add(1);
							dsm.addElement(dsh.getName());
							dspm.addElement(dsh.getPrice());
							genbill.setEnabled(true);
							totalbill += dsh.getPrice();
							tbill.setText(
									"Total Bill : ------------------------------------------------------------------------- : "
											+ totalbill);
							dsqm.addElement(1);
							break;
						}
					}
				} else {
					for (Dish dsh : The_Cafe.dishes) {
						if (dsh.getName().equals(((JCheckBox) e.getSource()).getText())) {

//							dsadded.setSelectedValue(dsh.getName(), true);

							totalbill -= dsh.getPrice() * dishqnt.get(billdish.indexOf(dsh));
							tbill.setText(
									"Total Bill : ------------------------------------------------------------------------- : "
											+ totalbill);
							dspm.removeElementAt(billdish.indexOf(dsh));
							dsqm.removeElementAt(billdish.indexOf(dsh));
							dsm.removeElementAt(billdish.indexOf(dsh));
							dishqnt.remove(billdish.indexOf(dsh));
							billdish.remove(dsh);

							addds.setEnabled(false);
							lessds.setEnabled(false);
							if (billdish.size() == 0) {
								genbill.setEnabled(false);
							}
							break;
						}
					}
				}
			});
		}
		add(dishcscrpane);

		// dishes added for billing here
		dslistc = new JPanel();
		dslist = new JScrollPane(dslistc);
		dslistc.setLayout(new BoxLayout(dslistc, BoxLayout.X_AXIS));
		dslist.setBounds(30, 33, 450, 300);

		// adding JLists
		dsm = new DefaultListModel<String>();
		dsadded = new JList(dsm);
		dsadded.setMaximumSize(new Dimension(320,Integer.MAX_VALUE));

		dsadded.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				qnts.setSelectedIndex(dsadded.getSelectedIndex());
				prcs.setSelectedIndex(dsadded.getSelectedIndex());

				if (qnts.getSelectedValue() != null) {
					if (qnts.getSelectedValue() <= 1) {
						lessds.setEnabled(false);
						addds.setEnabled(true);
					} else {
						lessds.setEnabled(true);
						addds.setEnabled(true);
					}
				}
			}
		});
		dslistc.add(dsadded);

		dsqm = new DefaultListModel<Integer>();
		qnts = new JList<Integer>(dsqm);
		qnts.setMaximumSize(new Dimension(50,Integer.MAX_VALUE));

		qnts.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				dsadded.setSelectedIndex(qnts.getSelectedIndex());
				prcs.setSelectedIndex(qnts.getSelectedIndex());

			}
		});

		dslistc.add(qnts);

		dspm = new DefaultListModel<Float>();
		prcs = new JList<Float>(dspm);
		prcs.setMaximumSize(new Dimension(80,Integer.MAX_VALUE));
		prcs.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				dsadded.setSelectedIndex(prcs.getSelectedIndex());
				qnts.setSelectedIndex(prcs.getSelectedIndex());

			}
		});
		dslistc.add(prcs);

		add(dslist); // add dishlist jpanel to the internal bill frame

		addds = new JButton("+");
		addds.addActionListener((e) -> {
			updateDishQntBill(qnts.getSelectedIndex(), 1);
			totalbill += prcs.getSelectedValue();
			tbill.setText("Total Bill : ------------------------------------------------------------------------- : "
					+ totalbill);
			lessds.setEnabled(true);
		});
		addds.setEnabled(false);
		addds.setBounds(30, 338, 45, 25);
		add(addds);

		lessds = new JButton("-");
		lessds.addActionListener((e) -> {
			updateDishQntBill(qnts.getSelectedIndex(), -1);
			totalbill -= prcs.getSelectedValue();
			tbill.setText("Total Bill : ------------------------------------------------------------------------- : "
					+ totalbill);
			if (qnts.getSelectedValue() <= 1) {
				lessds.setEnabled(false);
			}
		});
		lessds.setEnabled(false);
		lessds.setBounds(80, 338, 45, 25);
		add(lessds);

		tbill = new JLabel();
		tbill.setBounds(30, 370, 450, 25);
		tbill.setText("Total Bill : ------------------------------------------------------------------------- : "
				+ totalbill);
		add(tbill);

		gencpn = new JCheckBox("Generate Discount Cupon ", false);
		gencpn.setBounds(30, 400, 200, 25);
		gencpn.addActionListener((e) -> {
			if (gencpn.isSelected()) {
				pergencpn.setEnabled(true);
				pergencpn.setValue(1);
				dpersh.setText("1");
			} else {
				pergencpn.setValue(0);
				dpersh.setText("0");
				pergencpn.setEnabled(false);
			}
		});
		add(gencpn);

		pergencpn = new JSlider(1, 100, 1);
		pergencpn.setBounds(250, 400, 100, 25);
		pergencpn.addChangeListener((e) -> {
			dpersh.setText(pergencpn.getValue() + "");
			discountper = pergencpn.getValue();
		});
		pergencpn.setEnabled(false);
		add(pergencpn);

		dpersh = new JLabel("0");
		dpersh.setBounds(355, 400, 50, 25);
		add(dpersh);

		apldsct = new JCheckBox("Apply discount cupon");
		apldsct.setBounds(30, 430, 150, 25);
		apldsct.addActionListener((e) -> {
			if (apldsct.isSelected()) {
				cpnentr.setEnabled(true);
				cpncheck.setEnabled(true);
			} else {
				cpnentr.setEnabled(false);
				cpnentr.setText("");
				cpncheck.setEnabled(false);
				dscpnpre = 0;
			}
		});
		add(apldsct);

		cpnentr = new JTextField();
		cpnentr.setBounds(185, 430, 120, 25);
		cpnentr.setEnabled(false);
		add(cpnentr);

		cpncheck = new JButton("Check");
		cpncheck.setBounds(310, 430, 70, 25);
		cpncheck.addActionListener((e) -> {
			// check cupon is in the database or not for discount
			try {
				int chkr = Billing.checkCupon(Long.parseLong(cpnentr.getText()));
				if (chkr == 1) {
					JOptionPane.showMessageDialog(this, "Cuppon added successfully...", "Congrats...",
							JOptionPane.INFORMATION_MESSAGE);
					cpncheck.setEnabled(false);
					cpnentr.setEnabled(false);
					dscpnpre = Billing.checkCuponPer(Long.parseLong(cpnentr.getText()));
				} else if (chkr == 0) {
					JOptionPane.showMessageDialog(this, "Entered cupon is invalid", "Invalid ...",
							JOptionPane.ERROR_MESSAGE);
				} else if (chkr == -1) {
					JOptionPane.showMessageDialog(this, "Error while checking cupon please try again", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Please enter valid cupon code", "Error...",
						JOptionPane.ERROR_MESSAGE);
			}

		});
		cpncheck.setEnabled(false);
		add(cpncheck);

//		billtemail = new JCheckBox("Want to bill on e-mail");
//		billtemail.setBounds(30, 460, 150, 25);
//		billtemail.addActionListener((e) -> {
//			if (billtemail.isSelected()) {
//				email.setEnabled(true);
//			} else {
//				email.setText("");
//				email.setEnabled(false);
//			}
//		});
//		add(billtemail);

//		email = new JTextField();
//		email.setEnabled(false);
//		email.setBounds(185, 460, 200, 25);
//		add(email);

		blnm = new JLabel("Customer Name");
		blnm.setBounds(30, 490, 100, 25);
		add(blnm);

		cbname = new JTextField();
		cbname.setBounds(135, 490, 250, 25);
		cbname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (cbname.getText().trim().matches("[A-Za-z ]+")) {
					if (cbname.getText().trim().length() < 5) {
						nmerr.setText("very short name");
						if (genbill.isEnabled()) {
							genbill.setEnabled(false);
						}
					} else {
						nmerr.setText("");
						if (billdish.size() != 0) {
							if (!genbill.isEnabled()) {
								genbill.setEnabled(true);
							}
						}
					}
				} else {
					nmerr.setText("Invalid input");
					if (genbill.isEnabled()) {
						genbill.setEnabled(false);
					}
				}
				super.keyReleased(e);
			}
		});

		add(cbname);

		nmerr = new JLabel();
		nmerr.setBounds(135, 515, 250, 20);
		nmerr.setForeground(new Color(255, 0, 0));
		add(nmerr);

		genbill = new JButton("Generate Bill");
		genbill.setBounds(355, 540, 150, 25);
		genbill.addActionListener((e) -> {
			if (cbname.getText().trim().length() < 5) {
				genbill.setEnabled(false);
				nmerr.setText("very short name");
				return;
			}

			long cpn = 0;
			Cupons cupn = new Cupons();
			if (gencpn.isSelected()) {
				cpn = cuponGen();
				cupn.setCnum(cpn);
				cupn.setFlag('p');
				cupn.setPer(pergencpn.getValue());
			}
			float tax = (((totalbill-((totalbill*dscpnpre)/100))*The_Cafe.tax)/100);
			if (apldsct.isSelected()) {

				if (dscpnpre != 0) {

					Billing.addBill(makeBill(cpnentr.getText()));
					Billing.useCupon(Long.parseLong(cpnentr.getText()));
					try {
						this.setClosed(true);
					} catch (PropertyVetoException e1) {
						e1.printStackTrace();
					}
					if (cpn != 0) {
						Billing.addCupon(cupn);
					}
					
					Cafe.billprnt.add(new BillPreview(billdish, dishqnt, totalbill,
							sections.getSelectedItem().toString(), tables.getSelectedItem().toString(), dscpnpre,
							Long.parseLong(cpnentr.getText()), cpn, cbname.getText(),0,tax));
				} else {
					JOptionPane.showMessageDialog(this, "Please verify cupon first");
				}
			} else {

				Billing.addBill(makeBill(""));
				try {
					this.setClosed(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
				if (cpn != 0) {
					Billing.addCupon(cupn);
				}
				Cafe.billprnt.add(new BillPreview(billdish, dishqnt, totalbill, sections.getSelectedItem().toString(),
						tables.getSelectedItem().toString(), dscpnpre, 0, cpn, cbname.getText(),0,tax));
			}
		});

		genbill.setEnabled(false);
		add(genbill);

		dishesEnable(false);
		setVisible(true);
	}

	// update the price of the perticular qnt list index
	private void updateDishQntBill(int index, int qnt) {
		int q = dishqnt.remove(index);
		q += qnt;
		dishqnt.add(index, q);
		dsqm.removeAllElements();
		for (int i : dishqnt) {
			dsqm.addElement(i);
		}
		qnts.setSelectedIndex(index);
	}

	// cupon generator
	private long cuponGen() {
		Random rnd = new Random();
		long cpn;
		do {
			cpn = (long) (Math.random() * (Long.MAX_VALUE - 1000 + 1) + 1000);
		} while (Billing.checkCuponToAdd(cpn));
		return cpn;
	}

	// set dishes enabled or disabled
	private void dishesEnable(boolean v) {
		if (v) {
			for (JCheckBox jbx : dshsel) {
				jbx.setEnabled(true);
			}
		} else {
			for (JCheckBox jbx : dshsel) {
				if (jbx.isSelected()) {
					jbx.doClick();
				}
				jbx.setEnabled(false);
			}
		}
	}

	// make bill
	private BillSave makeBill(String cpn) {
		BillSave bsl = new BillSave();
		List<BillDish> blds = new ArrayList<BillDish>();
		bsl.setDcpn(cpn);
		bsl.setDiscount((totalbill * dscpnpre) / 100);
		bsl.setSection(sections.getSelectedItem().toString());
		bsl.setTble(tables.getSelectedItem().toString());
		bsl.setTbill(totalbill - ((totalbill * dscpnpre) / 100));
		bsl.setDate(new Date());
		bsl.setCname(cbname.getText().toUpperCase().trim());
		bsl.setTax((((totalbill-((totalbill*dscpnpre)/100))*The_Cafe.tax)/100));
		Iterator<Dish> ditr = billdish.iterator();
		Iterator<Integer> qitr = dishqnt.iterator();
		while (ditr.hasNext() && qitr.hasNext()) {
			BillDish bld = new BillDish();
			bld.setDishid(ditr.next().getDishid());
			bld.setQnt(qitr.next());
			blds.add(bld);
			bld = null;
			System.gc();
		}
		bsl.setDishes(blds);

		return bsl;
	}

	// variable declaration
	JComboBox<String> sections;
	JComboBox<String> tables;
	long secid;
	long tblid;

	JPanel dishc;
	JScrollPane dishcscrpane;

	// list manipulation buttons
	JButton addds;
	JButton lessds;

	List<Dish> billdish;
	List<Integer> dishqnt;
	List<JCheckBox> dshsel;

	JList<String> dsadded;
	JList<Integer> qnts;
	JList<Float> prcs;

	JPanel dslistc;
	JScrollPane dslist;

	DefaultListModel<String> dsm;
	DefaultListModel<Integer> dsqm;
	DefaultListModel<Float> dspm;

	// total bill
	JLabel tbill;

	float totalbill;

	// cupon generator
	JCheckBox gencpn;
	long cupongen;
	int discountper;
	JLabel dpersh;
	JSlider pergencpn;

	// cupon percentage for discount
	int dscpnpre;

	JCheckBox apldsct;
	JTextField cpnentr;
	JButton cpncheck;

//	JCheckBox billtemail;
//	JTextField email;

	JLabel blnm;
	JTextField cbname;
	JLabel nmerr;

	JButton genbill;

	// end of variable declaration

	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		Cafe.billswindow.remove(this);
		System.gc();
	}

	@Override
	public void internalFrameIconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}
}
