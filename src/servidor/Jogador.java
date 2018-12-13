package servidor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

public class Jogador extends Thread {
	
	private Socket con;
	private InputStream in;
	private InputStreamReader inr;
	private BufferedReader bfr;
	private BufferedWriter bfw;
	private IComunicacao comunicador;
	private int player;

	public Jogador(Socket con, int player) {
		this.con = con;
		try {
			in = con.getInputStream();
			inr = new InputStreamReader(in);
			bfr = new BufferedReader(inr);
			OutputStream ou = this.con.getOutputStream();
			Writer ouw = new OutputStreamWriter(ou);
			bfw = new BufferedWriter(ouw);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.player = player;
	}
	
	public void run() {

		try {
			String msg = "";
			while (!"Sair".equalsIgnoreCase(msg) && msg != null) {
				msg = bfr.readLine();
				System.out.println("chegou: "+ msg);
				this.comunicador.comunicar(msg, this);
			}

		} catch (Exception e) {
			try {
				this.comunicador.comunicar("Encerrar ", this);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}	
	}
	
	public void desconectar() {
		try {
			con.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendToJogador(String msg) {
		try{
			System.out.println("Mensagem que vai ser enviada ao player" + player +": "+msg+"\r\n");
			bfw.write(msg + "\r\n");
			bfw.flush();
		}catch(Exception e){
			System.out.println("Desconectour");
		}			
	}
	
	public int getPlayer () {
		return player;
	}
	
	public void setComunicador(IComunicacao comunicador) {
		this.comunicador = comunicador;
	}
}
