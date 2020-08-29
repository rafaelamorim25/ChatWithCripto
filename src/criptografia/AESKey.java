package criptografia;

public class AESKey {
	
	private String key;
	private String iv;
	
	public AESKey() {}
	
	public AESKey(String key, String iv) {
		super();
		this.key = key;
		this.iv = iv;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getIv() {
		return iv;
	}
	public void setIv(String iv) {
		this.iv = iv;
	}

	@Override
	public String toString() {
		return key + " " + iv;
	}
}
