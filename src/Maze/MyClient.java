package Maze;

import java.io.*;
import java.net.*;

public class MyClient implements Runnable 
{
	
	Socket clientSocket = null;
	DataInputStream disFromClient = null;
	DataOutputStream dosToServer = null;
	DataInputStream disFromServer = null;

	public void run() 
	{
		try 
		{
			String toServerMessage = "";
			String fromServerMessage = "";

			clientSocket = new Socket("localhost", 9001);
			disFromClient = new DataInputStream(System.in);
			dosToServer = new DataOutputStream(clientSocket.getOutputStream());
			disFromServer = new DataInputStream(clientSocket.getInputStream());

			fromServerMessage = disFromServer.readUTF(); // read initiate
															// message from
															// server
			System.out.println("Server: " + fromServerMessage);

			fromServerMessage = disFromServer.readUTF(); // read last message
															// from server
			System.out.println("Server: " + fromServerMessage);
		

		} 
		catch (SocketException e) 
		{
			System.out.println("Connection closed by the server...!!!");
		} 
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			System.out.println("The Server Closed the Connection,Please try again when Server is available..!!!");
		}
	}

	public static void main(String[] args) throws IOException 
	{

		Runnable client = new MyClient();
		Thread clientThread = new Thread(client);
		clientThread.start();
	}
}
