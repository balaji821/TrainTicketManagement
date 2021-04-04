package GUI;
import javax.swing.*;

import GUI.Window;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import functions.*;
public class Window {
	public static JTable tb;
	public static JScrollPane t;
	public Window() throws SQLException,NullPointerException
	{
		JFrame f= new JFrame("IRCTC");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    f.setSize(screenSize.width, screenSize.height-100);
	    f.setLayout(null);
		Database db=new Database();
		String[] s = {"dg"};
		s=db.getCities();
		ImageIcon img = new ImageIcon("D:\\my programs\\Mini Project\\src\\GUI\\swap.jpg");
		JLabel l1=new JLabel("Source: ");l1.setBounds(750,100, 150, 30);f.add(l1);
		JComboBox<String> src = new JComboBox<String>(s);src.setBounds(850,100, 200, 30);f.add(src);
		JLabel l2=new JLabel("Destination: ");l2.setBounds(750,150,150,30);f.add(l2);
		JComboBox<String> des = new JComboBox<String>(s);des.setBounds(850,150, 200, 30);f.add(des);
		JButton swap =new JButton(img);swap.setBounds(1100,115, 50, 50);f.add(swap);
		swap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String s= (String) src.getSelectedItem();
				src.setSelectedItem(des.getSelectedItem());
				des.setSelectedItem(s);
			}
		});
		JLabel l3 =new JLabel("-------------- OR --------------");l3.setBounds(880,250,300,50);f.add(l3);
		JLabel l4=new JLabel("PNR No.: ");l4.setBounds(750,350, 150, 30);f.add(l4);
		JTextField pnr = new JTextField(10);pnr.setBounds(850,350, 200, 30);f.add(pnr);
		JButton getTrains =new JButton("Get Trains");getTrains.setBounds(900,200,100,50);f.add(getTrains);
		
		getTrains.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String from = (String) src.getSelectedItem();
				String to = (String) des.getSelectedItem();
				if((from.equals(""))||(to.equals("")))
				{
					JOptionPane.showMessageDialog(f, new JLabel("Error: No Input Given"), "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				else if(from.contentEquals(to))
				{
					JOptionPane.showMessageDialog(f, new JLabel("Error: Source and Destination are same!"), "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					JScrollPane t= db.getTrains(from,to);t.setBounds(200, 500, 1500, 200);
					if(t!=null)
					{
						f.remove(t);
					}
					f.add(t);
					f.validate();
				}
				
			}
		});
		JButton getTrainInfo =new JButton("Get Train Info");getTrainInfo.setBounds(890,400,120,50);f.add(getTrainInfo);
		getTrainInfo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String n = pnr.getText();
				if(n.equals(""))
				{
					JOptionPane.showMessageDialog(f, new JLabel("Error: No Input Given"), "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					if(t!=null)
					{
						f.remove(t);
					}
					t= db.getTrainInfo(n);t.setBounds(200, 500, 1500, 200);
					f.add(t);
					f.validate();
				}
				
			}
		});
		
		f.setVisible(true);
		
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
	public static void main(String arg[]) throws SQLException,NullPointerException
	{
		new Window();
	}

}
