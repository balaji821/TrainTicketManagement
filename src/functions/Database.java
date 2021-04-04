package functions;

import java.awt.Font;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import GUI.Home;
import functions.JCombo;

public class Database {
	static String ticketNumber="T0004";
	static int temp=0;
	String[] cities;
	String[] pnr;
	String[] tickets;
	String[] passenger_names;
	String[] aadhar_ids;
	public JScrollPane getTrains(String from, String to)
	{
		JTable t = new JTable();
		DefaultTableModel model=new DefaultTableModel();
		Object[] h = {"SNo.","PNR No.","TRAIN NAME","SOURCE","DEPARTURE TIME","DESTINATION","ARRIVAL TIME","FARE","AVAILABLE TICKETS","DISTANCE","DURATION"};
		model.setColumnIdentifiers(h);
		t.setModel(model);
		t.setRowHeight(50);
		t.setCellSelectionEnabled(false);
		t.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				int r=t.getSelectedRow();
				String s=(String) Home.tb.getValueAt(r,8);
				String pnr = (String)Home.tb.getValueAt(r, 1);
				int availableTickets = Integer.parseInt(s.split("/")[0]);
				Object[] arr= generate_array(availableTickets);
				int count =(int) JOptionPane.showInputDialog(null,"Enter the number of tickets to book","Ticket Count",JOptionPane.QUESTION_MESSAGE,null,arr,arr[0]);
				book(pnr,count);
			}
		});
		try
		{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/trainticketmanagement","root","");
			Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs= stmt.executeQuery("select * from trains where source='"+from+"' and destination='"+to+"';");
			
			rs.last();
			if(rs.getRow() == 0)
			{
				return null;
			}
			rs.beforeFirst();
			int i=1;
			while(rs.next())
			{
				if(Integer.parseInt(rs.getString(7))>0) {
				Object[] obj = {i,rs.getString(1), 
					rs.getString(2),
					rs.getString(3),
					rs.getString(10),
					rs.getString(4),
					rs.getString(11),
					rs.getString(5),
					rs.getString(7)+"/"+rs.getString(6),
					rs.getString(8),
					rs.getString(9)};
					model.addRow(obj);
					i+=1;}
			}
			stmt.close();
			con.close();
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		TableColumnModel cm = t.getColumnModel();
		for(int i=0;i<t.getColumnCount();i++)
		{
			cm.getColumn(i).setWidth(200);
		}
		Home.tb=t;
		JScrollPane p = new JScrollPane(t);
		return p;
	}
	public JScrollPane getTrainInfo(String n)
	{
		JTable t = new JTable();
		DefaultTableModel model=new DefaultTableModel();
		Object[] h = {"SNo.","PNR No.","TRAIN NAME","SOURCE","DEPARTURE TIME","DESTINATION","ARRIVAL TIME","FARE","AVAILABLE TICKETS","DISTANCE","DURATION"};
		model.setColumnIdentifiers(h);
		t.setModel(model);
		t.setRowHeight(50);
		t.setColumnSelectionAllowed(false);
		t.setRowSelectionAllowed(true);
		t.setCellSelectionEnabled(false);
		try
		{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/trainticketmanagement","root","");
			Statement stmt=con.createStatement();
			ResultSet rs= stmt.executeQuery("select * from trains where pnr='"+n+"';");
			rs.next();
			Object[] obj = {1,rs.getString(1), 
				rs.getString(2),
				rs.getString(3),
				rs.getString(10),
				rs.getString(4),
				rs.getString(11),
				rs.getString(5),
				rs.getString(7)+"/"+rs.getString(6),
				rs.getString(8),
				rs.getString(9)};
			model.addRow(obj);
			stmt.close();
			con.close();
		}
		catch(SQLException ex)
		{
			System.out.println("Error"+ex);
		}
		TableColumnModel cm = t.getColumnModel();
		for(int i=0;i<t.getColumnCount();i++)
		{
			cm.getColumn(i).setWidth(200);
		}
		t.getSelectionModel().setValueIsAdjusting(true);
		t.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				int r=0;
				String s=(String) t.getValueAt(r,8);
				String pnr = (String)t.getValueAt(r, 1);
				int availableTickets = Integer.parseInt(s.split("/")[0]);
				Object[] arr= generate_array(availableTickets);
				int count =(int) JOptionPane.showInputDialog(null,"Enter the number of tickets to book","Ticket Count",JOptionPane.QUESTION_MESSAGE,null,arr,arr[0]);
				book(pnr,count);
			}
		});
		Home.tb = t;
		JScrollPane p = new JScrollPane(t);
		return p;
	}
	public void book(String pnr,int n)
	{
		JFrame f2 = new JFrame("Book Ticket");
		f2.setSize(1000,700);
		f2.setLayout(null);
		f2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f2.getContentPane().setBackground(Home.bcolor);
		f2.getContentPane().setForeground(Home.fcolor);
		f2.getContentPane().setFont(new Font("Arial", Font.BOLD, 15));
		JTabbedPane tb = new JTabbedPane();tb.setBounds(50, 50, 1000, 700);
		tb.setBackground(Home.bcolor);
		JPanel[] p= new JPanel[n];
		JLabel[] l1 = new JLabel[n];
		JTextField[] name1 = new JTextField[n];
		JLabel[] l2 = new JLabel[n];
		JSpinner[] age1 = new JSpinner[n];
		JLabel[] l3 = new JLabel[n];
		ButtonGroup[] gender1 = new ButtonGroup[n];
		JRadioButton[] m = new JRadioButton[n];
		JRadioButton[] f = new JRadioButton[n];
		JRadioButton[] o = new JRadioButton[n];
		JLabel[] l4 = new JLabel[n];
		JCombo type1[] = new JCombo[n];
		JLabel[] l5 = new JLabel[n];
		JTextField[] aadhar1 = new JTextField[n];
		for(int i=0;i<n;i++)
		{
			String[] types= {"2A","3A","FC","SL"};
			p[i] =new JPanel(null);p[i].setBounds(0, 0, 1000, 700);
			l5[i] = new JLabel("Aadhar No :");l5[i].setBounds(150, 50, 100, 30);p[i].add(l5[i]);
			aadhar1[i] = new JTextField();aadhar1[i].setBounds(250,50,100,30);p[i].add(aadhar1[i]);
			aadhar1[i].addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					char key = e.getKeyChar();
					String s=((JTextField) e.getSource()).getText();
					int n=s.length();
					if ( (!Character.isDigit(key))||(n>=14))
					{
						e.setKeyChar('\0');
					}
					else
					{
						if((n==4)||(n==9))
						{
							((JTextField) e.getSource()).setText(s+"-");
						}
					}
					
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			l1[i] = new JLabel("Name :");l1[i].setBounds(150, 150, 100, 30);p[i].add(l1[i]);
			name1[i] = new JTextField();name1[i].setBounds(250,150,100,30);p[i].add(name1[i]);
			l2[i] = new JLabel("Age :");l2[i].setBounds(150, 250, 100, 30);p[i].add(l2[i]);
			age1[i] = new JSpinner(new SpinnerNumberModel(1,1,100,1));age1[i].setBounds(250, 250, 100, 30);p[i].add(age1[i]);
			l3[i] = new JLabel("Gender :");l3[i].setBounds(150, 350, 100, 30);p[i].add(l3[i]);
			gender1[i] = new ButtonGroup();
			m[i] = new JRadioButton("Male");m[i].setBounds(250, 350, 100, 30);p[i].add(m[i]);gender1[i].add(m[i]);m[i].addActionListener(null);m[i].setActionCommand("Male");
			f[i] = new JRadioButton("Female");f[i].setBounds(250, 380, 100, 30);p[i].add(f[i]);gender1[i].add(f[i]);f[i].addActionListener(null);f[i].setActionCommand("Female");
			o[i] = new JRadioButton("Others");o[i].setBounds(250, 410, 100, 30);p[i].add(o[i]);gender1[i].add(o[i]);o[i].addActionListener(null);o[i].setActionCommand("Others");
			l4[i] = new JLabel("Class : ");l4[i].setBounds(150, 450, 100, 30);p[i].add(l4[i]);
			type1[i] = new JCombo(types);type1[i].cb.setBounds(250, 450, 100, 30);p[i].add(type1[i].cb);
			tb.add("Passenger "+(i+1),p[i]);
		}
		JButton done = new JButton("Done");done.setBounds(750,30,100,30);f2.add(done);
		done.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String gender,type,name,aadhar;
				int age;
				Connection con;
				Statement stmt;
				try 
				{
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trainticketmanagement","root","");
					stmt=con.createStatement();
					ResultSet rs= stmt.executeQuery("select fare from trains where pnr = '"+pnr+"';");
					rs.next();
					float fare = Float.parseFloat(rs.getString(1))*n;
					Database.ticketNumber ="T000"+(Integer.parseInt(Database.ticketNumber.substring(1))+1)+"";
					stmt.executeUpdate("insert into tickets values('"+Database.ticketNumber+"','"+pnr+"',"+n+","+fare+" );");
					for(int i=0;i<n;i++)
					{
						aadhar = aadhar1[i].getText();
						name = name1[i].getText();
						age = (int)age1[i].getValue();
						gender = gender1[i].getSelection().getActionCommand();
						type = (String) type1[i].cb.getSelectedItem();
						stmt.executeUpdate("insert into booked values('"+Database.ticketNumber+"','"+aadhar+"','"+name+"','"+age+"','"+gender+"','"+type+"' );");
					}
					rs= stmt.executeQuery("select availableticketcount from trains where pnr = '"+pnr+"';");
					rs.next();
					int avail = Integer.parseInt(rs.getString(1));
					stmt.executeUpdate("update trains set availableticketcount = "+(avail-n)+";");
					showTicket(ticketNumber,fare);
					stmt.close();
					con.close();
					f2.dispose();
				}
				catch(SQLException ex)
				{
					ex.printStackTrace();
				}
			}
		});
		f2.add(tb);
		f2.setVisible(true);
	}
	public String[] getCities() throws SQLException
	{
		try {
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trainticketmanagement","root","");
		Statement stmt=con.createStatement();
		ResultSet rs = stmt.executeQuery("select count(*) from cities;" );
		rs.next();
		int city_size = rs.getInt(1);
		String s;
		cities = new String[city_size];
		rs = stmt.executeQuery("select * from cities;" );
		int i=0;
		while(rs.next())
		{
			s= rs.getString(1)+" ("+rs.getString(2)+")";
			cities[i]=s;
			i++;
		}
		}catch (Exception e) {
			System.out.println("Error: "+e);
		}
		return sort(cities);
	}
	public String[] getPNR() throws SQLException
	{
		try {
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trainticketmanagement","root","");
		Statement stmt=con.createStatement();
		ResultSet rs = stmt.executeQuery("select count(*) from trains;" );
		rs.next();
		int city_size = rs.getInt(1);
		pnr = new String[city_size];
		rs = stmt.executeQuery("select pnr from trains;" );
		int i=0;
		while(rs.next())
		{
			pnr[i]=rs.getString(1);
			i++;
		}
		}catch (Exception e) {
			System.out.println("Error: "+e);
		}
		return pnr;
	}
	public void showTicket(String ticketNumber,float fare)
	{
		JFrame f2 = new JFrame("Ticket Booked!");
		f2.setSize(1000,700);
		f2.setLayout(null);
		f2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f2.setBackground(Home.bcolor);
		f2.setForeground(Home.fcolor);
		f2.setFont(new Font("Arial", Font.BOLD, 15));
		JLabel tno = new JLabel("Ticket Number: "+ticketNumber);tno.setFont(new Font("Arial", Font.BOLD, 25));tno.setBounds(300,10,1000,30);f2.add(tno);
		JTabbedPane tb = new JTabbedPane();tb.setBounds(50, 50, 1000, 700);
		int count;
		try
		{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trainticketmanagement","root","");
			Statement stmt=con.createStatement();
			ResultSet rs = stmt.executeQuery("select count from tickets where ticket_no='"+ticketNumber+"';");
			rs.next();
			count=Integer.parseInt(rs.getString(1));
			JPanel[] p= new JPanel[count];
			JLabel[] l1 = new JLabel[count];
			JLabel[] name1 = new JLabel[count];
			JLabel[] l2 = new JLabel[count];
			JLabel[] age1 = new JLabel[count];
			JLabel[] l3 = new JLabel[count];
			JLabel[] gender1 = new JLabel[count];
			JLabel[] l4 = new JLabel[count];
			JLabel type1[] = new JLabel[count];
			JLabel[] l5 = new JLabel[count];
			JLabel[] aadhar1 = new JLabel[count];
			rs = stmt.executeQuery("select * from booked where ticket_no='"+ticketNumber+"';");
			for(int i=0;i<count;i++)
			{
				rs.next();
				p[i] =new JPanel(null);p[i].setBounds(0, 0, 1000, 700);
				l5[i] = new JLabel("Aadhar No :");l5[i].setBounds(150, 50, 100, 30);p[i].add(l5[i]);
				aadhar1[i] = new JLabel(rs.getString(2));aadhar1[i].setBounds(250,50,100,30);p[i].add(aadhar1[i]);
				l1[i] = new JLabel("Name :");l1[i].setBounds(150, 150, 100, 30);p[i].add(l1[i]);
				name1[i] = new JLabel(rs.getString(3));name1[i].setBounds(250,150,100,30);p[i].add(name1[i]);
				l2[i] = new JLabel("Age :");l2[i].setBounds(150, 250, 100, 30);p[i].add(l2[i]);
				age1[i] = new JLabel(rs.getString(4));age1[i].setBounds(250, 250, 100, 30);p[i].add(age1[i]);
				l3[i] = new JLabel("Gender :");l3[i].setBounds(150, 350, 100, 30);p[i].add(l3[i]);
				gender1[i] = new JLabel(rs.getString(5));gender1[i].setBounds(250, 350, 100, 30);p[i].add(gender1[i]);
				l4[i] = new JLabel("Class : ");l4[i].setBounds(150, 450, 100, 30);p[i].add(l4[i]);
				type1[i] = new JLabel(rs.getString(6));type1[i].setBounds(250, 450, 100, 30);p[i].add(type1[i]);
				tb.add("Passenger "+(i+1),p[i]);
			}
			f2.add(tb);
			JLabel cost = new JLabel("Total Fare : "+fare);cost.setBounds(750,30,300,30);f2.add(cost);
			f2.setVisible(true);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	public String[] getTicketNos()
	{
		try
		{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trainticketmanagement","root","");
			Statement stmt=con.createStatement();
			ResultSet rs = stmt.executeQuery("select count(*) from tickets");
			rs.next();
			int n = Integer.parseInt(rs.getString(1));
			tickets = new String[n];
			int i=0;
			rs = stmt.executeQuery("select ticket_no from tickets");
			while(rs.next())
			{
				tickets[i]= rs.getString(1);
				i++;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		tickets = sort(tickets);
		return tickets;
	}
	public String[] getNames(int ticket_no)
	{
		try
		{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trainticketmanagement","root","");
			Statement stmt=con.createStatement();
			ResultSet rs = stmt.executeQuery("select name from booked where ticket_no = '"+ticket_no+"');");
			int i=0;
			while(rs.next())
			{
				passenger_names[i]=rs.getString(1);
				i++;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return passenger_names;
	}
	public String[] getTicketDetails(String ticket_no)
	{
		try
		{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trainticketmanagement","root","");
			Statement stmt=con.createStatement();
			ResultSet rs = stmt.executeQuery("select count from tickets where ticket_no = '"+ticket_no+"';");
			rs.next();
			int n = Integer.parseInt(rs.getString(1));
			aadhar_ids = new String[n];
			int i=0;
			rs = stmt.executeQuery("select aadhar_no from booked where ticket_no = '"+ticket_no+"';");
			while(rs.next())
			{
				aadhar_ids[i]= rs.getString(1);
				i++;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		aadhar_ids=sort(aadhar_ids);
		return aadhar_ids;
	}
	public String[] sort(String[] s)
	{
		String t;
		for (int i = 0; i < s.length-1; i++) 
        {
            for (int j = i + 1; j < s.length; j++) { 
                if (s[i].compareTo(s[j])>0) 
                {
                    t = s[i];
                    s[i] = s[j];
                    s[j] = t;
                }
            }
        }
		return s;
	}
	public static Object[] generate_array(int end)
	{
		if(end>10)
		{
			end=10;
		}
		Object arr[] = new Object[end];
		for(int i=0;i<end;i++)
		{
			arr[i]=i+1;
		}
		return arr;
	}
	public void cancelTicket(String tno)
	{
		try
		{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trainticketmanagement","root","");
			Statement stmt=con.createStatement();
			int r = stmt.executeUpdate("delete from tickets where ticket_no = '"+tno+"';");
			if(r!=1)
			{
				JOptionPane.showMessageDialog(null,"Sorry!!! Ticket not Found","Oops!",JOptionPane.OK_CANCEL_OPTION,null);
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Ticket Cancelled","Ticket Cancellation",JOptionPane.OK_CANCEL_OPTION,null);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

}
