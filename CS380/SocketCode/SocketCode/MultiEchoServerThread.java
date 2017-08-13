import java.net.*;
import java.io.*; 

public class MultiEchoServerThread extends Thread {
	private Socket clientSocket = null;	

	public MultiEchoServerThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
  	}

	public void run(){
    	String inputLine, outputLine;
		try {    
			PrintWriter out = new PrintWriter( clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader( clientSocket.getInputStream())); 
			while ((inputLine = in.readLine()) != null) {	
			outputLine = inputLine;
			out.println(outputLine);
			System.out.println("TCP Port Number: " + clientSocket.getPort());
			System.out.println("Echo to TCP Client: " + outputLine);

    			if (outputLine.equals("Bye."))
        		break;
			}
		out.close();
		in.close();
		clientSocket.close();
        	} 
        	catch (IOException e) {
			e.printStackTrace();
		}

    } 
} 
