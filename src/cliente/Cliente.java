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
	private JanelaMultiPlayerOnline janela;
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
		janela = new JanelaMultiPlayerOnline(this);
	}

	public void enviarMensagem(String str) {
		janela.getPainelChat().mostrarMensagem(conexao.getNome() + ": " + str);
		try {
			System.out.println("---------------------------------");
			System.out.println("Mensagem descriptografada com AES que será enviada: " + conexao.getNome() + ": " + str);
			
			str = AES.encrypt(conexao.getNome() + ": " + str, privateKey);
			
			conexao.enviar(str);
			
			System.out.println("Mensagem descriptografada com AES que será enviada: " + str);
			System.out.println("---------------------------------");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void controle(String msg) {

		if (firstMessage) {
			
			System.out.println("---------------------------------");
			System.out.println("Chave RSA pública que chegou do servidor: " + msg);
			System.out.println("---------------------------------");
			
			String[] mensagem = msg.split(" ");
			publicKey.setE(new BigInteger(mensagem[0]));
			publicKey.setN(new BigInteger(mensagem[1]));
			privateKey = AES.generateKey();
			String aux = RSA.encriptar(privateKey.toString(), publicKey);
			conexao.enviar(aux);
			firstMessage = false;
			
			System.out.println("---------------------------------");
			System.out.println("Chave AES que será enviada sem criptografia: " + privateKey);
			System.out.println("Chave AES que será enviada com criptografia: " + aux);
			System.out.println("---------------------------------");
			

		} else {

			try {
				System.out.println("---------------------------------");
				System.out.println("Mensagem criptografada com AES que chegou: " + msg);
				
				msg = AES.decrypt(msg, privateKey);
				
				System.out.println("Mensagem descriptografada com AES que chegou: " + msg);
				System.out.println("---------------------------------");
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			janela.getPainelChat().mostrarMensagem(msg);

		}
	}

	@Override
	public void comunicar(String msg, Usuario jogador) throws IOException {
	}

	@Override
	public void comunicar(String msg) throws IOException {
		controle(msg);
	}
}
