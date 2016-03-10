package main;

import java.util.*;
import java.lang.*;
import java.math.BigInteger;
import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
	Socket server;
	// int port;
	PeerVar parameters;
	

	// Map<BigInteger,Successor> fingerTable;
	ClientHandler(Socket conn, int port, PeerVar parameters) {
		this.server = conn;
		// this.port=port;
		this.parameters = parameters;
	}

	public void run() {
		// System.out.println("Just connected to "
		// + server.getRemoteSocketAddress());
		DataInputStream in = null;
		DataOutputStream out = null;
		try {
			in = new DataInputStream(server.getInputStream());
			String received = in.readUTF();
			String[] arguments = received.split(":");
			System.out.println("recevied msg: " + received);
			
			out = new DataOutputStream(server.getOutputStream());

			switch (arguments[0]) {
			case "closestPrecedingFinger":
				out.writeUTF(closestPrecedingFinger(new BigInteger(arguments[1])));
				break;
			case "findPredecessor":
				out.writeUTF(findPredecessor(new BigInteger(arguments[1])));
				break;
			case "findSuccessor":
				out.writeUTF(findSuccessor(new BigInteger(arguments[1])));
				break;
			case "join":
				out.writeUTF(join(Integer.parseInt(arguments[1])));
				break;
			case "getPredecessor":
				out.writeUTF(getPredecessor());
				break;
			case "getSuccessor":
				out.writeUTF(getSuccessor());
				break;
			case "setPredecessor":
				out.writeUTF(setPredecessor(new BigInteger(arguments[1]), Integer.parseInt(arguments[2])));
				break;
			case "setSuccessor":
				out.writeUTF(setSuccessor(new BigInteger(arguments[1]), Integer.parseInt(arguments[2])));
				break;
			case "updateFingerTable":
				out.writeUTF(updateFingerTable(new BigInteger(arguments[1]), Integer.parseInt(arguments[2]),
						Integer.parseInt(arguments[3])));
				break;
			case "getIthFinger":
				out.writeUTF(getIthFinger(Integer.parseInt(arguments[1])));
				break;
			case "printFingerTable":
				out.writeUTF(printFingerTable());
				break;
			case "findKeySuccessor":
				arguments = received.split(" ");
				out.writeUTF(findKeySuccessor(arguments[1],arguments[2],arguments[3]));
				break;
			case "keyInsert":
				arguments = received.split(" ");
				out.writeUTF(keyInsert(new BigInteger(arguments[1]),arguments[2], Integer.parseInt(arguments[3])));
				break;
			case "keyRetrieve":
				arguments = received.split(" ");
				out.writeUTF(keyRetrieve(new BigInteger(arguments[1])));
				break;
			case "getKeyValues":
				String ress=getKeyValues(out);
				out.writeUTF(ress);
				break;
			case "updateMyReplica":
				 out.writeUTF(updateMyReplica(arguments[1], Integer.parseInt(arguments[2])));
				 break;
			case "printReplicas":
				 out.writeUTF(printReplicas());
				 break;
			case "printKeyValues":
				 out.writeUTF(printKeyValues());
				 break;
			case "getKeyValuesPred":
				 String res = getKeyValuesPred(out, Integer.parseInt(arguments[1]));
				 out.writeUTF(res);
				 break;
			case "updateReplicaKeysForReplica2":
				 out.writeUTF(updateReplicaKeysForReplica2());
				 break;
			case "clearReplica":
				 out.writeUTF(clearReplica(Integer.parseInt(arguments[1])));
				 break;
			case "dummyFailure":
				 dummyFailure(out);
				 break;
			case "assignMyReplicas":
				 out.writeUTF(assignMyReplicas());
				 break;
			case "recoverStageOne":
				out.writeUTF(recoverStageOne(out));
				break;
			case "recoverStageTwo":
				out.writeUTF(recoverStageTwo());
				break;
			case "updateFingerTableInRepair":
				out.writeUTF(updateFingerTableInRepair(Integer.parseInt(arguments[1]),arguments[2]));
				break;
			default: server.close();
				
			
			
			/*
			 * case "moveKeys" : out.writeUTF(moveKeys(new
			 * BigInteger(arguments[1])))); break;
			 */
			}

			// out.writeUTF("Thank you for connecting to "
			// + server.getLocalSocketAddress() + "\nGoodbye!");
			server.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// finally{

	}
	
	public String dummyFailure(DataOutputStream out){
		
	//	System.exit(0);
		
		try {
			out.writeUTF("failure here on node port "+ parameters.port);
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "failure";
	}

	
	public String updateFingerTableInRepair(int failedPort,String newNode){
		for(int i=Vars.m-1;i>=0;i--){
			if(parameters.fingerTable.get(i).port == failedPort){
				parameters.fingerTable.get(i).node=new BigInteger(newNode.split(" ")[0]);
				parameters.fingerTable.get(i).port=Integer.parseInt(newNode.split(" ")[1]);
			}
		}
		return String.valueOf(parameters.predPort);
	}
	
	public String closestPrecedingFinger(BigInteger id) {
		System.out.println("called closestPrecedingFinger with argument: " + id + " with in port " + parameters.port);
		for (int i = Vars.m - 1; i >= 0; i--) {
			if (Vars.isInRange(false, false, parameters.nodeName, id, parameters.fingerTable.get(i).node)) {
				
				System.out.println("closestPrecedingFinger returning: " + parameters.fingerTable.get(i).node.toString() + " "
						+ String.valueOf(parameters.fingerTable.get(i).port));
				
				return parameters.fingerTable.get(i).node.toString() + " "
						+ String.valueOf(parameters.fingerTable.get(i).port);
			}
		}
		System.out.println("called closestPrecedingFinger and returning: " + parameters.nodeName.toString() + " "
				+ this.parameters.port);
		return parameters.nodeName.toString() + " " + this.parameters.port;
	}

	public String printFingerTable() {
		System.out.println("FINGER TABLE of Port:"+parameters.port);
		for (int i = 0; i < parameters.fingerTable.size(); i++)
			System.out.println(
					parameters.fingerTable.get(i).intervalStart + " " + parameters.fingerTable.get(i).intervalEnd + " "
							+ parameters.fingerTable.get(i).node + " " + parameters.fingerTable.get(i).port);
		return parameters.nodeName.toString();
	}

	public String findPredecessor(BigInteger id) {

		System.out.println("called findPredecessor with argument: " + id + " with in port " + parameters.port);
		System.out.println(parameters.nodeName + " " + parameters.succ);
		if (parameters.nodeName.compareTo(parameters.succ) == 0)
			return parameters.nodeName.toString() + " " + parameters.port;

		BigInteger nodePrime = parameters.nodeName;
		// Here we need to connect to the server of succs
		BigInteger nodePrimeSucc = parameters.succ;
		String temp = "";
		OurRMI ourRMI;
		String resultPort = String.valueOf(parameters.port);
		while (!Vars.isInRange(false, true, nodePrime, nodePrimeSucc, id)) {
			if (nodePrime.compareTo(parameters.nodeName) == 0) {
				temp = this.closestPrecedingFinger(id);
			} else {
				ourRMI = new OurRMI(Integer.parseInt(temp.split(" ")[1]),
						"closestPrecedingFinger:" + id.toString() + ": " + ": " + ": ");
				temp = ourRMI.result();
			}
			nodePrime = new BigInteger(temp.split(" ")[0]);
			resultPort = temp.split(" ")[1];
			System.out.println("new nodePrime : " + nodePrime + " " + resultPort);
			if (nodePrime.compareTo(parameters.nodeName) != 0) {
				ourRMI = new OurRMI(Integer.parseInt(temp.split(" ")[1]),
						"getSuccessor:" + ": " + ": " + ": " + ": ");
				nodePrimeSucc = new BigInteger(ourRMI.result().split(" ")[0]);
				System.out.println("new nodePrime successor : " + nodePrimeSucc);
			} else
				break;
		}
		return nodePrime.toString() + " " + resultPort;
	}

	public String findSuccessor(BigInteger id) {

		System.out.println("Inside the find successor with id:" + id + " with in port " + parameters.port);
		

		OurRMI ourRMI = new OurRMI(Integer.parseInt(findPredecessor(id).split(" ")[1]),
				"getSuccessor:" + ": " + ": " + ": " + ": ");
		return ourRMI.result();
	}

	public String join(int friend) {
		Socket client;
		try {
			client = new Socket("localhost", friend);
		} catch (ConnectException e) {
			parameters.succ = parameters.nodeName;
			parameters.pred = parameters.nodeName;
			parameters.succPort = parameters.port;
			parameters.predPort = parameters.port;

			System.out.println("basic join executed:" + parameters.succ);
			printFingerTable();
			
			return "Success in join";
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		initFingerTable(friend);
		System.out.println("INIT FINGER TABLE DONE:");
		printFingerTable();
		
		notifyPeers(friend);
		System.out.println("Notify done:");
	
		assignMyReplicas();
		System.out.println("ASSIGNING MY REPLICAS DONE:");
		
		/*	
		OurRMI ourRMI = new OurRMI(parameters.succPort, "getPredecessor:" + ": " + ": " + ": " + ": ");
		String res = ourRMI.result();

		parameters.pred = new BigInteger(res.split(" ")[0]);
		parameters.predPort = Integer.parseInt(res.split(" ")[1]);
		//FIX: set pred.succ and succ.pred for current node
		
	*/	
		OurRMI ourRMI = new OurRMI(parameters.succPort, "setPredecessor:" + parameters.nodeName+":"+ parameters.port+": " + ": " + ": ");
		ourRMI.result();
		
		ourRMI = new OurRMI(parameters.predPort, "setSuccessor:" + parameters.nodeName+":"+ parameters.port+": " + ": " + ": ");
		ourRMI.result();
		
		
		System.out.print("Success in join for "+parameters.port);
	//	ourRMI.result();
		
		
		updateReplicas();
		
		moveKeys();
		return "success in join";
		// get successor S
		// move the keys to n
	

	}
	
	
	public String setPredecessor(BigInteger node,int port){
		System.out.println("Inside the set pred with in port " + parameters.port);

		
		parameters.pred=node;
		parameters.predPort=port;
		
		
		return "success";
	}

	public String setSuccessor(BigInteger node,int port) {
		System.out.println("Inside the set successor with in port " + parameters.port);

		
		parameters.succ=node;
		parameters.succPort=port;
		
		return "success";
	}
	
	public String getPredecessor() {
		System.out.println("Inside the get pred with in port " + parameters.port);

		return parameters.pred.toString() + " " + String.valueOf(parameters.predPort);
	}

	public String getSuccessor() {
		System.out.println("Inside the get successor with in port " + parameters.port);

		return parameters.succ.toString() + " " + String.valueOf(parameters.succPort);
	}

	public void initFingerTable(int port) {

		System.out.println("INIT FINGER TABLE START in port:" + parameters.port);
		OurRMI ourRMI = new OurRMI(port,
				"findSuccessor:" + parameters.fingerTable.get(0).intervalStart.toString() + ": " + ": " + ": ");
		String res = ourRMI.result();
		System.out.println("result is:" + res);
		parameters.fingerTable.get(0).node = new BigInteger(res.split(" ")[0]);
		parameters.fingerTable.get(0).port = Integer.parseInt(res.split(" ")[1]);
		parameters.succ = parameters.fingerTable.get(0).node;
		parameters.succPort = parameters.fingerTable.get(0).port;
		
		
		
		//--- new
//		
//		OurRMI ourRMI1 = new OurRMI(parameters.succPort, "setPredecessor:" + parameters.nodeName+":"+ parameters.port+": " + ": " + ": ");
//		ourRMI1.result();
//		
		//----new
	
		ourRMI = new OurRMI(parameters.succPort,
				"getPredecessor:"  + ": " + ": " + ": ");
		String temp=ourRMI.result();
		parameters.pred=new BigInteger(temp.split(" ")[0]);
		parameters.predPort = Integer.parseInt(temp.split(" ")[1]);

		for (int i = 0; i < Vars.m - 1; i++) {
			if (Vars.isInRange(true, false, parameters.nodeName, parameters.fingerTable.get(i).node,
					parameters.fingerTable.get(i + 1).intervalStart)) {
				System.out.println("is in Range" + i);
				parameters.fingerTable.get(i + 1).node = parameters.fingerTable.get(i).node;
				parameters.fingerTable.get(i + 1).port = parameters.fingerTable.get(i).port;
			} else {
				ourRMI = new OurRMI(port,
						"findSuccessor:" + parameters.fingerTable.get(i+1).intervalStart.toString() + ": " + ": " + ": ");
				res = ourRMI.result();
				System.out.println("result is:" + res);
				
				//---new
				if(Vars.isInRange(false, false, parameters.fingerTable.get(i+1).intervalStart, new BigInteger(res.split(" ")[0]), parameters.nodeName)){
					res=parameters.nodeName.toString() + " "+String.valueOf(parameters.port);
				}
				//----new
				
				parameters.fingerTable.get(i + 1).node = new BigInteger(res.split(" ")[0]);
				parameters.fingerTable.get(i + 1).port = Integer.parseInt(res.split(" ")[1]);
			}
		}
	}

	
	public void notifyPeers(int friend) {
		OurRMI ourRMI;
		String res, res1;
		BigInteger nodeDiff;
		
		for(int i=0;i<Vars.m;i++){
		//	ourRMI=new OurRMI(friend,"findPredecessor" + ":"+parameters.nodeName.subtract(new BigInteger("2").pow(i)).mod(new BigInteger("2").pow(Vars.m)) + ": " + ": " + ": ");
		//	res=ourRMI.result();
			res=findPredecessor(parameters.nodeName.subtract(new BigInteger("2").pow(i)).mod(new BigInteger("2").pow(Vars.m)));
			 System.out.println("Predecessor is "+res);
			 
			 if(new BigInteger(res.split(" ")[0]).compareTo(parameters.nodeName)==0){
				 res=parameters.pred.toString()+" "+String.valueOf(parameters.predPort);
			 }
			 
			nodeDiff = parameters.nodeName.subtract(new BigInteger(res.split(" ")[0])).mod(new BigInteger("2").pow(Vars.m)); 
			 
			 ourRMI=new OurRMI(Integer.valueOf(res.split(" ")[1]),"getIthFinger:"+i+": "+": "+": "); 
			 res1 = ourRMI.result();
			 if(nodeDiff.compareTo(new BigInteger("2").pow(i)) >= 0) { //&& new BigInteger(res1.split(" ")[0]).compareTo(parameters.succ) == 0) {
				 ourRMI=new OurRMI(Integer.parseInt(res.split(" ")[1]),"updateFingerTable:"+parameters.nodeName+":"+parameters.port + ":" + i + ": "); 
				 ourRMI.result();
			 } 
		}

	}

	public String updateFingerTable(BigInteger nodeName, int port, int i) {
		System.out.println("Inside updatefingertable with in port "+parameters.port);
		
		OurRMI ourRMI;
		BigInteger nodeDiff;
		String res, res1;
		
		if (Vars.isInRange(true, false, parameters.nodeName, parameters.fingerTable.get(i).node, nodeName)) {
			 System.out.println("updatefingertable with nodeName:"+nodeName+" with in port "+port+" with i:"+i);
			parameters.fingerTable.get(i).node = nodeName;
			parameters.fingerTable.get(i).port = port;
			
			nodeDiff = nodeName.subtract(parameters.pred).mod(new BigInteger("2").pow(Vars.m)); 
			
			System.out.println("Predecessor = " + parameters.pred + " nodeDiff = " + nodeDiff + " 2 ^ 1 = " + new BigInteger("2").pow(i));
			ourRMI = new OurRMI(parameters.predPort, "getIthFinger:"+i+": "+": "+": ");
			res = ourRMI.result();
			
			ourRMI = new OurRMI(port, "getSuccessor:" + ": " + ": " + ": " + ": ");
			res1 = ourRMI.result();
			
			if(nodeDiff.compareTo(new BigInteger("2").pow(i)) >= 0 &&  new BigInteger(res.split(" ")[0]).compareTo(new BigInteger(res1.split(" ")[0])) == 0) {
				ourRMI = new OurRMI(parameters.predPort, "updateFingerTable:" + nodeName + ":" + port + ":" + i + ": ");
				ourRMI.result(); 
			}
			
		}
		else {
			if(Vars.isInRange(false, false, parameters.nodeName, nodeName, parameters.succ)) {
				nodeDiff = nodeName.subtract(parameters.succ).mod(new BigInteger("2").pow(Vars.m)); 
				ourRMI = new OurRMI(parameters.succPort, "getIthFinger:"+i+": "+": "+": ");
				res = ourRMI.result();
				
				ourRMI = new OurRMI(port, "getSuccessor:" + ": " + ": " + ": " + ": ");
				res1 = ourRMI.result();
				if(nodeDiff.compareTo(new BigInteger("2").pow(i)) >= 0 &&  new BigInteger(res.split(" ")[0]).compareTo(new BigInteger(res1.split(" ")[0])) == 0) {
					ourRMI = new OurRMI(parameters.succPort, "updateFingerTable:" + nodeName + ":" + port + ":" + i + ": ");
					ourRMI.result(); 
				}
			}
		}
		return nodeName.toString();
	}

	// Send ith node and port
	public String getIthFinger(int i) {
		return parameters.fingerTable.get(i).node.toString() + " " + String.valueOf(parameters.fingerTable.get(i).port);
	}
	
	public String assignMyReplicas() {
		OurRMI ourRMI = new OurRMI(parameters.succPort, "getSuccessor:" + ": " + ": " + ": " + ": ");
		String res = ourRMI.result();
		
		
		parameters.myReplicas.set(0,parameters.succ.toString() +" "+ String.valueOf(parameters.succPort));
		parameters.myReplicas.set(1,res);
		
		System.out.println("Tnside assignMyReplicas with in port "+ parameters.port+ " with my replicas " + parameters.succPort + "and "+ res);
		return "success assign replica";
	}
	
	public String updateReplicas() {
		OurRMI ourRMI;
		
		ourRMI = new OurRMI(parameters.predPort, "getPredecessor:" + ":");
		String res = ourRMI.result();
		
		ourRMI = new OurRMI(parameters.predPort, "updateMyReplica:"+parameters.nodeName + " " + parameters.port+ ":" + "0");
		ourRMI.result();
		
		ourRMI = new OurRMI(parameters.predPort, "updateMyReplica:"+parameters.succ + " " + parameters.succPort+ ":" + "1");
		ourRMI.result();
		
		ourRMI = new OurRMI(Integer.parseInt(res.split(" ")[1]), "updateMyReplica:"+parameters.nodeName + " " + parameters.port+ ":" + "1");
		ourRMI.result();
		
		return "Sucess update replicas"; 
	}
	
	public String updateMyReplica(String newReplica, int replicaLevel) {
		System.out.println("Inside updateMyReplica with in port " + parameters.port + " and new replica " + newReplica);
		parameters.myReplicas.set(replicaLevel, newReplica);
		return "Sucess update replica";
	}
	
	public String findKeySuccessor(String key,String value, String job){
		
		System.out.println( "checking for!!!!!!!!!!!!!!!!!!! "+job +"of "+key  );
		
		BigInteger tempKey=ShaGen.shaGenerator(key);
		System.out.println("tempKey:"+tempKey);
		String res = findSuccessor(tempKey);
		OurRMI ourRMI;
		
		if(res.split(" ")[0].compareTo(parameters.nodeName.toString())==0){
			if(job.compareTo("insert") == 0) {
				System.out.println("Calling key insert with in port :"+parameters.port);
				keyInsert(tempKey,value,0);
				for(int i = 0; i < parameters.myReplicas.size(); i++) { 
					ourRMI = new OurRMI(Integer.parseInt(parameters.myReplicas.get(i).split(" ")[1]), "keyInsert: " + tempKey + " " + value + " "+ String.valueOf(i+1));
					ourRMI.result();
				}
			}
			else {
				System.out.println("Calling key retrieve with in port: "+parameters.port);
				return keyRetrieve(tempKey);
			}
		}
		else{
			if(job.equals("insert")) {
				for(int i=0;i<parameters.myReplicas.size()+1;i++){
					ourRMI=new OurRMI(Integer.parseInt(res.split(" ")[1]), "keyInsert: " + tempKey + " " + value +" "+i);
					ourRMI.result();
					if(i!=parameters.myReplicas.size()){
						ourRMI=new OurRMI(Integer.parseInt(res.split(" ")[1]),"getSuccessor:"+" :");
						res=ourRMI.result();
					}
				}
			}
			else {
				ourRMI=new OurRMI(Integer.parseInt(res.split(" ")[1]), "keyRetrieve: " + tempKey);
				return ourRMI.result();
			}
		}
		
		return "success";
	}
	
	public String keyInsert(BigInteger key,String value, int replicaLevel){
		System.out.println("keyInsert with in port:"+parameters.port+" with replica level:"+replicaLevel);
		switch(replicaLevel) {
			case 0: parameters.keyValue.put(key, value);
					break;
			case 1: parameters.keysAsReplica1.put(key, value);
					break;
			case 2: parameters.keysAsReplica2.put(key, value);
					break;
		}
		return "success Insert";
	}
	
	
	public String keyRetrieve(BigInteger key){
		System.out.println("keyRetrieve with in port:"+parameters.port);
		return parameters.keyValue.get(key);
	}
	
	public String moveKeys() {
		System.out.println("move keys in port:"+parameters.port);
		
		OurRMI ourRMI;
		
		ourRMI = new OurRMI(parameters.succPort, "getKeyValues:"+":"+":");
		updateReplicaKeysFromPred(ourRMI,parameters.keyValue);
		ourRMI.closeSocket();
		
		
		ourRMI = new OurRMI(parameters.predPort, "getKeyValuesPred:0"+":"+":");
		updateReplicaKeysFromPred(ourRMI,parameters.keysAsReplica1);
		ourRMI.closeSocket();
		
		
		ourRMI = new OurRMI(parameters.predPort, "getKeyValuesPred:1"+":"+":");
		updateReplicaKeysFromPred(ourRMI,parameters.keysAsReplica2);
		ourRMI.closeSocket();
		
		ourRMI = new OurRMI(Integer.parseInt(parameters.myReplicas.get(1).split(" ")[1]), "updateReplicaKeysForReplica2:"+":");
		ourRMI.result();
		
		return "sucess move";
	}
	
	public String updateReplicaKeysForReplica2() {
		parameters.keysAsReplica2 = new HashMap<BigInteger, String>();
		OurRMI ourRMI = new OurRMI(parameters.predPort, "getKeyValuesPred:1"+":");
		updateReplicaKeysFromPred(ourRMI, parameters.keysAsReplica2);
		ourRMI.closeSocket();
		
		return "success update replica 2";
	}
	
	public void updateReplicaKeysFromPred(OurRMI ourRMI,HashMap<BigInteger,String> temp){
		String prevEntry = ourRMI.result1(true);
		while(prevEntry.compareTo("end") != 0) {
			System.out.println("Moving key " + new BigInteger(prevEntry.split(" ")[0]) + " to port " + parameters.port);
			temp.put(new BigInteger(prevEntry.split(" ")[0]), prevEntry.split(" ")[1]);
			prevEntry=ourRMI.result1(false);
		}
		
	}
	
	public String getKeyValuesPred(DataOutputStream out,int replicaLevel){
		HashMap<BigInteger,String> temp=parameters.keyValue;
		
		if(replicaLevel==1)
			temp=parameters.keysAsReplica1;

		for(BigInteger key: temp.keySet()){
				try {
					System.out.println("sending out:"+key);
					out.writeUTF(key+" "+temp.get(key));		
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		return "end";
	}
	
	public String recoverStageOne(DataOutputStream out){
		parameters.keyValue.putAll(parameters.keysAsReplica1);
		parameters.keysAsReplica1.clear();
		recoverStageTwo();
		
		System.out.println("Done inside the 3");
/*
			//updating r1 and r2 for my first replica
			OurRMI ourRMI = new OurRMI(parameters.succPort,"recoverStagetwo:"+" :");
			ourRMI.result();
			
			//updating r2 for my second replica
			ourRMI = new OurRMI(Integer.parseInt(parameters.myReplicas.get(1).split(" ")[1]), "updateReplicaKeysForReplica2:"+":");
			ourRMI.result();
*/			
		return "success stage one";
	}
	
	public String recoverStageTwo(){
		parameters.keysAsReplica1.putAll(parameters.keysAsReplica2);
		parameters.keysAsReplica2.clear();
		updateReplicaKeysForReplica2();

		return "success stage two";
	}
	
	
	public String getKeyValues(DataOutputStream out){
		
		System.out.println("Inside getKeyValues with in port "+parameters.port);
		
		//--new2			
			parameters.keysAsReplica2= new HashMap<BigInteger, String>(parameters.keysAsReplica1);
			parameters.keysAsReplica1 = new HashMap<BigInteger,String>();
		//--new2
		
		
		ArrayList<BigInteger> forDeletion=new ArrayList<BigInteger>();
			
		for(BigInteger key: parameters.keyValue.keySet()){
			if(Vars.isInRange(true, false, key, parameters.nodeName, parameters.pred)){
				try {
					System.out.println("sending out:"+key);
					parameters.keysAsReplica1.put(key, parameters.keyValue.get(key));
					out.writeUTF(key+" "+parameters.keyValue.get(key));
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				forDeletion.add(key);
			}
		}
		
		for(BigInteger key : forDeletion){
			parameters.keyValue.remove(key);
		}
		
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("Inside port "+parameters.port+" calling CLEAR replicas "+parameters.myReplicas.get(0)+" "+parameters.myReplicas.get(1));
		
		OurRMI ourRMI = new OurRMI(Integer.parseInt(parameters.myReplicas.get(0).split(" ")[1]), "clearReplica:0"+":");
		ourRMI.result();
		
		ourRMI = new OurRMI(Integer.parseInt(parameters.myReplicas.get(1).split(" ")[1]), "clearReplica:1"+":");
		ourRMI.result();
		
		ourRMI = new OurRMI(Integer.parseInt(parameters.myReplicas.get(0).split(" ")[1]), "printKeyValues"+":"+":"+":");
		System.out.println(ourRMI.result());
		
		ourRMI = new OurRMI(Integer.parseInt(parameters.myReplicas.get(1).split(" ")[1]), "printKeyValues"+":"+":"+":");
		System.out.println(ourRMI.result());

		System.out.println("-------------------------------------------------------------------------");
		
		
		
		for(BigInteger key: parameters.keyValue.keySet()) {
			for(int i = 0; i < parameters.myReplicas.size(); i++) { 
				ourRMI = new OurRMI(Integer.parseInt(parameters.myReplicas.get(i).split(" ")[1]), "keyInsert: " + key + " " + parameters.keyValue.get(key) + " "+ String.valueOf(i+1));
				ourRMI.result();
			}
		}
	
		return "end";
	}
	
	
	public String clearReplica(int replicaLevel) {
		if(replicaLevel == 0) {
			parameters.keysAsReplica1.clear();
		} else if(replicaLevel == 1) {
			parameters.keysAsReplica2.clear();
		}
		
		return "success clear replica";
	}
	
	public String printReplicas(){
		return "The replicas of "+parameters.nodeName + " are "+parameters.myReplicas.get(0)+" and "+parameters.myReplicas.get(1);
	}
	
	public String printKeyValues() {
		System.out.println("Printing replicas of port:"+parameters.nodeName);
		System.out.println(parameters.keyValue);
		System.out.println(parameters.keysAsReplica1);
		return parameters.keysAsReplica2.toString();
	}
	
}
