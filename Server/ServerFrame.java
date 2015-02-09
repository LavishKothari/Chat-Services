
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class ServerFrame extends JFrame implements ActionListener
{
	JTextArea logTextArea;
	JButton startServerButton;
	static LinkedList<Socket> clientLinkedList;
	public ServerFrame()
	{
		clientLinkedList=new LinkedList<Socket>();
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
		
		this.setLayout(new BorderLayout());
		JPanel parentPanel=new JPanel();
		parentPanel.setLayout(new BorderLayout());
		
		startServerButton=new JButton("Start the Server");
		parentPanel.add(startServerButton,BorderLayout.NORTH);
		this.add(parentPanel,BorderLayout.CENTER);
		startServerButton.addActionListener(this);
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==startServerButton)
		{
			try
			{
				ServerSocket ss=new ServerSocket(9876);
				JOptionPane.showMessageDialog(this, "Server Started Successfully");
				startServerButton.setEnabled(false);
				while(true)
				{	
					Socket client=ss.accept();
					clientLinkedList.add(client);
					DataInputStream dis=new DataInputStream(client.getInputStream());
					DataOutputStream dos=new DataOutputStream(client.getOutputStream());
					
					new ServerThread(client,dis,dos);
				}
			}
			catch(Exception e)
			{
			
			}
		}
	}
	public static void sendToAllClients(String str) throws IOException
	{
		for(int i=0;i<clientLinkedList.size();i++)
		{
			DataOutputStream dos=new DataOutputStream((clientLinkedList.get(i)).getOutputStream());
			dos.writeUTF(str);
			dos.close();
		}
	}
}

class Main
{
	public static void main(String []a)
	{
		ServerFrame sf=new ServerFrame();
		sf.setVisible(true);
		sf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
