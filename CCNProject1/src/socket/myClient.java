package socket;

import java.net.*;
import java.io.*;

public class myClient {
	@SuppressWarnings("resource")
	public static void main(String args[]) throws IOException {

		Socket socket = null;
		String hostName, command, fileName;
		int port, character;
		StringBuffer buffer = new StringBuffer();

		if (args.length == 0) {
			System.out.println(
					"Error: command line arguments (hostname,port,command," + "filename) not found.\nTry again...!!!");
			System.exit(1);
		}

		// assigning the command line arguments to the local variables
		hostName = args[0];
		port = Integer.parseInt(args[1]);
		command = args[2];
		fileName = args[3];

		try {
			// connecting to the server
			socket = new Socket(hostName, port);
			System.out.println("Client: Connected to the server..!!");

			// creating client input and output streams to read from and write
			// to the server
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os, true);

			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			// GET Method implementation.
			if (command.equals("GET")) {
				// sending a valid HTTP/1.1 GET request to the server
				System.out.println("Client: GET /" + fileName + " HTTP/1.1 request sent to the server\n");
				pw.println("GET /" + fileName + " HTTP/1.1\n");

				// reading the servers response and displaying it
				String line = br.readLine();
				if (line.equals("404 Not Found")) {
					System.out.println("Server: " + line);
					System.exit(0);
				} else {
					line = br.readLine();
					while (line != null) {
						System.out.println("Server: " + line);
						line = br.readLine();
						if (line.equals("close"))
							break;
					}
					System.out.println("\n");
				}
			}

			// PUT Method implementation
			if (command.equals("PUT")) {
				// submitting a valid HTTP/1.1 PUT request to the server
				pw.println("PUT /" + fileName + " HTTP/1.1\n");
				System.out.println("Client: PUT /" + fileName + " HTTP/1.1 request sent to the server\n");

				// pass the file contents to the server
				FileInputStream input = new FileInputStream(fileName);
				character = input.read();
				while (character != -1) {
					buffer.append((char) character);
					character = input.read();
				}
				pw.println(buffer);
				// pw.flush();

				// displaying servers responses
				String line = br.readLine();
				if (line.endsWith("close")) {
					System.out.println("Server: " + line);
					socket.close();
				}
				while (line != null) {
					System.out.println("Server: " + line);
					// line = br.readLine();
					// if(line == null)
					break;
				}
				System.out.println("\n");
			}
		} catch (UnknownHostException uhe) {
			System.out.println("Unknown Host...!!!");
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Client: shuting down now...!!!");
			socket.close();
		}
	}
}