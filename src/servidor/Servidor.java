package servidor;

import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Servidor {

	private static ServerSocket server;
	
	public static void main(String[] args) {
		try {

			Integer porta = EscolherPorta.porta(); //Escolhe a porta
			server = new ServerSocket(porta); //Instancia o servidor
			JOptionPane.showMessageDialog(null, "Servidor ativo na porta: " + porta); //Mensagem que o servidor est� ativo
			
			while (true) {
				
				Controlador controlador  = new Controlador();	//Instancia o controlador
				
				//Recebe a primeira conexao
				System.out.println("Aguardando conex�o...");
				Socket conexao = server.accept(); //Aceita a requisi��o de conex�o
				Usuario usuario = new Usuario(conexao); //Cria um objeto para representar o usu�rio no servidor passando a conex�o
				controlador.addUser1(usuario); //Indica para o controlador da existencia do novo usu�rio
				usuario.start();
				System.out.println("User 1 conectado...");
				
				//Recebe a segunda conexao
				conexao = server.accept();
				usuario = new Usuario(conexao);
				controlador.addUser2(usuario);
				usuario.start();
				System.out.println("User 2 conectado...");
			}

		} catch (Exception e) {
			System.out.println("Erro ao iniciar servidor");
			System.exit(0);
		}
	}
}
