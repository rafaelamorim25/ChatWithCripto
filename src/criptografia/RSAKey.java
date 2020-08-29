package criptografia;

import java.math.BigInteger;

public class RSAKey {
	
	private BigInteger n, e, d;
	
	public RSAKey() {}

	public RSAKey(BigInteger n, BigInteger e, BigInteger d) {
		super();
		this.n = n;
		this.e = e;
		this.d = d;
	}

	public BigInteger getN() {
		return n;
	}

	public BigInteger getE() {
		return e;
	}

	public BigInteger getD() {
		return d;
	}

	public void setN(BigInteger n) {
		this.n = n;
	}

	public void setE(BigInteger e) {
		this.e = e;
	}

	public void setD(BigInteger d) {
		this.d = d;
	}
}
