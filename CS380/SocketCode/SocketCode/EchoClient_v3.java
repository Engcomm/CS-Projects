import java.io.*; 
import java.net.*;
import java.lang.*;
//Please enter the following to run 
//java EchoClient_v3 <Server IP> <Server Port>; use a space to separate them and hit enter
public class EchoClient_v3 { 
	public static void main(String[] args) throws IOException { 
		try {	
			Socket echoSocket = null; 
			PrintWriter out = null; 
			BufferedReader in = null;
			String echoServer = null;
			int echoServPort;          /* Echo server port */
        	String echoServIP;     /* IP address of server */
        	echoServIP = args[0];  /* First arg: server IP Address */
        	echoServPort = Integer.parseInt(args[1]);	/* Second arg: string to echo */
			System.out.println("Server IP address: " + echoServIP + ":" + echoServPort);
			try { 
				echoSocket = new Socket(echoServIP, echoServPort); 
				out = new PrintWriter(echoSocket.getOutputStream(), true); 
				in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream())); 
				} 
			catch (UnknownHostException e) { 
				System.err.println("Do not know about echoServer: " + echoServIP +e); 	
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
			System.err.println("Could not get I/O for the connection to echoServer: "+e); 
			System.exit(1);
		} 
	}
 } 

