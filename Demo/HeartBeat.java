package main;

import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class HeartBeat extends Thread{

	PeerVar parameters;
	Socket client;
	DataOutputStream out;


	HeartBeat(PeerVar parameters){
		this.parameters=parameters;
	}
	
	public void run() {
		while(true){
		
			try {
				this.sleep(3000);
				if(!parameters.serverUp)
					break;
				
				client=new Socket("localhost",parameters.succPort);
				out = new DataOutputStream(client.getOutputStream());
				System.out.println("Port "+parameters.port +"  Checking HeartBeat of  " + parameters.succPort);
				out.writeUTF("My Successor is Goood");
				client.close();
			}
			catch(ConnectException e ){
				System.out.println("My succ server is dead Please Help! . I am in "+ parameters.port + " and my successor is port:"+parameters.succPort);
				
				runRepair();
				
			//	break;
			}
			catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	
	public void runRepair(){
		int failedPort=Integer.parseInt(parameters.myReplicas.get(0).split(" ")[1]);
		OurRMI ourRMI;


		ourRMI = new OurRMI(Vars.masterPort,"failPort:"+parameters.succPort);
		ourRMI.result();
		
		System.out.println("run Repair:Inside run repair START");

		//update my replica
		parameters.myReplicas.set(0,parameters.myReplicas.get(1));
		ourRMI = new OurRMI(Integer.parseInt(parameters.myReplicas.get(0).split(" ")[1]), "getSuccessor:" + ": " + ": " + ": " + ": ");
		parameters.myReplicas.set(1,ourRMI.result());
		
		System.out.println("run Repair:update my replicas done");
		
		//update my successor
		parameters.succ=new BigInteger(parameters.myReplicas.get(0).split(" ")[0]);
		parameters.succPort=Integer.parseInt(parameters.myReplicas.get(0).split(" ")[1]);
		
		System.out.println("run Repair:update my successors done");

		
		//update your successor's predecessor
		ourRMI = new OurRMI(parameters.succPort, "setPredecessor:" + parameters.nodeName+":"+ parameters.port+": " + ": " + ": ");
		ourRMI.result();
		
		System.out.println("update your successor's predecessor");
		
		//update your predecessor's replicas
		ourRMI = new OurRMI(parameters.predPort,"assignMyReplicas:"+" :");
		ourRMI.result();
		System.out.println("update your predecessor's replicas");
		
		System.out.println("run Repair: Moving kets started");
		ourRMI = new OurRMI(parameters.succPort,"recoverStageOne:"+" :");
		ourRMI.result();

		//updating r1 and r2 for my first replica
		ourRMI = new OurRMI(Integer.parseInt(parameters.myReplicas.get(1).split(" ")[1]),"recoverStageTwo:"+" :");
		System.out.println(ourRMI.result());
		
		System.out.println("updating r1 and r2 for my first replica done~1!Q!E!EASF");
		
		ourRMI = new OurRMI(Integer.parseInt(parameters.myReplicas.get(1).split(" ")[1]),"getSuccessor:"+" :");
		int tempPort=Integer.parseInt(ourRMI.result().split(" ")[1]);
		
		System.out.println("upd  Got successor of the sdfjlkasdjf asdlkjalskdf");
		
		//updating r2 for my second replica
		ourRMI = new OurRMI(tempPort, "updateReplicaKeysForReplica2:"+":");
		ourRMI.result();
		
		System.out.println("updating r2 for my second replica done");
		
		System.out.println("run Repair: Updating the finger tables");
		
		for(int i=Vars.m-1;i>=0;i--){
			if(parameters.fingerTable.get(i).port == failedPort){
				parameters.fingerTable.get(i).node=parameters.succ;
				parameters.fingerTable.get(i).port=parameters.succPort;
			}
		}
		
		int prevNodePort = parameters.predPort;
		while(prevNodePort != parameters.port){
			ourRMI = new OurRMI(prevNodePort,"updateFingerTableInRepair:"+String.valueOf(failedPort)+":"+parameters.succ+" "+parameters.succPort + ":");
			prevNodePort=Integer.parseInt(ourRMI.result());	
		}
		
		System.out.println("run Repair: Updating the finger tables DONE DONE !!!!");
		
		
		
		
		
		System.out.println("run Repair:Inside run repair END");
		
		
		

		
	}
	
}
