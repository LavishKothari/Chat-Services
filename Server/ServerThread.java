package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Thread
{
	Socket client;
	DataInputStream dis;
	DataOutputStream dos;
	ServerThread(Socket client)
	{
		this.client=client;
	}
	public void run()
	{
		try 
		{
			dis=new DataInputStream(client.getInputStream());
			dos=new DataOutputStream(client.getOutputStream());
			
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}
}
