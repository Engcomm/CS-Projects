import java.net.*;
import java.io.*; 

public class MultiEchoServer { 
	public static void main(String[] args) throws IOException { 
	ServerSocket serverSocket = null;
	boolean listening = true;

		try {
	serverSocket = new ServerSocket(62000);
    		} 
    	catch (IOException e) {
		System.out.println("Could not listen on port: 62000");
    		System.exit(-1);
	} 
		while (listening)
	  	new MultiEchoServerThread(serverSocket.accept()).start();
		serverSocket.close();
}
}
