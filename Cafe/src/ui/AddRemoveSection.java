package ui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxEditor;
import javax.swing.JButton;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxEditor;

import cafe.The_Cafe;
import databaselogic.TableSectionManager;
import entities.Section;
import entities.Tbl;

public class AddRemoveSection extends JPanel {
	public AddRemoveSection() {
		init();
	}

	// intialization of the object
	private void init() {
		setLayout(new BorderLayout());

		TableSection.secs.setEditable(true);

		secedtr = new BasicComboBoxEditor();
		TableSection.secs.setEditor(secedtr);

		TableSection.secs.addActionListener((e) -> {
			if (TableSection.secs.getSelectedIndex() != -1) {
				slct = TableSection.secs.getSelectedIndex();
			}

			if (slct == 0) {
				secedtr.selectAll();
				TableSection.secs.setEditable(true);
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
					TableSection.secs.setEditable(false);
				}
			}
		});

		add(TableSection.secs, BorderLayout.NORTH);

		btncntr = new JPanel();
		btncntr.setLayout(new BorderLayout(20, 30));

		// buttons for the manipulation of the sections
//		// update button
//		update = new JButton("Update");
//		update.setEnabled(false);
//		update.addActionListener((e) -> {
//			String secname = TableSection.secs.getSelectedItem().toString();
//			if (secname.matches("[a-zA-Z ]+")) {
//				save.setEnabled(false);
//				if (secname.length() <= 25) {
//					Section section = new Section();
//					section.setName(secname);
//					section.setFlag('p');
//
//					boolean f = false;
//					for (Section s : The_Cafe.sections) {
//						if (secname.trim().equalsIgnoreCase(s.getName().trim())) {
//							f = true;
//							break;
//						}
//					}
//
//					if (f) {
//						JOptionPane.showMessageDialog(Cafe.tsc, "Section name already present in system");
//						save.setEnabled(true);
//					} else {
//						section.setId(The_Cafe.sections.get(slct -1).getId());
//						if(TableSectionManager.updateSection(section)) {
//							The_Cafe.sections.get(slct - 1).setName(secname);
//							TableSection.secs.setSelectedIndex(0);
//							section = null;
//							System.gc();
//							
//							JOptionPane.showMessageDialog(Cafe.tsc, "Section update successfully");
//						}else {
//							JOptionPane.showMessageDialog(Cafe.tsc, "Section update failed please try again");
//						}
//					}			
//				}else {
//					JOptionPane.showMessageDialog(Cafe.tsc, "Section name must be less than 25 character");
//					save.setEnabled(true);
//				}
//			}else {
//				JOptionPane.showMessageDialog(Cafe.tsc, "Invalid section name");
//			}
//		});

//		btncntr.add(update, BorderLayout.WEST);

		// save button
		save = new JButton("Save");
		save.addActionListener((e) -> {
			String secname = TableSection.secs.getSelectedItem().toString();
			if (secname.matches("[a-zA-Z ]+")) {
				save.setEnabled(false);
				if (secname.length() <= 25) {
					Section section = new Section();
					section.setName(secname);
					section.setFlag('p');

					boolean f = false;
					for (Section s : The_Cafe.sections) {
						if (secname.trim().equalsIgnoreCase(s.getName().trim())) {
							f = true;
							break;
						}
					}

					if (f) {
						JOptionPane.showMessageDialog(Cafe.tsc, "Section name already present in system");
						save.setEnabled(true);
					} else {
						if (TableSectionManager.checkRSection(secname)) {
							int chkr = JOptionPane.showConfirmDialog(Cafe.tsc,
									"This section  already present in theh system want to recover it ?",
									"Section recover...", JOptionPane.YES_NO_OPTION);
							if (chkr == 0) {
								if (TableSectionManager.recoverSectionByName(secname)) {
									long id = TableSectionManager.getSecId(secname);
									if (id != -1) {
										section.setId(id);
										The_Cafe.sections.add(section);
										TableSection.secs.addItem(secname);
										int ch = JOptionPane.showConfirmDialog(Cafe.tsc,
												"You want to recover previously added tables ?");
										if(ch == 0) {
											List<Tbl> tabl = TableSectionManager.recoverRecovedSecTbl(id);
											TableSection.cntr.addTab(secname, new TablePanel(tabl, id));
										}else {
											TableSection.cntr.addTab(secname, new TablePanel(new ArrayList<Tbl>(), id));
										}
										
										JOptionPane.showMessageDialog(Cafe.tsc, "Section successfully recovered");
										TableSection.secs.setSelectedIndex(0);
										section = null;
										System.gc();
									} else {
										JOptionPane.showMessageDialog(Cafe.tsc, "please restart the system");
										TableSection.secs.setSelectedIndex(0);
									}
								}
							}
						} else {
							if (TableSectionManager.addSection(section)) {
								TableSection.secs.addItem(secname);
								long id = TableSectionManager.getSecId(secname);
								TableSection.cntr.addTab(secname, new TablePanel(new ArrayList<Tbl>(), id));
								if (id == -1) {
									JOptionPane.showMessageDialog(Cafe.tsc, "Please restart the system");
									TableSection.secs.setSelectedIndex(0);
								} else {
									section.setId(id);
									The_Cafe.sections.add(section);
									JOptionPane.showMessageDialog(Cafe.tsc, "Section added successfully...");
									TableSection.secs.setSelectedIndex(0);
									section = null;
									System.gc();
								}
							} else {
								JOptionPane.showMessageDialog(Cafe.tsc, "Error occurs section adding fails");
								save.setEnabled(true);
							}
						}
					}
				} else {
					JOptionPane.showMessageDialog(Cafe.tsc, "Section name less than 25 character");
					save.setEnabled(true);
				}
			} else {
				JOptionPane.showMessageDialog(Cafe.tsc, "Invalid section name");
			}
		});
		btncntr.add(save, BorderLayout.WEST);

		delete = new JButton("Delete");
		delete.setEnabled(false);
		delete.addActionListener((e) -> {
			long id = The_Cafe.sections.get(slct - 1).getId();

			if (TableSectionManager.removeSection(id)) {
				The_Cafe.sections.remove(slct - 1);
				TableSection.secs.removeItemAt(slct);
				TableSection.cntr.remove(slct + 1);
				TableSection.secs.setSelectedIndex(0);
				JOptionPane.showMessageDialog(Cafe.tsc, "Selected section removed successfully");
			} else {
				JOptionPane.showMessageDialog(Cafe.tsc, "Error while removing selected section please try again");
			}
		});

		btncntr.add(delete, BorderLayout.EAST);

		add(btncntr, BorderLayout.SOUTH);

		setVisible(true);
	}

	// variable defination
	private ComboBoxEditor secedtr;

	private JPanel btncntr;

	private JButton save;
	private JButton delete;
//	private JButton update;

	private int slct;

	// end of variable defiantion

}
