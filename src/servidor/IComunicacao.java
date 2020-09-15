package servidor;

import java.io.IOException;

public interface IComunicacao {
	
	public void comunicar (String msg, Usuario usuario) throws IOException;
	
	public void comunicar (String msg) throws IOException;

}
