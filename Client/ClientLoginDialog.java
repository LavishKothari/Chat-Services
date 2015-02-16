package Client;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ClientLoginDialog extends Dialog implements ActionListener
{
	JTextField userNameTextField,ipTextField,portNumberTextField;
	JPasswordField passwordTextField;
	JLabel userNameLabel,ipLabel,portNumberLabel,passwordLabel;
	JButton submitButton,clearAllButton,cancelButton;
	
	public ClientLoginDialog(ClientFrame cf)
	{
		super(cf,true);
		setTitle("Login");
		setSize(600,200);
		userNameTextField=new JTextField();
		ipTextField=new JTextField("127.0.0.1");
		portNumberTextField=new JTextField("9876");
		passwordTextField=new JPasswordField();
		
		userNameLabel=new JLabel("User Name");
		ipLabel=new JLabel("IP Address");
		portNumberLabel=new JLabel("Port Number");
		passwordLabel=new JLabel("Password");
		
		submitButton=new JButton("Submit");
		clearAllButton=new JButton("Clear All");
		cancelButton=new JButton("Cancel");
		
		
		JPanel parentPanel=new JPanel(new BorderLayout(10,10));
		
		JPanel mainPanel=new JPanel(new BorderLayout(10,10));
		JPanel leftPanel=new JPanel(new GridLayout(4,1,10,10));
		JPanel rightPanel=new JPanel(new GridLayout(4,1,10,10));
		
		leftPanel.add(ipLabel);
		leftPanel.add(portNumberLabel);
		leftPanel.add(userNameLabel);
		leftPanel.add(passwordLabel);
		
		rightPanel.add(ipTextField);
		rightPanel.add(portNumberTextField);
		rightPanel.add(userNameTextField);
		rightPanel.add(passwordTextField);
		
		mainPanel.add(leftPanel,BorderLayout.WEST);
		mainPanel.add(rightPanel);
		
		JPanel buttonPanel=new JPanel(new GridLayout(1,5,10,10));
		buttonPanel.add(submitButton);
		buttonPanel.add(new JLabel());
		buttonPanel.add(clearAllButton);
		buttonPanel.add(new JLabel());
		buttonPanel.add(cancelButton);
		
		parentPanel.add(mainPanel);
		parentPanel.add(buttonPanel,BorderLayout.SOUTH);
		this.add(parentPanel);
		
		addWindowListener(new DlgAdapter(this));
	
		submitButton.addActionListener(this);
		cancelButton.addActionListener(this);
		clearAllButton.addActionListener(this);
		
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==clearAllButton)
		{
			userNameTextField.setText("");
			passwordTextField.setText("");
			ipTextField.setText("");
			portNumberTextField.setText("");
		}
		else if(ae.getSource()==submitButton)
		{
			if(userNameTextField.getText().equals("") || ipTextField.getText().equals("") || passwordTextField.getText().equals("") || portNumberTextField.getText().equals("") )
			{
				JOptionPane.showMessageDialog(this, "You can't leave a Text Field blank...!!!");
			}
			else
			{
				
			}
		}
		else if(ae.getSource()==cancelButton)
		{
			this.dispose();
		}
		
	}
}


class DlgAdapter extends WindowAdapter
{
	Dialog d;
	DlgAdapter(Dialog d)
	{
		this.d=d;
	}
	public void windowClosing(WindowEvent we)
	{
		d.dispose();
	}
}