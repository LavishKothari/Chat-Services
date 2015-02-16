package Server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ServerFrame extends JFrame implements ActionListener
{
	JButton startServer;
	ServerFrame()
	{
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		startServer=new JButton("Start Server");
		JPanel parentPanel=new JPanel(new BorderLayout(10,10));
		parentPanel.add(startServer,BorderLayout.NORTH);
		
		startServer.addActionListener(this);
		this.add(parentPanel);
	}
	
	public void actionPerformed(ActionEvent ae) 
	{
		if(ae.getSource()==startServer)
		{
			Server server=new Server();
			try 
			{
				server.startServer();
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(this, "Server Started Successfully");
		}
	}
}

class Main
{
	public static void main(String args[])
	{
		ServerFrame sf=new ServerFrame();
		sf.setVisible(true);
		sf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
