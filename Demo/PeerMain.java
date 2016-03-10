package main;

public class PeerMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	

		System.out.println(args[0]);	
		PeerNode p= new PeerNode(Integer.parseInt(args[0]),"main/dsff");
		OurRMI ourRMI= new OurRMI(Vars.masterPort,"join:"+args[0]);
		int friend=Integer.parseInt(ourRMI.result());
		System.out.println("friend is:"+friend);
		ourRMI = new OurRMI(Integer.parseInt(args[0]), "join"+":"+friend+":"+":");
		ourRMI.result();
		
	//	p.join(friend);
	
	}

}
