package ui;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Cafe extends JFrame {

    //cafe constructor
    public Cafe() {
    	billprnt = new ArrayList<BillPreview>();
        init();
    }

    //initialize all the window
    public void init() {
    	mainwin = this;
    	mainwd = new JDesktopPane();
    	mainwd.setLayout(null);
    	this.add(mainwd);
    	
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(this.getSize());
        setMaximumSize(this.getSize());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setIconImage(new ImageIcon(System.getProperty("user.dir")+"/coffee.png").getImage());
        
        //setting menubbar
        menubar = new JMenuBar();
        this.setJMenuBar(menubar);

        //setting menu
        file = new JMenu("File");
        menubar.add(file);
        
        dish = new JMenuItem("Dish");
        dish.addActionListener((e) -> {
        	if(dishf == null) {
        		dishf = new DishFrame();         		
        		mainwd.add(dishf);
        		try {
					dishf.setSelected(true);
				} catch (PropertyVetoException e1) {
					JOptionPane.showMessageDialog(mainwin,e1.getMessage());					
				}        		
        	}else {
        		try {
					dishf.setSelected(true);
				} catch (PropertyVetoException e1) {
					JOptionPane.showMessageDialog(mainwin, e1.getMessage());
				}
        	}
        });        
        file.add(dish);

        addtable = new JMenuItem("Table / Section");
        addtable.addActionListener((e)->{
            if(tsc == null) {
            	tsc = new TableSection();
            	mainwd.add(tsc);
            	try {
					tsc.setSelected(true);
				} catch (PropertyVetoException e1) {
					JOptionPane.showMessageDialog(mainwin,e1.getMessage());
					e1.printStackTrace();
				}
            }else {
            	try {
					tsc.setSelected(true);
				} catch (PropertyVetoException e1) {
					JOptionPane.showMessageDialog(mainwin,e1.getMessage());
					e1.printStackTrace();
				}
            }
        });
        file.add(addtable);
        
        exit = new JMenuItem("Exit");
        exit.addActionListener((e)->{
         int chk = JOptionPane.showConfirmDialog(this,"You wan't to close application ?","Confirm exit...",JOptionPane.YES_NO_OPTION);
              if(chk == 0){
                  System.exit(0);
              }            
        });
        file.add(exit);
        
        
        bills = new JMenu("Bills");
        menubar.add(bills);
        
        billswindow = new ArrayList<Bill>();
        newbill = new JMenuItem("New Bill");
        newbill.addActionListener((e)->{
        	billswindow.add(new Bill());
        	mainwd.add(billswindow.get(billswindow.size()-1));
        });
        bills.add(newbill);       
        
        statement = new JMenuItem("Statement");
        statement.addActionListener((e)->{
        		if(stment == null) {
        			stment = new Statement();
        			mainwd.add(stment);
        			try {
						stment.setSelected(true);
					} catch (PropertyVetoException e1) {
						e1.printStackTrace();
					}
        		}else {
        			try {
						stment.setSelected(true);
					} catch (PropertyVetoException e1) {
						e1.printStackTrace();
					}
        		}
        });
        bills.add(statement);
        
        help = new JMenu("Help");
        menubar.add(help);
        
        setting = new JMenuItem("Setting");
        setting.addActionListener((e)->{
        	if(settings == null) {
        		settings = new Settings();
            	mainwd.add(settings);
            	try {
    				settings.setSelected(true);
    			} catch (PropertyVetoException e1) {
    				e1.printStackTrace();
    			}
        	}else {
        		try {
					settings.setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
        	}
        });
        help.add(setting);
        
        about = new JMenuItem("About");
        about.addActionListener((e)->{
        	JOptionPane.showMessageDialog(this, "Version 1.0\n\nReleased On : 09-10-2021");
        });
        help.add(about);
        
        
        //events
        this.addWindowListener(new WindowAdapter() {
        	@Override
        	public void windowClosing(WindowEvent e) {
        		 int chk = JOptionPane.showConfirmDialog(mainwin,"You wan't to close application ?","Confirm exit...",JOptionPane.YES_NO_OPTION);
                 if(chk == 0){
                     System.exit(0);
                 }                        
        		super.windowClosing(e);
        	}
        });
        
    setVisible(true);   
    }

    //variables defination
    private JFrame mainwin;
    private JDesktopPane mainwd;
    
    private JMenuBar menubar;    //menubar and it's items
    private JMenu file;
    private JMenu bills;
    private JMenu help;

    private JMenuItem dish;
    private JMenuItem addtable;
    private JMenuItem exit;
    private JMenuItem newbill;
    private JMenuItem statement;
    private JMenuItem about;
    private JMenuItem setting;
    
    
    
    public static DishFrame dishf;  //internal frames
    public static TableSection tsc;
    public static Settings settings;
    public static Statement stment;
    
//    public static JFrame NewBill; //frame
    
    
    public static List<BillPreview> billprnt;
    public static List<Bill> billswindow;
    //end of the variable defination

}
