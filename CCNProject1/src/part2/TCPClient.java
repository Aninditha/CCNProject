
package part2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;

public class TCPClient {
	public static void main(String args[])
	{
		TCPClient tcpClient=new TCPClient();
		tcpClient.run();
	}
	@SuppressWarnings("resource")
	public void run()
	{	
		long sumOfEte=0;
		long maxEte=0;
		try {
		
			//Defining a socket
			Socket sock=new Socket("localhost",8777);
			
			//Defining input and output buffer
			BufferedReader inFromServer=new BufferedReader(new InputStreamReader(sock.getInputStream()));
			PrintStream outputToReceiverBuffer = new PrintStream(sock.getOutputStream());
			//outputToReceiverBuffer.print("String");
			
			//Declaring variables
			int i=1,j;
			long currTime,prevTime,timeDiff;
			String sentence = "A",inputFromReceiver;
			long[] ete=new long[2000];
			
			//Declaring the String to be sent. In this case it is sequence of 'A's
			//Change the upper limit value of variable 'j' for the change in length of string to be send.
			for(j=1;j<11;j++)
			 sentence=sentence.concat("A");
            
            //Repeating the process for 1000 times
			//Change the value from 1000 to number of times process needs to be repeated
			while(i<=1000)
			{
					currTime=System.nanoTime();
					System.out.println("\n Sent:"+i+sentence);
					System.out.println("\n At time:"+currTime);
					outputToReceiverBuffer.println(i+sentence);
	          
	                //Reading input from the output stream and calculating the time-differences and ETEs
					if((inputFromReceiver=inFromServer.readLine()) != null)
	                {
						System.out.println("Data received from the receiver:"+inputFromReceiver);
						prevTime=System.nanoTime();
						System.out.println("Time taken:"+prevTime+" nano-secs");
						timeDiff=prevTime-currTime;
						System.out.println("Difference in time:"+timeDiff+ " nano-secs");
						ete[i]=timeDiff/2;
						System.out.println("The ETE Delay is:"+ete[i]+" nano-secs");
						sumOfEte=sumOfEte+ete[i];
		                if(maxEte<ete[i])
		                {
		                	maxEte=ete[i];
		                }     
		                i=i+1;
		             }
			}
			
			//Printing calculated Average and Max ETEs
            long avg;
			avg=sumOfEte/1000;  //Change the value of 1000 to the number of times the process is repeated
			//System.out.println(sumOfEte);
			System.out.println("Average of all the etes is="+avg);
			System.out.println("Maximum ete of all the iterations is="+maxEte);  
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}