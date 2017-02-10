package part2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
	@SuppressWarnings("resource")
	public static void main(String args[]) throws Exception
	{
		DatagramSocket serverSocket = new DatagramSocket(8777);
		System.out.println("Waiting for the packets:");
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		
		while(true)
		{
			//Initialising the packet object to store the received packet and printing it
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			String sentence = new String( receivePacket.getData());
			System.out.println("Received data: " + sentence);
			//Determining the destination parameters and send the packet to the proper client.
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
			sendData = sentence.getBytes();
			DatagramPacket sendPacket =new DatagramPacket(sendData, sendData.length,IPAddress , port);
			serverSocket.send(sendPacket);
		}
	}
}