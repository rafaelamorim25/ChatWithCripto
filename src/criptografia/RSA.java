package criptografia;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {
	
	//https://pt.wikipedia.org/wiki/RSA_(sistema_criptogr%C3%A1fico)
	
	public static RSAKey generateKey() {
		
	    int bitlen = 2048;

	    //Escolha de forma aleatória dois números primos grandes p e q
	    SecureRandom r = new SecureRandom();
	    BigInteger p = new BigInteger(bitlen / 2, 100, r); 
	    BigInteger q = new BigInteger(bitlen / 2, 100, r); 

	    //Compute n = p * q
	    BigInteger n = p.multiply(q);

	    //Compute a função totiente phi(n) = (p -1) (q -1)
	    BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

	    //Escolha um inteiro  "e"  , 1 < "e" < phi(n) ,  "e" e phi(n) sejam primos entre si.
	    BigInteger e = new BigInteger("3");
	    while(m.gcd(e).intValue() > 1) e = e.add(new BigInteger("2"));

	    // d seja inverso multiplicativo de "e"
	    BigInteger d = e.modInverse(m);
	    
	    return new RSAKey(n, e, d);
	}
	
	public static String encriptar(String mensagem, RSAKey key) {
		return new BigInteger(mensagem.getBytes()).modPow(key.getE(), key.getN()).toString();
	}
	
	public static String decriptar(String mensagemCifrada, RSAKey key) {
		return new String(new BigInteger(mensagemCifrada).modPow(key.getD(), key.getN()).toByteArray());
	}
	
	/*
	
	public static void main(String args[]) {
	    String msg = "Paz e felicidade a todos!";
	    String msgcifrada = null;
	    String msgdecifrada = null;
	    BigInteger n, d, e;
	    int bitlen = 2048;

	    //Escolha de forma aleatória dois números primos grandes p e q
	    SecureRandom r = new SecureRandom();
	    BigInteger p = new BigInteger(bitlen / 2, 100, r); 
	    BigInteger q = new BigInteger(bitlen / 2, 100, r); 

	    //Compute n = p * q
	    n = p.multiply(q);

	    //Compute a função totiente phi(n) = (p -1) (q -1)
	    BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

	    //Escolha um inteiro  "e"  , 1 < "e" < phi(n) ,  "e" e phi(n) sejam primos entre si.
	    e = new BigInteger("3");
	    while(m.gcd(e).intValue() > 1) e = e.add(new BigInteger("2"));

	    // d seja inverso multiplicativo de "e"
	    d = e.modInverse(m);

	    System.out.println("p:"+p);
	    System.out.println("q:"+q);
	    System.out.println("n:"+n);
	    System.out.println("e:"+e);
	    System.out.println("d:"+d);

	    //mensagem cifrada - RSA_encrypt()
	    msgcifrada = new BigInteger(msg.getBytes()).modPow(e, n).toString();
	    System.out.println("msg cifrada: "+ msgcifrada);

	    //mensagem decifrada - RSA_decrypt()
	    msgdecifrada = new String(new BigInteger(msgcifrada).modPow(d, n).toByteArray());
	    System.out.println("msg decifrada: " +msgdecifrada);
	    
	    System.out.println("---------------------------------------------------------");
	    String mensagem = "Mensagem";
	    Keys k = RSA.generateKeys();
	    String mensagemCifrada = RSA.encriptar(mensagem, k.getE(), k.getN());
	    String mensagemDecriptada = RSA.decriptar(mensagemCifrada, k.getD(), k.getN());
	    System.out.println(mensagem);
	    System.out.println(mensagemCifrada);
	    System.out.println(mensagemDecriptada);
	    System.out.println("---------------------------------------------------------");
	  } */
}
