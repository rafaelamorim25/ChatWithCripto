package cliente;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Color;
import servidor.IComunicacao;
import servidor.Jogador;
import java.io.IOException;

public class Cliente extends JFrame implements IComunicacao {

	private static final long serialVersionUID = 1L;
	private PainelTabuleiro tab;
	private PainelChat chat;
	private Conexao conexao;
	private JanelaMultiPlayerOnline janela;

	public static void main(String[] args) throws IOException {
		Cliente app = new Cliente();
	}

	public Cliente() throws IOException {
		conexao = new Conexao(this);
		janela = new JanelaMultiPlayerOnline(this);
	}

	public void enviarJogada(int coluna) {
		conexao.enviar("Jogada: " + coluna);
	}
	
	public void enviarMensagem(String str) {
		chat.mostrarMensagem(conexao.getNome() + " -> " + str);
		conexao.enviar("Mensagem: " + conexao.getNome() + " -> " + str);
	}
	
	public void controle(String msg) {
		
		if(msg.contains("Pinte: ")) {
			realizarJogada(msg);
		} else if(msg.contains("Mensagem: ")) {
			String[] mensagem = msg.split(" ");
			
			StringBuilder str = new StringBuilder("");
			
			for(int i = 1; i < mensagem.length; i++) {
				str.append(mensagem[i]);
			}
			chat.mostrarMensagem(str.toString());
		} else {
			JOptionPane.showMessageDialog(null, msg);
		}
	}
	
	public void realizarJogada(String msg) {
		Color c;
		String jogada[] = msg.split(" ");
		
		c = (Integer.parseInt(jogada[3])==0) ? Color.blue : Color.red;
		
		Boolean travei = (Integer.parseInt(jogada[4])==1) ? false : true;
		tab.travar(travei);
		tab.pintar(Integer.parseInt(jogada[1]), Integer.parseInt(jogada[2]), c);
	}
		
	@Override
	public void comunicar(String msg, Jogador jogador) throws IOException {}

	@Override
	public void comunicar(String msg) throws IOException {
		controle(msg);
	}
}
