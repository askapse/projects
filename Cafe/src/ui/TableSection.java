package ui;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.plaf.basic.BasicComboBoxEditor;

import cafe.The_Cafe;
import entities.Section;
import entities.Tbl;

public class TableSection extends JInternalFrame {

	// constructor
	public TableSection() {
		init();
	}

	// initializer
	public void init() {
		setBounds((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - 300, 100, 600, 400);
		setClosable(true);
		setIconifiable(true);
		setFrameIcon(null);
		setTitle("Table / Section");

		// evnets
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
				Cafe.tsc = null;
				System.gc();
				super.internalFrameClosed(e);
			}
		});

		// add tabbed pane
		cntr = new JTabbedPane(JTabbedPane.LEFT);
		add(cntr);

		// assinging the JCombobox instance
		secs = new JComboBox<String>();
		secs.addItem("Select / Enter section");

		// adding add remove sections tab to the tabbed pane
		adrs = new AddRemoveSection();
		cntr.addTab("Add / Remove section", adrs);

		// add tabs to the tabbedpane
		Iterator itr = The_Cafe.sections.iterator();
		while (itr.hasNext()) {
			Section section = (Section) itr.next();
			List<Tbl> tbls = new ArrayList<Tbl>();
			for (Tbl t : The_Cafe.tables) {
				if (t.getSecid() == section.getId()) {
					tbls.add(t);
				}
			}
			cntr.addTab(section.getName(), new TablePanel(tbls, section.getId()));
			secs.addItem(section.getName());
			tbls = null;
			System.gc();
		}

		setVisible(true);
	}

	// variable declaration
	public static JTabbedPane cntr;
	private List<JPanel> tabs;
	public static JComboBox<String> secs;

	public static AddRemoveSection adrs;

	// end of the variable declaration
}
