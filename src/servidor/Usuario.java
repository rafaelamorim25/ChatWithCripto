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

import criptografia.AES;
import criptografia.AESKey;
import criptografia.RSA;
import criptografia.RSAKey;

public class Usuario extends Thread {

	private Socket con;
	private InputStream in;
	private InputStreamReader inr;
	private BufferedReader bfr;
	private BufferedWriter bfw;
	private IComunicacao comunicador;
	private RSAKey keys;
	private Boolean firstMessage = true;
	private AESKey privateKey;

	public Usuario(Socket con) {
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
	}

	public void run() {

		keys = RSA.generateKey();
		try {
			System.out.println("---------------------------------");
			System.out.println("Chave RSA pública que será enviada: " + keys.getE() + " " + keys.getN());
			System.out.println("---------------------------------");
			bfw.write(keys.getE() + " " + keys.getN() + "\r\n");
			bfw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String msg = "";
		while (!"Sair".equalsIgnoreCase(msg) && msg != null) {

			try {
				msg = bfr.readLine();
			} catch (IOException e) {
				System.out.println("Um usuário saiu");
				System.out.println("Encerrando servidor");
				System.exit(0);
			}
			if (firstMessage) {
				System.out.println("---------------------------------");
				System.out.println("Chave AES recebida criptografada com RSA: " + msg);
				

				msg = RSA.decriptar(msg, keys);
				String[] mensagem = msg.split(" ");
				privateKey = new AESKey(mensagem[0], mensagem[1]);
				firstMessage = false;
				
				System.out.println("Chave AES recebida sem criptografia: " + privateKey);
				System.out.println("---------------------------------");

			} else {

				try {
					msg = AES.decrypt(msg, privateKey);
					this.comunicador.comunicar(msg, this);
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}

	}

	public void sendToCliente(String msg) {
		try {
			System.out.println("---------------------------------");
			System.out.println("Mensagem: " + msg);
			msg = AES.encrypt(msg, privateKey);
			bfw.write(msg + "\r\n");
			bfw.flush();
			System.out.println("Mensagem encriptada: " + msg);
			System.out.println("---------------------------------");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setComunicador(IComunicacao comunicador) {
		this.comunicador = comunicador;
	}
}
