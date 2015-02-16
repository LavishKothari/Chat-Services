package Client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClientFrame extends JFrame implements ActionListener
{	
	JButton connectButton,disconnectButton;
	
	public ClientFrame() 
	{
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		connectButton=new JButton("Connect to Server");
		disconnectButton=new JButton("Disconnect from Server");
		JPanel parentPanel=new JPanel(new BorderLayout(10,10));
		JPanel buttonPanel=new JPanel(new GridLayout(1,2,10,10));
		buttonPanel.add(connectButton);
		buttonPanel.add(disconnectButton);
		parentPanel.add(buttonPanel,BorderLayout.NORTH);
		this.add(parentPanel);
		
		connectButton.addActionListener(this);
		disconnectButton.addActionListener(this);
		
	}
	
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==connectButton)
		{
			ClientLoginDialog cd=new ClientLoginDialog(this);
			cd.setVisible(true);
			//JOptionPane.showMessageDialog(this, "Connected...!!!");
		}
		else if(ae.getSource()==disconnectButton)
		{
			JOptionPane.showMessageDialog(this, "Disconnected...!!!");
		}
	}
}


class Main
{
	public static void main(String args[])
	{
		ClientFrame cf=new ClientFrame();
		cf.setVisible(true);
		cf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}