import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;


public class LoginDialog extends JDialog implements ActionListener
{
	JTextField userNameTextField;
	JPasswordField passwordTextField;
	String userName,password;
	
	public LoginDialog()
	{
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e){}
		userNameTextField=new JTextField();
		passwordTextField=new JPasswordField();
		
		this.setLayout(new BorderLayout(20, 20));
		this.setSize(500,150);
		JPanel leftPanel=new JPanel(new GridLayout(2,1,10,10));
		leftPanel.add(new JLabel("UserName : "));
		leftPanel.add(new JLabel("Password : "));
		
		JPanel rightPanel=new JPanel(new GridLayout(2,1,10,10));
		rightPanel.add(userNameTextField);
		rightPanel.add(passwordTextField);
		
		JPanel parentPanel=new JPanel(new BorderLayout(20,20));
		parentPanel.add(leftPanel,BorderLayout.WEST);
		parentPanel.add(rightPanel,BorderLayout.CENTER);
		JButton submitButton=new JButton("Submit");
		submitButton.addActionListener(this);
		parentPanel.add(submitButton,BorderLayout.SOUTH);
		this.add(parentPanel,BorderLayout.CENTER);
		
	}
	public void actionPerformed(ActionEvent ae)
	{
		userName=userNameTextField.getText();
		password=new String(passwordTextField.getPassword());
		System.out.println("submit of login frame clicked");
		this.dispose();
	}
}
