package socket;

import java.net.*;
import java.io.*;

class RequestHandlingClass extends Thread {

	Socket socket;
	DataInputStream inputClient;

	RequestHandlingClass(Socket socket) {
		this.socket = socket;
		try {
			this.inputClient = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

public class myServer implements Runnable {
	static Socket socket;
	String line, delimiters = "\\s/|\\s";
	StringBuffer buffer = new StringBuffer();
	int character;

	@SuppressWarnings({ "static-access" })
	myServer(Socket socket) {
		this.socket = socket;
	}

	@SuppressWarnings("resource")
	@Override
	public void run() {
		try {
			// creating server input and output streams to read from and write
			// to the client
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os, true);

			// reading the clients first input command and splitting it
			line = br.readLine();
			String[] splitCommand = line.split(delimiters);
			if (splitCommand[0].equals("GET")) {
				// checking if the file exists
				try {
					FileInputStream input = new FileInputStream(splitCommand[1]);
					// if id does not exist then it goes to the catch block
					pw.println("200 OK\n");
					character = input.read();
					while (character != -1) {
						buffer.append((char) character);
						character = input.read();
					}
					pw.println(buffer);
					pw.println("close");
				} catch (FileNotFoundException e) {
					pw.println("404 File Not Found");
					System.exit(0);
				}
			}
			if (splitCommand[0].equals("PUT")) {

				String tempFileName = splitCommand[1];
				int Lastindex = tempFileName.lastIndexOf('\\');
				String fileName = tempFileName.substring(Lastindex + 1, tempFileName.length());
				String path = "C:\\Eclipse\\";
				path = path.concat(fileName);

				FileInputStream in = new FileInputStream(tempFileName);
				FileOutputStream out = new FileOutputStream(path);

				int character;
				String success = "200 OK File Created";
				pw.flush();

				while ((character = in.read()) != -1) {
					out.write(character);
				}
				if (character == -1)
					pw.println(success);
				System.out.println("\n\n");
			}
		} catch (Exception e) {
			System.out.println("Connection Issue");
		}
	}

	public static void main(String args[]) {

		boolean running = true;
		try {
			int port = Integer.parseInt(args[0]);
			ServerSocket server = new ServerSocket(port);

			while (running) {
				// creating a socket and listening on a port
				System.out.println("Server started and listening on port " + port);
				socket = server.accept();

				// For making this server a multithreaded server: to accept more
				// number of clients at the socket
				RequestHandlingClass request = new RequestHandlingClass(socket);
				request.start();

				// RequestHandler requestHandler = new RequestHandler( socket );
				System.out.println("received a connection :" + socket + "\n\n");
				new Thread(new myServer(socket)).start();

				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
				String s = bufferRead.readLine();
				if (s.equalsIgnoreCase("shutdown")) {
					OutputStream os1 = socket.getOutputStream();
					PrintWriter pw1 = new PrintWriter(os1, true);
					pw1.println("close");
					System.out.println("all clients are closed");
					System.out.println("Server is going to shutdown");
					// socket.close();
					server.close();
					running = false;
				}
			}
		} catch (IOException e) {
			System.out.println("No conncetion established");
			System.exit(0);
		} finally {
			// Closing the socket
			try {
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}