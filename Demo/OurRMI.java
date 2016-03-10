package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class OurRMI {

int port;
String arguments;
Socket client;
OutputStream outToServer;// = client.getOutputStream();
DataOutputStream out ;
InputStream inFromServer;
DataInputStream in;

	public OurRMI(int port,String arguments){

			this.port=port;
			this.arguments=arguments;
		try {
		     client=new Socket("localhost",port);
			 outToServer = client.getOutputStream();
			 out = new DataOutputStream(outToServer);
	//	     
		     inFromServer = client.getInputStream();
		     in =new DataInputStream(inFromServer);
	     
	     
		     System.out.println("trying to connect");
		
		}
		catch (UnknownHostException e) {
			 System.out.println("trying to connect failed unknownhostexception");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			 System.out.println("trying to connect failed ioexception");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	
	public String result(){
		String res="";
		try {
			out.writeUTF(arguments);
			res=in.readUTF();
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(res.equals(""))
			return arguments.split(" ")[0];
		return res;
	}
	
	public String result1(boolean send){
		String res="";
		try {
			if(send)
				out.writeUTF(arguments);
			res=in.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public void closeSocket(){
		try {
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




	
	
}
