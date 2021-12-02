package ui;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.beans.PropertyVetoException;
import java.util.Iterator;

import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.plaf.basic.BasicComboBoxEditor;

import cafe.The_Cafe;
import databaselogic.DishManager;
import entities.Dish;

public class DishFrame extends JInternalFrame {

	// constructor
	public DishFrame() {
		init();
	}

	// initialize object
	public void init() {
		setBounds((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - 275, 20, 550, 350);
		setIconifiable(true);
		setClosable(true);
		setMinimumSize(getSize());
		setMaximumSize(getSize());
		setFrameIcon(null);
		setLayout(null);
		setTitle("Dish...");

//		events
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
				Cafe.dishf = null;
				System.gc();
				super.internalFrameClosed(e);
			}
		});

		// msg label test here
		msg = new JLabel("");
		msg.setBounds(100, 20, 350, 25);
		add(msg);

		// adding component to the frame
		dishl = new JLabel("Dishes");
		dishl.setBounds(100, 75, 100, 25);
		add(dishl);

		pricel = new JLabel("Price");
		pricel.setBounds(100, 125, 100, 25);
		add(pricel);

		dishes = new JComboBox<>();
		dishes.addItem("Select / Enter ");
		dsedtr = new BasicComboBoxEditor();
		dishes.setEditor(dsedtr);

		// add all the dishes to the combobox
		Iterator itr = The_Cafe.dishes.iterator();
		while (itr.hasNext()) {
			dishes.addItem(((Dish) itr.next()).getName());
		}

		// add onselected event
		dishes.addActionListener((e) -> {
			if (dishes.getSelectedIndex() != -1) {
				seldish = dishes.getSelectedIndex();
			}

			if (seldish == 0) { // if the selected index is zeroth position
				price.setText("");
				dsedtr.selectAll();
//				dishes.setEditable(true);
				if (!save.isEnabled()) {
					save.setEnabled(true);
					update.setEnabled(false);
					delete.setEnabled(false);
				}
			} else { // if the selected item position is greater than on other than 0
				price.setText(String.format("%.0f", The_Cafe.dishes.get(seldish - 1).getPrice()));
				save.setEnabled(false);
				update.setEnabled(true);
				delete.setEnabled(true);
//				if(dishes.getSelectedIndex() > 0) {
//					dishes.setEditable(false);
//				}
			}
		});
		dishes.setEditable(true);

		// focus listener for the dishes editor
		dishes.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				msg.setText("");
				super.focusLost(e);
			}
		});

		dishes.setBounds(230, 75, 220, 25);
		add(dishes);

		price = new JTextField();
		// key press event
		price.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) { // on key release event for the validating price value
				if (price.getText().equals("")) { // if price field is empty
					msg.setText("Please enter price");
					if (save.isEnabled() && seldish == 0) {
						save.setEnabled(false);
					}
					msg.setForeground(new Color(0, 0, 0));
					getToolkit().beep();
				} else {
					// if price field is not empty
					// matches the regex pattern for the only digits in the price textfield
					if (!price.getText().matches("[0-9]+")) {
						msg.setText("Invalid input in price field");
						msg.setForeground(new Color(255, 0, 0));
						if (save.isEnabled() && seldish == 0) { // if slected dish item index is zezro
							save.setEnabled(false);
						}

						if (update.isEnabled() && seldish > 0) { // if selected dish item is other than zeroth index
							update.setEnabled(false);
						}
						getToolkit().beep();

					} else {
						// if the pattern not matches the value in the price field
						msg.setText("");
						// enables save button if selectes item is zeroth position
						if (!save.isEnabled() && seldish == 0) {
							save.setEnabled(true);
						}

						// enable the diable update button if selected dish is other than zeroth
						// position
						if (!update.isEnabled() && seldish > 0) {
							update.setEnabled(true);
						}
					}
				}
			}
		});

		// setting price focus listener
		price.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				msg.setText("");
				super.focusLost(e);
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (price.getText().matches("[0-9]+")) {
					msg.setText("Please enter price");
					msg.setForeground(new Color(0, 0, 0));
					super.focusGained(e);
				} else {
					msg.setText("Correct the price");
					msg.setForeground(new Color(255, 0, 0));
				}
			}
		});
		price.setBounds(230, 125, 220, 25);
		add(price);

		save = new JButton("Save");

		// set what will do after clicking the save button
		save.addActionListener((e) -> {
			this.setEnabled(false);
			// gets dish name from the UI
			String dname = dishes.getSelectedItem().toString();

			// check the price matched the pattern or not
			if (!price.getText().matches("[0-9]+")) {
				msg.setText("Price must be entered");
				msg.setForeground(new Color(255, 0, 0));
				return;
			} else {
				// if price pattern not matchese
				float dprc = Float.parseFloat(price.getText());
				if (!dname.matches("[a-zA-Z ]+")) {
					msg.setText("Digits and other characters are not allowed");
					msg.setForeground(new Color(255, 0, 0));
				} else {
					boolean f = false;
					for (Dish d : The_Cafe.dishes) {
						if (d.getName().trim().equalsIgnoreCase(dname.trim())) {
							f = true;
							break;
						}
					}

					if (!f) {

						if (DishManager.checkRDishName(dname.trim())) {
							int chk = JOptionPane.showConfirmDialog(Cafe.dishf,
									"Given dish already in system recover it ?", "Recover dish...",
									JOptionPane.YES_NO_OPTION);
							if (chk == 0) {
								if (DishManager.recoverDish(dname, dprc)) {
									JOptionPane.showMessageDialog(Cafe.dishf, "Dish recovered with new price");
									Dish dish = DishManager.dishByName(dname);
									The_Cafe.dishes.add(dish);
									dishes.addItem(dname);
									dishes.setSelectedIndex(0);
									price.setText("");
									dish = null;
									System.gc();
								} else {
									JOptionPane.showMessageDialog(Cafe.dishf, "Failed to recover dish");
								}
							} else {
								JOptionPane.showMessageDialog(Cafe.dishf,
										"Can't add this named dish as new please recover previous dish");
							}
						} else {
							Dish dish = new Dish();
							dish.setName(dname);
							dish.setPrice(dprc);
							dish.setFlag('p');
							if (DishManager.addDish(dish) == 1) {
								JOptionPane.showMessageDialog(Cafe.dishf, dname + " dish added successfully");
								dish = DishManager.dishByName(dname);
								The_Cafe.dishes.add(dish);
								dishes.addItem(dname);
								dishes.setSelectedIndex(0);
								price.setText("");
								dish = null;
								System.gc();
							} else {
								JOptionPane.showMessageDialog(Cafe.dishf, "Dish added failed");
							}

						}
					} else {
						JOptionPane.showMessageDialog(Cafe.dishf, "Dish name already in the system");
					}
				}
			}
			this.setEnabled(true);

		});
		save.setBounds(50, 250, 80, 25);
		add(save);

		update = new JButton("Update");
		update.setBounds(170, 250, 80, 25);
		update.addActionListener((e) -> {
			this.setEnabled(false);
			String dname = dishes.getSelectedItem().toString();

			if (!price.getText().trim().matches("[0-9]+")) {
				msg.setText("Price must be entered");
				msg.setForeground(new Color(255, 0, 0));
				return;
			} else {
				float dprc = Float.parseFloat(price.getText());
				if (!dname.matches("[a-zA-Z ]+")) {
					msg.setText("Digits and other characters are not allowed");
					msg.setForeground(new Color(255, 0, 0));
				} else {
					if (DishManager.checkDishNameOtherId(dname.trim(), The_Cafe.dishes.get(seldish - 1).getDishid())) {
						JOptionPane.showMessageDialog(Cafe.dishf, "Entered dish name already present in the system");
					} else {
						Dish dish = new Dish();
						dish.setDishid(The_Cafe.dishes.get(seldish - 1).getDishid());
						dish.setName(dname);
						dish.setPrice(dprc);
						dish.setFlag('p');
						if (DishManager.updateDish(dish)) {
							dish = DishManager.dishByName(dname);
							The_Cafe.dishes.get(seldish - 1).setName(dname);
							The_Cafe.dishes.get(seldish - 1).setPrice(dprc);
							dishes.removeAllItems();
							dishes.addItem("Select / Enter ");
							for (Object d : The_Cafe.dishes.toArray()) {
								dishes.addItem(((Dish) d).getName());
							}
							dishes.setSelectedIndex(0);
							price.setText("");
							JOptionPane.showMessageDialog(Cafe.dishf, "Dish updated successfully");
							dish = null;
							System.gc();
						} else {
							JOptionPane.showConfirmDialog(Cafe.dishf, "Dish updation faild");
						}
					}
				}
			}
			this.setEnabled(true);

		});
		update.setEnabled(false);

		add(update);

		delete = new JButton("Delete");
		delete.setBounds(290, 250, 80, 25);
		delete.setEnabled(false);

		// delete button action listener
		delete.addActionListener((e) -> {
			delete.setEnabled(false);
			if (seldish <= 0) { // nothing to do after selected item is zeroth or less than zeroth
				return;
			}

			// if selected item removed from the database then removed from the UI and data
			// loaded in the system
			if (DishManager.removeDish(The_Cafe.dishes.get(seldish - 1).getDishid()) == 1) {
				dishes.removeItemAt(seldish);
				dishes.repaint();
				The_Cafe.dishes.remove(seldish);

				JOptionPane.showMessageDialog(Cafe.dishf, "Dish removed successfully");
				dishes.setSelectedIndex(0);
				if (seldish > 0) {
					save.setEnabled(false);
					update.setEnabled(true);
					delete.setEnabled(true);
				} else {
					save.setEnabled(true);
					update.setEnabled(false);
					delete.setEnabled(false);
				}
			} else {
				JOptionPane.showMessageDialog(Cafe.dishf, "Failed to remove dish");
			}
		});
		add(delete);

		cancel = new JButton("Cancel");
		cancel.setBounds(410, 250, 80, 25);

		// cancel button click performance/action
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Cafe.dishf.setClosed(true);
				} catch (PropertyVetoException e1) {
					JOptionPane.showMessageDialog(Cafe.dishf, e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		add(cancel);

		setVisible(true);
	}

	// variables declaration

	private int seldish;

	private JLabel msg;
	private JLabel dishl;
	private JLabel pricel;

	private ComboBoxEditor dsedtr;
	private JComboBox<String> dishes;
	private JTextField price;

	private JButton save;
	private JButton update;
	private JButton delete;
	private JButton cancel;

	// end of the variable declaration

}
