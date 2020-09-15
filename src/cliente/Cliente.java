package cliente;

import java.io.IOException;
import java.math.BigInteger;

import javax.swing.JFrame;

import criptografia.AES;
import criptografia.AESKey;
import criptografia.RSA;
import criptografia.RSAKey;
import servidor.IComunicacao;
import servidor.Usuario;

public class Cliente extends JFrame implements IComunicacao {

	private static final long serialVersionUID = 1L;
	private Conexao conexao;
	private JanelaChat janela;
	private RSAKey publicKey = new RSAKey();
	private AESKey privateKey = new AESKey();
	private boolean firstMessage = true;

	public static void main(String[] args) throws IOException {
		@SuppressWarnings("unused")
		Cliente app = new Cliente();
	}

	public Cliente() throws IOException {
		conexao = new Conexao(this);
		Thread t = new Thread(conexao);
		t.start();
		janela = new JanelaChat(this);
	}
	
	@Override
	public void comunicar(String msg) throws IOException {
		controle(msg); //Da o devido tratamento do que deve ser feito com as mensagens
	}
	
	public void controle(String msg) {

		if (firstMessage) {
			
			gerarCriptografaEnviaChavePrivada(msg);
			
			firstMessage = false;

		} else {
			
			decriptaMostraMensagem(msg);

		}
	}
	
	public void gerarCriptografaEnviaChavePrivada(String msg) {
		
		System.out.println("---------------------------------");
		System.out.println("Chave RSA pública que chegou do servidor: " + msg);
		System.out.println("---------------------------------");
		
		String[] mensagem = msg.split(" ");
		publicKey.setE(new BigInteger(mensagem[0])); //Recebe a chave publica
		publicKey.setN(new BigInteger(mensagem[1])); //Recebe a chave publica
		privateKey = AES.generateKey(); //Gera a chave privada 
		String aux = RSA.encriptar(privateKey.toString(), publicKey); //Criptografa a chave privada com a pública
		conexao.enviar(aux); //Envia para o servidor
		
		System.out.println("---------------------------------");
		System.out.println("Chave AES que será enviada sem criptografia: " + privateKey);
		System.out.println("Chave AES que será enviada com criptografia: " + aux);
		System.out.println("---------------------------------");
	}
	
	public void decriptaMostraMensagem(String msg) {
		
		try {
			System.out.println("---------------------------------");
			System.out.println("Mensagem criptografada com AES que chegou: " + msg);
			
			msg = AES.decrypt(msg, privateKey); //Decripta
			
			System.out.println("Mensagem descriptografada com AES que chegou: " + msg);
			System.out.println("---------------------------------");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		janela.getPainelChat().mostrarMensagem(msg); //Manda apresentar na tela
		
	}

	public void criptografaMandaEnviarMensagem(String str) {
		
		janela.getPainelChat().mostrarMensagem(conexao.getNome() + ": " + str); //A mensagem que o cliente envia é mostrada para ele mesmo
		
		try {
			System.out.println("---------------------------------");
			System.out.println("Mensagem descriptografada com AES que será enviada: " + conexao.getNome() + ": " + str);
			
			str = AES.encrypt(conexao.getNome() + ": " + str, privateKey); //Encripta
			conexao.enviar(str); //Envia
			
			System.out.println("Mensagem descriptografada com AES que será enviada: " + str);
			System.out.println("---------------------------------");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@Override
	public void comunicar(String msg, Usuario jogador) throws IOException {
	}
}
