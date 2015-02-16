package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

public class Server 
{
	LinkedList<Socket>clientLinkedList; 
	ServerSocket serverSocket;
	Server()
	{
		clientLinkedList=new LinkedList<Socket>();
	}
	public void startServer() throws IOException,UnknownHostException
	{
		serverSocket = new ServerSocket(9876);
		while(true)
		{
			Socket client=serverSocket.accept();
			clientLinkedList.add(client);
			new ServerThread(client);
		}
	}
}
