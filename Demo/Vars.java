package main;

import java.math.BigInteger;

public class Vars {
	static int m=10;
	static int masterPort=5710;
	static boolean isInRange(boolean leftInclude,boolean rightInclude,BigInteger left,BigInteger right,BigInteger id){
		
	//	System.out.println(leftInclude+" "+rightInclude+" "+left +" "+right+" "+id);
		
		if(right.compareTo(new BigInteger("0")) == 0) {
			if(left.compareTo(new BigInteger("0")) == 0) {
				if(id.compareTo(new BigInteger("0")) == 0) return leftInclude || rightInclude;
				else return true;
			}
			else if(left.compareTo(id) == 0) return leftInclude;
			else if(right.compareTo(id) == 0) return rightInclude;
			else return !isInRange(!rightInclude, !leftInclude, right, left, id);
		}
		
		if(left.compareTo(right)>=0){ 
			return isInRange(leftInclude,false,left,new BigInteger("2").pow(Vars.m),id) || 
					(isInRange(true,rightInclude,(new BigInteger("0")),right,id));
		}
		
		
		if(leftInclude && rightInclude){
			if(id.compareTo(left)>=0 && id.compareTo(right)<=0){
				return true;
			}
		}
		else if(leftInclude){
			if(id.compareTo(left)>=0 && id.compareTo(right)<0){
				return true;
			}
		}
		else if(rightInclude){
			if(id.compareTo(left)>0 && id.compareTo(right)<=0){
				return true;
			}
		}
		else if(id.compareTo(left)>0 && id.compareTo(right)<0){
				return true;
		}
		
		return false;
	}

	
	public static void main(String[] args){
		BigInteger x1=new BigInteger("7");
		BigInteger x2=new BigInteger("2");
		BigInteger id=new BigInteger("3");
		System.out.println(isInRange(true,false,x1,x2,id));
	} 
}
