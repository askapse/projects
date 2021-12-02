package ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxEditor;

import cafe.The_Cafe;
import databaselogic.TableSectionManager;
import entities.Section;
import entities.Tbl;

public class TablePanel extends JPanel {
	// connstructor
	public TablePanel(List<Tbl> tables, long s_id) {
		this.tables = tables;
		this.s_id = s_id;
		init();
	}

	public void init() {
		setLayout(new BorderLayout());

		tbls = new JComboBox<String>();		
		tbls.setEditable(true);
		tbls.addItem("Select / Enter table");
		for (Tbl t : tables) {
			tbls.addItem(t.getName());
		}
		tbedtr = new BasicComboBoxEditor();
		tbls.setEditor(tbedtr);

		tbls.addActionListener((e) -> {
			if (tbls.getSelectedIndex() != -1) {
				slct = tbls.getSelectedIndex();
			}

			if (slct == 0) {
				tbedtr.selectAll();
				tbls.setEditable(true);
				if (!save.isEnabled()) {
					save.setEnabled(true);
					delete.setEnabled(false);
//					update.setEnabled(false);
				}
			} else {
				save.setEnabled(false);
				delete.setEnabled(true);
//				update.setEnabled(true);
				if (slct > 0) {
					tbls.setEditable(false);
				}
			}
		});

		add(tbls, BorderLayout.NORTH);

		btncntr = new JPanel();
		btncntr.setLayout(new BorderLayout(20, 30));
		save = new JButton("Save");
		save.addActionListener((e) -> {
			String tname = tbls.getSelectedItem().toString();
			if (tname.matches("[A-Z0-9 ]+")) {
				save.setEnabled(false);
				if (tname.length() <= 5) {
					Tbl tbl = new Tbl();
					tbl.setName(tname);
					tbl.setFlag('p');
					tbl.setSecid(s_id);

					boolean f = false;
					for (Tbl t : tables) {
						if (tname.trim().equalsIgnoreCase(t.getName().trim())) {
							f = true;
							break;
						}
					}

					if (f) {
						JOptionPane.showMessageDialog(Cafe.tsc, "Table name already present in system");
						save.setEnabled(true);
					} else {
						if (TableSectionManager.checkRTable(tname,s_id)) {
							int chkr = JOptionPane.showConfirmDialog(Cafe.tsc,
									"This section  already present in theh system want to recover it ?",
									"Section recover...", JOptionPane.YES_NO_OPTION);
							if (chkr == 0) {
								if (TableSectionManager.recoverTableByName(tname,s_id)) {
									long id = TableSectionManager.getTableId(tname);
									if (id != -1) {
										tbl.setId(id);
										tables.add(tbl);
										The_Cafe.tables.add(tbl);
										tbls.addItem(tname);
										JOptionPane.showMessageDialog(Cafe.tsc, "Table successfully recovered");
										tbls.setSelectedIndex(0);
										tbl = null;
										System.gc();
									} else {
										JOptionPane.showMessageDialog(Cafe.tsc, "please restart the system");
										tbls.setSelectedIndex(0);
									}
								}
							}
						} else {
							if (TableSectionManager.addTable(tbl)) {								
								long id = TableSectionManager.getTableId(tname);
								tbls.addItem(tname);
								if (id == -1) {
									JOptionPane.showMessageDialog(Cafe.tsc, "Please restart the system");
									tbls.setSelectedIndex(0);
								} else {
									tbl.setId(id);
									tables.add(tbl);
									The_Cafe.tables.add(tbl);
									JOptionPane.showMessageDialog(Cafe.tsc, "Table added successfully...");
									tbls.setSelectedIndex(0);
									tbl = null;
									System.gc();
								}
							} else {
								JOptionPane.showMessageDialog(Cafe.tsc, "Error occurs table adding fails");
								save.setEnabled(true);
							}
						}
					}
				} else {
					JOptionPane.showMessageDialog(Cafe.tsc, "Table name less than 5 character");
					save.setEnabled(true);
				}
			} else {
				JOptionPane.showMessageDialog(Cafe.tsc, "Invalid table name \n\nonly Capital letter and numbers allowed");
			}
		});
		btncntr.add(save, BorderLayout.WEST);

		delete = new JButton("Delete");
		delete.setEnabled(false);
		delete.addActionListener((e) -> {
			long id = tables.get(slct - 1).getId();

			if (TableSectionManager.removeTable(id)) {
				
				The_Cafe.tables.remove(tables.get(slct - 1));
				tables.remove(slct - 1);
				tbls.removeItemAt(slct);
				tbls.setSelectedIndex(0);
				JOptionPane.showMessageDialog(Cafe.tsc, "Selected table removed successfully");
			} else {
				JOptionPane.showMessageDialog(Cafe.tsc, "Error while removing selected table please try again");
			}
		});

		btncntr.add(delete, BorderLayout.EAST);

		add(btncntr, BorderLayout.SOUTH);

		setVisible(true);

	}

//	variable declaration 
	private List<Tbl> tables;
	private long s_id;

	private JComboBox<String> tbls;
	private ComboBoxEditor tbedtr;
	private JPanel btncntr;

	private JButton save;
//	private JButton update;
	private JButton delete;

	int slct;

//	end of variable declaration

}
