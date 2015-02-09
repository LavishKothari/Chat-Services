import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;


public class ServerThread extends Thread
{
	Socket client;
	DataInputStream dis;
	DataOutputStream dos;
	
	public ServerThread(Socket client,DataInputStream dis,DataOutputStream dos)
	{
		this.dis=dis;
		this.dos=dos;
		this.client=client;
		this.start();
	}
	public void run()
	{
		try 
		{
			String userName=dis.readUTF();
			String password=dis.readUTF();
			
			File userFile=new File("users.txt");
			BufferedReader br=new BufferedReader(new FileReader(userFile));
			String line=null;
			String serverUserName=null,serverPassword=null;
			while((line=br.readLine())!=null)
			{
				StringTokenizer stz=new StringTokenizer(line);
				serverUserName=stz.nextToken();
				serverPassword=stz.nextToken();
				if(userName.equals(serverUserName) && password.equals(serverPassword))
				{
					dos.writeUTF("USER VALIDATED");
					BufferedReader fbr=new BufferedReader(new FileReader(new File("chat.txt")));
					String chatString=new String("");
					String tempString=new String("");
					
					while((tempString=fbr.readLine())!=null)
						chatString+=tempString+"\n";
					fbr.close();
					dos.writeUTF(chatString);
					break;
				}
				else if(userName.equals(serverUserName))
				{
					dos.writeUTF("INVALID PASSWORD");
					break;
				}
			}
			if(line==null)
			{
				dos.writeUTF("USER DONT EXISTS");
			}
			else if(password.equals(serverPassword))
			{
				/*
				 * this means that the user is validated and now you need to accept all the requests from the user
				 */
				while(true)
				{
					String currentString=new String("");
					try
					{
						currentString=(dis.readUTF());
						/*
						if(currentString.equals("CLIENT_DISCONNECTING"))
						{
							ServerFrame.clientLinkedList.remove(client);
							break;
						}
						*/
					}
					catch(Exception e)
					{
						/*
						 * means the client has disconnected.
						 */
						e.printStackTrace();
						break;
					}
					FileWriter fw=new FileWriter("chat.txt",true); // opening the file in appending mode.
					ServerFrame.sendToAllClients("["+userName+"] : "+currentString);
					fw.write("\n"+"["+userName+"] : "+currentString);
					fw.close();
					
				}
			}
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
