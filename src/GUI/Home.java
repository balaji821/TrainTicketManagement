package GUI;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import GUI.Home;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import functions.*;
public class Home {
	public static JTable tb;
	public JScrollPane t;
	public static Color bcolor = Color.BLACK;
	public static Color fcolor = Color.WHITE;
	public static ImageIcon bg_image;
	JScrollPane des1,src1;
	JComboBox<String> pnr;
	JList<String> src;
	JList<String> des;
	JPopupMenu pm;
	JToolBar toolbar;
	boolean tbstatus=false;
	public Home() throws SQLException,NullPointerException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		JFrame f= new JFrame("IRCTC");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.setSize(1000, 800);
	    f.setLayout(null);
	    JLabel logo = new JLabel(new ImageIcon("D:\\my programs\\Mini Project\\src\\GUI\\logo.jpg"));logo.setBounds(0,-100,1000,433);
	    f.add(logo);
	    f.getContentPane().setBackground(Color.BLACK);
	    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	    JMenuItem by_train = new JMenuItem("Search a Train",KeyEvent.VK_T);
	    JMenuItem by_station = new JMenuItem("Trains between Stations",KeyEvent.VK_S);
	    JMenu search = new JMenu("Search"); search.add(by_station); search.add(by_train);search.setMnemonic(KeyEvent.VK_S);
	    JMenuItem m_bg_color = new JMenuItem("Change Background Color",KeyEvent.VK_B);
	    JMenuItem m_fg_color = new JMenuItem("Change Foreground Color",KeyEvent.VK_F);
	    JMenuItem m_bg_image = new JMenuItem("Change Background Image",KeyEvent.VK_I);
	    JMenu tools = new JMenu("Tools");tools.add(m_fg_color); tools.add(m_bg_color); tools.add(m_bg_image);tools.setMnemonic(KeyEvent.VK_T);
	    JMenuItem booked_tickets = new JMenuItem("Booked Tickets",KeyEvent.VK_B);
	    JMenuItem cancel_ticket = new JMenuItem("Cancel Ticket");
	    JMenu my_tickets = new JMenu("My Tickets");my_tickets.add(booked_tickets);my_tickets.add(cancel_ticket);my_tickets.setMnemonic(KeyEvent.VK_M);
	    JMenuBar mb = new JMenuBar(); mb.add(search); mb.add(tools); mb.add(my_tickets);
	    f.setJMenuBar(mb);
	    JMenuItem close = new JMenuItem("Close",KeyEvent.VK_E);
	    JMenuItem bg_color = new JMenuItem("Change Background Color",KeyEvent.VK_B);
	    JMenuItem fg_color = new JMenuItem("Change Foreground Color",KeyEvent.VK_F);
	    JMenuItem bg_image = new JMenuItem("Change Background Image",KeyEvent.VK_I);
	    pm = new JPopupMenu("Tools");pm.add(bg_color);pm.add(fg_color);pm.add(bg_image);pm.add(close);
	    JMenuItem t_bg_color = new JMenuItem("Change Background Color",KeyEvent.VK_B);
	    JMenuItem t_fg_color = new JMenuItem("Change Foreground Color",KeyEvent.VK_F);
	    JMenuItem t_bg_image = new JMenuItem("Change Background Image",KeyEvent.VK_I);
	    toolbar = new JToolBar("Tools",JToolBar.HORIZONTAL);toolbar.addSeparator();toolbar.add(t_bg_color);toolbar.add(t_fg_color);toolbar.addSeparator();toolbar.add(t_bg_image);
	    toolbar.setBounds(100, 600, 800, 100);f.add(toolbar);toolbar.setFloatable(false);toolbar.setVisible(false);
	    by_station.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_DOWN_MASK));
	    by_train.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,InputEvent.CTRL_DOWN_MASK));
	    booked_tickets.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,InputEvent.CTRL_DOWN_MASK));
	    f.addMouseListener(new MouseAdapter() {
	    	public void mouseReleased(MouseEvent e)
	    	{
	    		if(e.isPopupTrigger())
	    		{
	    			pm.show(f,e.getX(),e.getY());
	    		}
	    	}
		});
	    m_bg_color.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Color c = JColorChooser.showDialog(f, "Select a Color", Color.BLACK);
				f.getContentPane().setBackground(c);
			}
		});
	    m_fg_color.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Color c = JColorChooser.showDialog(f, "Select a Color", Color.BLACK);
				f.getContentPane().setForeground(c);
			}
		});
	    m_bg_image.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int r = fc.showDialog(f, "Choose...");
				if(r==JFileChooser.APPROVE_OPTION) {
				ImageIcon bg = new ImageIcon(fc.getSelectedFile().getAbsolutePath());
				Component list[] = removeComponents(f);
				f.setContentPane(new JLabel(bg));
				addComponents(f, list);
				f.validate();
				}
			}
		});
	    bg_color.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame t = (JFrame) ((JPopupMenu)((JMenuItem) e.getSource()).getParent()).getInvoker();
				Color c = JColorChooser.showDialog(t, "Select a Color", Color.BLACK);
				Home.bcolor = c;
				t.getContentPane().setBackground(c);
				//System.out.println(c.toString());
				t.validate();
			}
		});
	    fg_color.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame t = (JFrame) ((JPopupMenu)((JMenuItem) e.getSource()).getParent()).getInvoker();
				Color c = JColorChooser.showDialog(t, "Select a Color", Color.BLACK);
				Home.fcolor = c;
				t.getContentPane().setForeground(c);
				
			}
		});
	    bg_image.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				JFrame t = (JFrame) ((JPopupMenu)((JMenuItem) e.getSource()).getParent()).getInvoker();
				int r = fc.showDialog(t, "Choose...");
				if(r==JFileChooser.APPROVE_OPTION) {
				Home.bg_image = new ImageIcon(fc.getSelectedFile().getAbsolutePath());
				Component list[] = removeComponents(t);
				t.setContentPane(new JLabel(Home.bg_image));
				addComponents(t, list);
				t.validate();
				}
			}
		});
	    close.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				((JFrame)((JPopupMenu)((JMenuItem) e.getSource()).getParent()).getInvoker()).dispose();
			}
		});
	    by_train.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				by_Train(e);
			}
		});
	    by_station.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				by_Station(e);
			}
		});
	    booked_tickets.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				booked_Tickets(e);
				
			}
		});
	    JLabel byTrain = new JLabel(new ImageIcon("D:\\my programs\\Mini Project\\src\\GUI\\by_train.png"));byTrain.setBounds(100, 250, 219, 117);f.add(byTrain);
	    JLabel byStation = new JLabel(new ImageIcon("D:\\my programs\\Mini Project\\src\\GUI\\by_station.png"));byStation.setBounds(400, 250, 219, 117);f.add(byStation);
	    JLabel bookedTickets = new JLabel(new ImageIcon("D:\\my programs\\Mini Project\\src\\GUI\\booked_tickets.png"));bookedTickets.setBounds(100, 400, 219, 117);f.add(bookedTickets);
	    JLabel toolbarStat = new JLabel(new ImageIcon("D:\\my programs\\Mini Project\\src\\GUI\\toolbar_off.png"));toolbarStat.setBounds(400, 400, 219, 117);f.add(toolbarStat);
	    byTrain.addMouseListener(new MouseAdapter() {
	
			@Override
			public void mouseClicked(MouseEvent me) {
				// TODO Auto-generated method stub
				by_Train(new ActionEvent(me.getSource(), me.getID(), me.paramString()));
			}
		});
	    byStation.addMouseListener(new MouseAdapter() {
	    	
			@Override
			public void mouseClicked(MouseEvent me) {
				// TODO Auto-generated method stub
				by_Station(new ActionEvent(me.getSource(), me.getID(), me.paramString()));
			}
		});
	    bookedTickets.addMouseListener(new MouseAdapter() {
	    	
			@Override
			public void mouseClicked(MouseEvent me) {
				// TODO Auto-generated method stub
				booked_Tickets(new ActionEvent(me.getSource(), me.getID(), me.paramString()));
			}
		});
	    toolbarStat.addMouseListener(new MouseAdapter() {
	    	
			@Override
			public void mouseClicked(MouseEvent me) {
				// TODO Auto-generated method stub
				//toolbar.setVisible(true);
				if(tbstatus)
				{
					toolbarStat.setIcon(new ImageIcon("D:\\my programs\\Mini Project\\src\\GUI\\toolbar_off.png"));
					tbstatus = false;
				}
				else
				{
					toolbarStat.setIcon(new ImageIcon("D:\\my programs\\Mini Project\\src\\GUI\\toolbar_on.png"));
					tbstatus = true;
				}
				toolbar.setVisible(tbstatus);
				f.validate();
			}
		});
	    t_bg_color.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Color c = JColorChooser.showDialog(t, "Select a Color", Color.BLACK);
				f.getContentPane().setBackground(c);
				//System.out.println(c.toString());
				f.validate();
			}
		});
	    t_fg_color.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Color c = JColorChooser.showDialog(t, "Select a Color", Color.BLACK);
				f.getContentPane().setForeground(c);
				f.validate();
			}
		});
	    t_bg_image.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();

				int r = fc.showDialog(t, "Choose...");
				if(r==JFileChooser.APPROVE_OPTION) {
				Home.bg_image = new ImageIcon(fc.getSelectedFile().getAbsolutePath());
				Component list[] = removeComponents(f);
				f.setContentPane(new JLabel(Home.bg_image));
				addComponents(f, list);
				f.validate();
				}
			}
		});
	    cancel_ticket.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Database db = new Database();
				String tno =(String) JOptionPane.showInputDialog(null,"Enter the Ticket Number to Cancel","Cancel my Ticket",JOptionPane.QUESTION_MESSAGE);
				db.cancelTicket(tno);
			}
		});
	    
	    
	    f.setVisible(true);
		
	}
	public void by_Train(ActionEvent e)
	{
		JFrame f1 = new JFrame("Search Train");
		f1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    f1.setSize(1800, 800);
	    f1.setLayout(null);
	    f1.getContentPane().setBackground(bcolor);
	    f1.getContentPane().setForeground(fcolor);
	    f1.addMouseListener(new MouseAdapter() {
	    	public void mouseReleased(MouseEvent e)
	    	{
	    		if(e.isPopupTrigger())
	    		{
	    			pm.show(f1,e.getX(),e.getY());
	    		}
	    	}
		});
	    Database db = new Database();
	    JLabel l1=new JLabel("PNR No.: ");l1.setBounds(550,100, 150, 30);l1.setForeground(fcolor);f1.add(l1);
		try {
			pnr = new JComboBox<String>(db.getPNR());
			pnr.setBounds(650,100, 200, 30);f1.add(pnr);
			pnr.setFont(new Font("Serif", Font.BOLD, 16));pnr.setForeground(bcolor);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JButton getTrainInfo =new JButton("Get Train Info");getTrainInfo.setBounds(690,200,120,50);f1.add(getTrainInfo);
		getTrainInfo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String n = (String)pnr.getSelectedItem();
				if(t!=null)
				{
					f1.remove(t);
				}
				t= db.getTrainInfo(n);t.setBounds(150, 300, 1500, 200);
				if(t==null)
				{
					JOptionPane.showMessageDialog(null,"Sorry!!! No Trains Found","Oops!",JOptionPane.OK_CANCEL_OPTION,null);
				}
				f1.add(t);
				f1.validate();
			}
		});
		f1.setVisible(true);
	}
	public void by_Station(ActionEvent e)
	{
		JFrame f1 = new JFrame("Search Train");
		f1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    f1.setSize(1800, 800);
	    f1.setLayout(null);
	    f1.getContentPane().setBackground(Home.bcolor);
	    f1.getContentPane().setForeground(Color.GREEN);
	    f1.addMouseListener(new MouseAdapter() {
	    	public void mouseReleased(MouseEvent e)
	    	{
	    		if(e.isPopupTrigger())
	    		{
	    			if(e.isPopupTrigger())
		    		{
		    			pm.show(f1,e.getX(),e.getY());
		    		}
	    		}
	    	}
		});
	    Database db = new Database();
	    ImageIcon img = new ImageIcon("D:\\my programs\\Mini Project\\src\\GUI\\swap.png");
		JLabel l1=new JLabel("Source: ");l1.setBounds(200,50, 150, 50);l1.setForeground(fcolor);l1.setFont(new Font("Arial", Font.BOLD, 25));f1.add(l1);
		try {
			src = new JList<String>(db.getCities());
			src.setFixedCellHeight(30);
			src.setBackground(bcolor);src.setForeground(fcolor);src.setFont(new Font("Arial",Font.BOLD,16));src.setSelectionBackground(fcolor);src.setSelectionForeground(bcolor);
			src1 = new JScrollPane(src);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}src1.setBounds(250,100, 250, 150);f1.add(src1);
		JLabel l2=new JLabel("Destination: ");l2.setBounds(1000,50,150,50);l2.setForeground(fcolor);l2.setFont(new Font("Arial", Font.BOLD, 25));f1.add(l2);
		try {
			des = new JList<String>(db.getCities());
			des.setFixedCellHeight(30);
			des.setBackground(bcolor);des.setForeground(fcolor);des.setFont(new Font("Arial",Font.BOLD,16));des.setSelectionBackground(fcolor);des.setSelectionForeground(bcolor);
			des1 = new JScrollPane(des);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}des1.setBounds(1050,100, 250, 150);f1.add(des1);
		JButton swap =new JButton(img);swap.setBounds(750,115, 150, 50);f1.add(swap);
		swap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int s= (int) src.getSelectedIndex();
				src.setSelectedIndex(des.getSelectedIndex());
				des.setSelectedIndex(s);
			}
		});
		JButton getTrains =new JButton("Get Trains");getTrains.setBounds(750,250,150,75);f1.add(getTrains);
		getTrains.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String from = (String) src.getSelectedValue();
				String to = (String) des.getSelectedValue();
				if(from.contentEquals(to))
				{
					JOptionPane.showMessageDialog(f1, new JLabel("Error: Source and Destination are same!"), "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					if(t!=null)
					{
						f1.remove(t);
					}
					JScrollPane t= db.getTrains(from,to);
					if(t==null)
					{
						JOptionPane.showMessageDialog(null,"Sorry!!! No Trains Found","Oops!",JOptionPane.OK_CANCEL_OPTION,null);
					}
					else
					{
						t.setBounds(150, 400, 1500, 200);
						f1.add(t);
						f1.validate();
					}
				}
			}
		});
		f1.setVisible(true);
	}
	public void booked_Tickets(ActionEvent e)
	{
		Database db = new Database();
		JFrame f1 = new JFrame("My Tickets");
		f1.setSize(700,500);
		f1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f1.setLayout(new GridLayout(1,1));
		String tickets[] = db.getTicketNos();
		int ticket_count=tickets.length;
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Tickets");
		DefaultMutableTreeNode t[] = new DefaultMutableTreeNode[ticket_count];
		for(int i=0;i<ticket_count;i++)
		{
			t[i] = new DefaultMutableTreeNode(tickets[i]);root.add(t[i]);
			String[] a = db.getTicketDetails(tickets[i]);
			DefaultMutableTreeNode[] t1 = new DefaultMutableTreeNode[a.length];
			for(int j=0;j<a.length;j++)
			{
				t1[j]=new DefaultMutableTreeNode(a[j]);t[i].add(t1[j]);
			}
		}
		JTree tree = new JTree(root);
		f1.add(tree);
		
		f1.setVisible(true);
	}
	public static void main(String arg[]) throws NullPointerException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		new Home();
		//Database db = new Database();
		//db.showTicket("T0002", 600);
	}
	public Component[] removeComponents(JFrame f1)
	{
		Component list[] = new Component[f1.getContentPane().getComponentCount()];
		int i=0;
		for(Component c:f1.getContentPane().getComponents())
		{
			f1.remove(c);
			list[i] = c;
			i++;
		}
		return list;
	}
	public void addComponents(JFrame f1,Component[] list)
	{
		for(Component c:list)
		{
			f1.add(c);
		}
	}


}