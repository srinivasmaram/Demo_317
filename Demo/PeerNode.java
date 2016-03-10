package main;
import java.util.*;
import java.lang.*;
import java.math.BigInteger;
import java.io.*;
import java.net.*;
import java.util.Date;

public class PeerNode {
	PeerVar parameters;

	public PeerNode(int port2, String filePath2) {
		// TODO Auto-generated constructor stub
		parameters=new PeerVar(port2,filePath2);
		new Thread(new Runnable(){
			public void run(){
				
					ServerThread();
				
			}
		}).start();
		
		
		heartBeatSucc();
		
	}
	
	public void heartBeatSucc(){
		new HeartBeat(parameters).start();
	}

	public void setServerUp(boolean par){
		parameters.serverUp=par;
	}

	public void ServerThread() {
		ServerSocket s;
		try {
			s = new ServerSocket(parameters.port);
			 while(parameters.serverUp)
		        {  	
		        	Socket conn = s.accept();
		      //  	System.out.println("Connection received from: " + conn.getInetAddress().getHostName() + " : " + conn.getPort());
		        	
		        	new ClientHandler(conn,parameters.port,parameters).start();  
		        }
			 s.close();
			 System.out.println("thread down");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    //	System.out.println("Server socket created at port "+parameters.port+" and waiting....");
       
	}
	
	public void asClient(String serverName,int port) throws UnknownHostException, IOException{
		Socket client=new Socket(serverName,port);
		System.out.println("Just connected to " 
				 + client.getRemoteSocketAddress());
		OutputStream outToServer = client.getOutputStream();
        DataOutputStream out = new DataOutputStream(outToServer);
        out.writeUTF("Hello from "
                + client.getLocalSocketAddress());
        InputStream inFromServer = client.getInputStream();
        DataInputStream in =
                new DataInputStream(inFromServer);
        System.out.println("Server says " + in.readUTF());
        client.close();
	}
	
	
	

	
	
	
}
