package part2;

import java.net.*;
import java.io.*;

public class TCPServer {
	public static void main(String args[]) throws IOException{
		TCPServer tcpServer=new TCPServer();
		tcpServer.run();
	}
	
	public void run() throws IOException
	{
		//Initialising and defining the server socket
		ServerSocket serverSocket=new ServerSocket(8777);
		System.out.println("Waiting for the packets from TCP client:");
		//Accepting the client connection and defining input and output buffer
		Socket sock=serverSocket.accept();
		BufferedReader inputSenderBuffer=new BufferedReader(new InputStreamReader(sock.getInputStream()));
		PrintStream outputToSenderBuffer = new PrintStream(sock.getOutputStream());
		outputToSenderBuffer.flush();
        
		char[] inputArray=new char[2000];
		int i, c=1;
		
		//Repeating the process for 1000 times
		//Change the value from 1000 to number of times process needs to be repeated
		while(c<=1000)
		{
			//Reading input from the sender,saving it in an array and sending response back
			String inputSender=inputSenderBuffer.readLine();
			for(i=0;i<inputSender.length();i++)
			{				
				inputArray[i]=inputSender.charAt(i);
			}
			outputToSenderBuffer.println(inputArray);
			System.out.println("Received from the client:"+inputSender);
			c=c+1;
		}
	}
}
