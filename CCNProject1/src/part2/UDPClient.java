package part2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
	public static void main(String[] args) throws IOException {
		//Declaring a datagramSocket
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("localhost");
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		
		//Initializing variables
		int j;
		long sysTime,prevTime,timeDiff;
		long sumOfEte=0;
		long maxEte=0;
		long[] ete=new long[2000];
        int iteration=1;
        
        //Repeating the process for 1000 times
     	//Change the value from 1000 to number of times process needs to be repeated
        while(iteration<=5)
		{
			    sysTime=System.nanoTime();
        	   	String sentence = iteration+"A";
				
				//Declaring the String to be sent. In this case it is sequence of 'A's
				//Change the upper limit value of variable 'j' for the change in length of string to be send.
				for(j=1;j<10;j++)
					 sentence=sentence.concat("A");
				sendData = sentence.getBytes();
				System.out.println("Sent:"+sentence);
                System.out.println("At time:"+sysTime);
		
                //Sending and receiving packet and calculation part
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 8777);
                clientSocket.send(sendPacket);
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        		clientSocket.receive(receivePacket);
        		String inputFromReceiver1 = new String(receivePacket.getData());
                System.out.println("\n Received from receiver:->"+inputFromReceiver1);
                prevTime=System.nanoTime();
                System.out.println("\n At time:"+prevTime);
                timeDiff=prevTime-sysTime;
                System.out.println("\nDifference:"+timeDiff);
                System.out.println("\n");
                ete[iteration]=timeDiff/2;
                System.out.println("The ETE Delay is:"+ete[iteration]);		
                sumOfEte=sumOfEte+ete[iteration];
                if(maxEte<ete[iteration])
                {
                  maxEte=ete[iteration];
                }     
                iteration=iteration+1;
                System.out.println(sumOfEte);
                System.out.println("sum of etes till now="+sumOfEte);                 
                     
		}

            //printing average and maximum ete
            long avg;
			avg=sumOfEte/5;
			System.out.println(sumOfEte);
			System.out.println("Average of all the etes is="+avg);
			System.out.println("Maximum ete of all the iterations is="+maxEte);
		    clientSocket.close(); 
	}
}
