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
		try { //A partir da conexao recebida, instancia e armazena os objetos que serão responsáveis por enviar e receber mensagens para o cliente
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

		escutar();

	}
	
	public void escutar() {
		
		enviarChavePublicaAssimetrica();

		String msg = "";
		while (msg != null) { //Fica recebendo as mensagens

			msg = receberMensagem();
			
			if (firstMessage) { //A primeira mensagem recebida é referente a configuração da chave privada
				
				armazenarChavePrivadaSimetrica(msg);

			} else { //Para os outros casos, desencripta-se a mensagem e envia ao controlador

				desencriptaEnviaReceptor(msg); 
			}
		}
		
	}
	
	public void enviarChavePublicaAssimetrica() {
		
		keys = RSA.generateKey(); //Gera e armazena o par de chaves RSA (Criptografia Assimétrica)
		
		try {
			System.out.println("---------------------------------");
			System.out.println("Chave RSA pública que será enviada: " + keys.getE() + " " + keys.getN());
			System.out.println("---------------------------------");
			
			bfw.write(keys.getE() + " " + keys.getN() + "\r\n"); //Envia a chave pública ao cliente
			bfw.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String receberMensagem() {
		
		try {
			return bfr.readLine(); //Recebe a mensagem
		} catch (IOException e) {
			System.out.println("Um usuário saiu");
			System.out.println("Encerrando servidor");
			System.exit(0);
		}
		return null;
	}
	
	public void armazenarChavePrivadaSimetrica(String msg) {
		System.out.println("---------------------------------");
		System.out.println("Chave AES recebida criptografada com RSA: " + msg);
		

		msg = RSA.decriptar(msg, keys); //Decripta o par de chaves
		String[] mensagem = msg.split(" ");
		privateKey = new AESKey(mensagem[0], mensagem[1]); //Armazena as chaves
		firstMessage = false;
		
		System.out.println("Chave AES recebida sem criptografia: " + privateKey);
		System.out.println("---------------------------------");
	}
	
	public void desencriptaEnviaReceptor(String msg) {
		
		try {
			msg = AES.decrypt(msg, privateKey);	//Decripta a mensagem
			
			this.comunicador.comunicar(msg, this);//Manda a mensagem ao controlador que irá enviar ao receptor
		} catch (Exception e) {

			e.printStackTrace();
		}
		
	}

	public void enviarParaCliente(String msg) {
		try {
			System.out.println("---------------------------------");
			System.out.println("Mensagem: " + msg);
			
			
			msg = AES.encrypt(msg, privateKey); //Criptografa
			bfw.write(msg + "\r\n"); //Envia
			bfw.flush();
			
			
			System.out.println("Mensagem encriptada: " + msg);
			System.out.println("---------------------------------");

		} catch (Exception e) {
			
			System.out.println("Erro ao enviar mensagem");
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void setComunicador(IComunicacao comunicador) {
		this.comunicador = comunicador;
	}
}
