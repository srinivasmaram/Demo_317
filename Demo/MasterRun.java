package main;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MasterRun {
	
	public static void main(String[] args) {
	
	//	new MainHandler();
	
		
		
		PeerNode p1=new PeerNode(5710,"C:/Users/srinivasMaram/workspace/KeyValueStore/keys/5678.txt");
		PeerNode p2=new PeerNode(5711,"C:/Users/srinivasMaram/workspace/KeyValueStore/keys/5679.txt");
		PeerNode p3=new PeerNode(5712,"C:/Users/srinivasMaram/workspace/KeyValueStore/keys/5680.txt");
		PeerNode p4=new PeerNode(5713,"C:/Users/srinivasMaram/workspace/KeyValueStore/keys/5680.txt");
		PeerNode p5=new PeerNode(5714,"C:/Users/srinivasMaram/workspace/KeyValueStore/keys/5680.txt");

		
		OurRMI ourRMI = new OurRMI(5710, "join"+":5623"+":"+":");
		ourRMI.result();
		
		ourRMI = new OurRMI(5711, "join"+":5710"+":"+":");
		ourRMI.result();
  		
		ourRMI = new OurRMI(5712, "join"+":5710"+":"+":");
		ourRMI.result();
		
//		ourRMI = new OurRMI(5713, "join"+":5710"+":"+":");
//		ourRMI.result();
//		
//		ourRMI = new OurRMI(5714, "join"+":5710"+":"+":");
//		ourRMI.result();
		
		ourRMI = new OurRMI(5710, "printFingerTable"+":5710"+":"+":");
		ourRMI.result();
		
		ourRMI = new OurRMI(5711, "printFingerTable"+":5710"+":"+":");
		ourRMI.result();
		
//		ourRMI = new OurRMI(5712, "printFingerTable"+":5710"+":"+":");
//		ourRMI.result();
//		
//		ourRMI = new OurRMI(5713, "printFingerTable"+":5710"+":"+":");
//		ourRMI.result();
		
		ourRMI = new OurRMI(5710, "getSuccessor"+":"+":"+":");
		System.out.println(ourRMI.result());
		
		ourRMI = new OurRMI(5711, "getSuccessor"+":"+":"+":");
		System.out.println(ourRMI.result());
		
		ourRMI = new OurRMI(5712, "getSuccessor"+":"+":"+":");
		System.out.println(ourRMI.result());
		
//		ourRMI = new OurRMI(5713, "getSuccessor"+":"+":"+":");
//		System.out.println(ourRMI.result());
//		
//		ourRMI = new OurRMI(5714, "getSuccessor"+":"+":"+":");
//		System.out.println(ourRMI.result());
		
		ourRMI = new OurRMI(5710, "getPredecessor"+":"+":"+":");
		System.out.println(ourRMI.result());
		
		ourRMI = new OurRMI(5711, "getPredecessor"+":"+":"+":");
		System.out.println(ourRMI.result());
		
		ourRMI = new OurRMI(5712, "getPredecessor"+":"+":"+":");
		System.out.println(ourRMI.result());
		
//		ourRMI = new OurRMI(5713, "getPredecessor"+":"+":"+":");
//		System.out.println(ourRMI.result());
//		
//		ourRMI = new OurRMI(5714, "getPredecessor"+":"+":"+":");
//		System.out.println(ourRMI.result());
		
		String key1="Hello";
		String value="https://www.youtube.com/watch?v=YQHsXMglC9A";
		ourRMI = new OurRMI(5710, "findKeySuccessor: "+ key1+" "+value+" "+"insert");
		System.out.println(ourRMI.result());
		
		 key1="nuvvantenanavvu";
		 value="https://www.youtube.com/watch?v=YQHsXMglC9A";
		ourRMI = new OurRMI(5710, "findKeySuccessor: "+ key1+" "+value+" "+"insert");
		System.out.println(ourRMI.result());
		
		 key1="pillanuvvuleni";
		 value="https://www.youtube.com/watch?v=YQHsXMglC9A";
		ourRMI = new OurRMI(5710, "findKeySuccessor: "+ key1+" "+value+" "+"insert");
		System.out.println(ourRMI.result());
		
		 key1="ramayyavasthavaya";
		 value="https://www.youtube.com/watch?v=YQHsXMglC9A";
		ourRMI = new OurRMI(5710, "findKeySuccessor: "+ key1+" "+value+" "+"insert");
		System.out.println(ourRMI.result());
		
		 key1="roseroseroserose";
		 value="https://www.youtube.com/watch?v=YQHsXMglC9A";
		ourRMI = new OurRMI(5710, "findKeySuccessor: "+ key1+" "+value+" "+"insert");
		System.out.println(ourRMI.result());
		
		key1="potheponilera";
		 value="https://www.youtube.com/watch?v=YQHsXMglC9A";
		ourRMI = new OurRMI(5710, "findKeySuccessor: "+ key1+" "+value+" "+"insert");
		System.out.println(ourRMI.result());
		
		key1="Iamverygoodboy";
		 value="https://www.youtube.com/watch?v=YQHsXMglC9A";
		ourRMI = new OurRMI(5710, "findKeySuccessor: "+ key1+" "+value+" "+"insert");
		System.out.println(ourRMI.result());
		
		key1="srinivasisagoodboy";
		 value="https://www.youtube.com/watch?v=YQHsXMglC9A";
		ourRMI = new OurRMI(5710, "findKeySuccessor: "+ key1+" "+value+" "+"insert");
		System.out.println(ourRMI.result());
		
		ourRMI = new OurRMI(5710, "findKeySuccessor: "+ key1+" deadbeef"+" "+"retrieve");
		System.out.println(ourRMI.result());
		
		/*
		ourRMI = new OurRMI(5710, "printKeyValues"+":"+":"+":");
		System.out.println(ourRMI.result());
		ourRMI = new OurRMI(5711, "printKeyValues"+":"+":"+":");
		System.out.println(ourRMI.result());
		ourRMI = new OurRMI(5712, "printKeyValues"+":"+":"+":");
		System.out.println(ourRMI.result());
		*/
		
		ourRMI = new OurRMI(5713, "join"+":5710"+":"+":");
		ourRMI.result();
		
		ourRMI = new OurRMI(5714, "join"+":5710"+":"+":");
		ourRMI.result();
		
		ourRMI = new OurRMI(5712, "printFingerTable"+":5710"+":"+":");
		ourRMI.result();
		
		ourRMI = new OurRMI(5713, "printFingerTable"+":5710"+":"+":");
		ourRMI.result();
		
		ourRMI = new OurRMI(5713, "getSuccessor"+":"+":"+":");
		System.out.println(ourRMI.result());
		
		ourRMI = new OurRMI(5714, "getSuccessor"+":"+":"+":");
		System.out.println(ourRMI.result());
		
		ourRMI = new OurRMI(5713, "getPredecessor"+":"+":"+":");
		System.out.println(ourRMI.result());
		
		ourRMI = new OurRMI(5714, "getPredecessor"+":"+":"+":");
		System.out.println(ourRMI.result());
		
		ourRMI = new OurRMI(5710, "findKeySuccessor: "+ key1+" deadbeef"+" "+"retrieve");
		System.out.println(ourRMI.result());
		
		ourRMI = new OurRMI(5710, "printReplicas"+":"+":"+":");
		System.out.println(ourRMI.result());
		
		
		
		ourRMI = new OurRMI(5711, "printReplicas"+":"+":"+":");
		System.out.println(ourRMI.result());
		ourRMI = new OurRMI(5712, "printReplicas"+":"+":"+":");
		System.out.println(ourRMI.result());
		ourRMI = new OurRMI(5713, "printReplicas"+":"+":"+":");
		System.out.println(ourRMI.result());
		ourRMI = new OurRMI(5714, "printReplicas"+":"+":"+":");
		System.out.println(ourRMI.result());
		
		ourRMI = new OurRMI(5710, "printKeyValues"+":"+":"+":");
		System.out.println(ourRMI.result());
		ourRMI = new OurRMI(5711, "printKeyValues"+":"+":"+":");
		System.out.println(ourRMI.result());
		ourRMI = new OurRMI(5712, "printKeyValues"+":"+":"+":");
		System.out.println(ourRMI.result());
		ourRMI = new OurRMI(5713, "printKeyValues"+":"+":"+":");
		System.out.println(ourRMI.result());
		ourRMI = new OurRMI(5714, "printKeyValues"+":"+":"+":");
		System.out.println(ourRMI.result());

		
		
		p4.parameters.serverUp=false;
		
		ourRMI = new OurRMI(5713, "dummyFailure"+":"+":"+":");		
		System.out.println(ourRMI.result());
		
		try {
			Thread.currentThread().sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ourRMI = new OurRMI(5710, "printReplicas"+":"+":"+":");
		System.out.println(ourRMI.result());
		ourRMI = new OurRMI(5711, "printReplicas"+":"+":"+":");
		System.out.println(ourRMI.result());
		ourRMI = new OurRMI(5712, "printReplicas"+":"+":"+":");
		System.out.println(ourRMI.result());
		ourRMI = new OurRMI(5714, "printReplicas"+":"+":"+":");
		System.out.println(ourRMI.result());
	
		
		ourRMI = new OurRMI(5710, "printKeyValues"+":"+":"+":");
		System.out.println(ourRMI.result());
		ourRMI = new OurRMI(5711, "printKeyValues"+":"+":"+":");
		System.out.println(ourRMI.result());
		ourRMI = new OurRMI(5712, "printKeyValues"+":"+":"+":");
		System.out.println(ourRMI.result());
		ourRMI = new OurRMI(5714, "printKeyValues"+":"+":"+":");
		System.out.println(ourRMI.result());
		
		ourRMI = new OurRMI(5710, "printFingerTable"+":5710"+":"+":");
		ourRMI.result();
		ourRMI = new OurRMI(5711, "printFingerTable"+":5710"+":"+":");
		ourRMI.result();
		ourRMI = new OurRMI(5712, "printFingerTable"+":5710"+":"+":");
		ourRMI.result();
		ourRMI = new OurRMI(5714, "printFingerTable"+":5710"+":"+":");
		ourRMI.result();
		
		
		
	/*	
		ourRMI = new OurRMI(5714, "printKeyValues"+":"+":"+":");
		System.out.println(ourRMI.result());
		*/
		/*
		
	    try {
			Socket client=new Socket("localhost",5714);
		} catch (ConnectException e) {
			System.out.println("sdfasdfasdf");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	*/
		
		
		
	/*	
		ourRMI = new OurRMI(5713, "join"+":5710"+":"+":");
		ourRMI.result();
	*/	
		
		
	//	p1.asClient("localhost", 5679);
	//	p2.asClient("localhost", 5680);
	//	p3.asClient("localhost", 5678);
		
		
	//	PeerNode p2=new PeerNode(5679,"c:/Users/srinivasMaram/workspace/KeyValueStore/keys/5679.txt");
		//p2.join(p1);
		
		
		
		
	//	PeerNode p3 = new PeerNode(5679,"c:/Users/srinivasMaram/workspace/KeyValueStore/keys/5680.txt");
		
		
	}
}
