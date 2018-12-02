package servidor;

import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SocketServer {

	private static ServerSocket server;
	
	public static void main(String[] args) {
		try {
			JLabel lblMessage = new JLabel("Porta do Servidor:");
			JTextField txtPorta = new JTextField("12345");
			Object[] texts = { lblMessage, txtPorta };
			JOptionPane.showMessageDialog(null, texts);
			server = new ServerSocket(Integer.parseInt(txtPorta.getText()));
			JOptionPane.showMessageDialog(null, "Servidor ativo na porta: " + txtPorta.getText());
			
			while (true) {
				Partida partida;
				partida = new Partida();
				System.out.println("Aguardando conexão...");
				Socket con = server.accept();
				Jogador jogador = new Jogador(con, Tabuleiro.PLAYER1);
				partida.addPlayer1(jogador);
				jogador.start();
				System.out.println("Player 1 conectado...");
				con = server.accept();
				jogador = new Jogador(con, Tabuleiro.PLAYER2);
				partida.addPlayer2(jogador);
				jogador.start();
				System.out.println("Player 2 conectado...");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
