
package main;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class OurClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		int choice,friend;
		Scanner sc = new Scanner(System.in);
		OurRMI ourRMI;
		while(true){
			System.out.println("1. Insert Key");
			System.out.println("2. Retrieve Key");
			System.out.println("3. Quit");
			System.out.println("Enter choice:");
			choice = sc.nextInt();
			if(choice==3) break;
			sc.nextLine();
			
			ourRMI= new OurRMI(Integer.parseInt(args[0]),"KeyInsertDelete");
			friend=Integer.parseInt(ourRMI.result());
			
			switch(choice){
				case 1:
					System.out.println("Enter Song Name:");
					String key=sc.nextLine();
					System.out.println("Enter Song Link:");
					String value=sc.nextLine();
					insertKey(friend,key, value);
					break;
				case 2:
					System.out.println("Enter Song Name to retrieve:");
					String fetchKey=sc.nextLine();
					String link = retrieveKey(friend,fetchKey);
					System.out.println(link);
					break;
				default:
					break;
					
					
			}
			
		
		}
	}
	
	public static void insertKey(int friend,String key, String value){
		OurRMI ourRMI = null;
		ourRMI = new OurRMI(friend, "findKeySuccessor: "+ key+" "+value+" "+"insert");
		System.out.println(ourRMI.result());
	}
	
	public static String retrieveKey(int friend,String key){
		OurRMI ourRMI = new OurRMI(friend, "findKeySuccessor: "+ key+" nothing"+" "+"retrieve");
		return ourRMI.result();
	}

}
