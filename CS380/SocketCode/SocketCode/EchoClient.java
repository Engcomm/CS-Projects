import java.io.*; 
import java.net.*;
public class EchoClient { 
	public static void main(String[] args) throws IOException {
		try {	
			Socket echoSocket = null; 
			PrintWriter out = null; 
			BufferedReader in = null; 
			try { 
				echoSocket = new Socket("127.0.0.1", 9000); 
				out = new PrintWriter(echoSocket.getOutputStream(), true); 
				in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream())); 
			} 
			catch (UnknownHostException e) { 
			System.err.println("Do not know about host: 127.0.0.1."+e); 	
			System.exit(1); 
			} 
BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)); 
			String userInput;
			while ((userInput = stdIn.readLine()) != null) { 
				out.println(userInput); 
				System.out.println("Echo from TCP Server: " + in.readLine());
	    		if (userInput.equals("Bye."))
	        		break;        
		 	} 
			out.close(); 
			in.close(); 
			stdIn.close(); 
			echoSocket.close();
		} 
		catch (IOException e) { 
			System.err.println("Could not get I/O for the connection to: localhost." + e); 
			System.exit(1);
		} 
	}
 } 
