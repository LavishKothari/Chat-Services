import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class ClientFrame extends JFrame implements ActionListener,KeyListener
{
	JButton connectServerButton;
	JButton disconnectServerButton;
	JTextArea roomChatTextArea;
	JTextField chatTextField;
	Socket server;
	boolean isClientAuthenticated=false;
	DataInputStream dis;
	DataOutputStream dos;
	public ClientFrame()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		/*
		 * The following line maximizes this window.
		 */
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		chatTextField=new JTextField();
		connectServerButton=new JButton("Connect to Server");		
		disconnectServerButton=new JButton("Disconnect from Server");

		connectServerButton.addActionListener(this);
		disconnectServerButton.addActionListener(this);
		
		disconnectServerButton.setEnabled(false);
		
		this.setLayout(new BorderLayout());
		
		JPanel parentPanel=new JPanel();
		parentPanel.setLayout(new BorderLayout(10,10));
		JPanel childPanel=new JPanel(new GridLayout(1, 2, 10, 10));
		childPanel.add(connectServerButton);
		childPanel.add(disconnectServerButton);
		parentPanel.add(childPanel,BorderLayout.NORTH);
		chatTextField.addKeyListener(this);
		this.add(parentPanel,BorderLayout.NORTH);
		roomChatTextArea=new JTextArea();
		roomChatTextArea.setEnabled(false);
		JScrollPane jsp=new JScrollPane(roomChatTextArea);
		this.add(jsp,BorderLayout.CENTER);
		this.add(chatTextField,BorderLayout.SOUTH);

		/*
		JScrollBar vertical = jsp.getVerticalScrollBar();
		vertical.setValue( vertical.getMaximum() );
		*/

		//this.add(roomChatTextArea);
	}
	public void actionPerformed(ActionEvent ae)
	{
		//server=null;
		if(ae.getSource()==connectServerButton)
		{
				System.out.println("connect Server button clicked");
				connectServerButton.setEnabled(false);
				disconnectServerButton.setEnabled(true);
				/*
				 * make a login frame here asking to the user for login name and password.
				 */
				LoginDialog loginDialog=new LoginDialog();
				loginDialog.setVisible(true);
				
				if(loginDialog.password!=null && loginDialog.userName!=null)
				{
					try 
					{
						server=new Socket("localhost",9876);
						dis=new DataInputStream(server.getInputStream());
						dos=new DataOutputStream(server.getOutputStream());
						dos.writeUTF(loginDialog.userName);
						dos.writeUTF(loginDialog.password);
						String serverResponse=dis.readUTF();
						if(serverResponse.equals("USER VALIDATED"))
						{
							JOptionPane.showMessageDialog(this, "User authenticated successfully");
							this.setTitle("Welcome "+loginDialog.userName);
							roomChatTextArea.setText(""+dis.readUTF());
							
							isClientAuthenticated=true;
							new ClientListeningThread(dis,roomChatTextArea);
							//System.out.println("hello lavish kothari");
						}
						else if(serverResponse.equals("INVALID PASSWORD"))
						{
							JOptionPane.showMessageDialog(this, "you entered invalid password try again");
						}
						else
						{
							JOptionPane.showMessageDialog(this, "this username doesnot exists");
						}
						
					} 
					catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//System.out.println("Execution flowed here");
		}
		else if(ae.getSource()==disconnectServerButton)
		{
			try
			{
				dos.writeUTF("CLIENT_DISCONNECTING");
				server.close();
				connectServerButton.setEnabled(true);
				disconnectServerButton.setEnabled(false);
				this.setTitle("");
				roomChatTextArea.setText("");
			}
			catch(UnknownHostException e)
			{
				e.printStackTrace();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent ke) {
		// TODO Auto-generated method stub
		if(ke.getKeyChar()=='\n')
		{
			//JOptionPane.showMessageDialog(this, ""+chatTextField.getText());
			try {
				dos.writeUTF(chatTextField.getText());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			chatTextField.setText("");
		}
		
	}
}
class ClientListeningThread extends Thread
{	
	DataInputStream dis;
	JTextArea roomChatTextArea;
	public ClientListeningThread(DataInputStream dis,JTextArea roomChatTextArea) throws IOException
	{
		this.roomChatTextArea=roomChatTextArea;
		this.dis=dis;
		this.start();
	}
	public void run()
	{
		while(true)
		{
			try 
			{
				String currentString=dis.readUTF();
				roomChatTextArea.setText(roomChatTextArea.getText()+"\n"+currentString);
				roomChatTextArea.setCaretPosition(roomChatTextArea.getDocument().getLength());
			}
			catch (Exception e) 
			{
				//e.printStackTrace();
				break;
			}
		}
	}
}
class Main
{
	public static void main(String []a)
	{
		ClientFrame f=new ClientFrame();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}