package main;
import java.lang.*;
import java.math.BigInteger;
import java.util.*;
import java.security.*;
public class ShaGen {
	
	public static BigInteger shaGenerator(String input){
		BigInteger result=new BigInteger("0");
		// TODO Auto-generated method stub
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(input.getBytes());
			byte[] digest = md.digest();
			result=new BigInteger(1,digest);
			result=result.mod(new BigInteger("2").pow(Vars.m));		
			//			System.out.println("MD5 fingerprint: " + result);
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
