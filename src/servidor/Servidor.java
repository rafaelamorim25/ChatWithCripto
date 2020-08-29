package servidor;

import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Servidor {

	private static ServerSocket server;
	
	/*
	 *Aqui ele mostra em qual porta o server vai se conectar, cria um partida, verifica se 
	 *conectaram no socket , cria  partida cria um novo jogador e add na partida, dai espera 
	 *uma segunda conexao
	 */
	
	public static void main(String[] args) {
		try {
			JLabel lblMessage = new JLabel("Porta do Servidor:");
			JTextField txtPorta = new JTextField("12345");
			Object[] texts = { lblMessage, txtPorta };
			JOptionPane.showMessageDialog(null, texts);
			server = new ServerSocket(Integer.parseInt(txtPorta.getText()));
			JOptionPane.showMessageDialog(null, "Servidor ativo na porta: " + txtPorta.getText());
			
			while (true) {
				Controlador partida;
				partida = new Controlador();
				System.out.println("Aguardando conexão...");
				Socket con = server.accept();
				Usuario jogador = new Usuario(con);
				partida.addUser1(jogador);
				jogador.start();
				System.out.println("User 1 conectado...");
				con = server.accept();
				jogador = new Usuario(con);
				partida.addUser2(jogador);
				jogador.start();
				System.out.println("User 2 conectado...");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
