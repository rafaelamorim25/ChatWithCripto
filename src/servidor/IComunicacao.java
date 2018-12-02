package servidor;

import java.io.IOException;

public interface IComunicacao {
	
	public void comunicar (String msg, Jogador jogador) throws IOException;

}
