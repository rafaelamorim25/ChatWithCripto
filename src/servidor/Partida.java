package servidor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Partida implements IComunicacao {
	
	private ControladorTabuleiro tabuleiro;
	private Jogador player1;
	private Jogador player2;

	public Partida () {
		tabuleiro = new ControladorTabuleiro();
	}
	
	public void addPlayer1(Jogador bfw) {
		player1 = bfw;
		player1.setComunicador(this);
	}
	
	public void addPlayer2(Jogador bfw) {
		player2 = bfw;
		player2.setComunicador(this);

	}

	@Override
	public void comunicar(String msg, Jogador jogador) {
		
		System.out.println("chegou mensagem: " + msg);
		
		if(msg.contains("Mensagem: ")) {
			this.enviarMensagem(msg, jogador);
			System.out.println("Entrou na msg");
		}else if(msg.contains("Jogada: ")) {
			this.tratarJogada(msg, jogador);
			System.out.println("Entrou na jogada");
		}else {
			jogador.sendToJogador("error");
			System.out.println("Mensagem enviada por aliens não pode ser decodificada");
		}
	}
	
	public void enviarMensagem(String msg, Jogador jogador) {
		
		if (jogador.equals(player1)) {
			player2.sendToJogador(msg); 
		}else {
			player1.sendToJogador(msg);
		}
	}
	
	public void tratarJogada(String msg, Jogador jogador) {
		String[] jogada = separarMensagem(msg);
		
		boolean jogadaDeuCerto = tabuleiro.jogar(Integer.parseInt(jogada[1]), jogador.getPlayer());
		
		if(jogadaDeuCerto) {
			int color, trave1, trave2;
			if(jogador.getPlayer()==Tabuleiro.PLAYER1) {
				 color = 0;
				 trave1 = 1;
				 trave2 = 0;
			}else {
				color = 1;
				trave1 = 0;
				trave2 = 1;
			}
			tabuleiro.getTabuleiro().printTabuleiro();
			player1.sendToJogador("Pinte: " + tabuleiro.getJogada().toString() + " " + color + " " + trave1 + "\r\n");
			player2.sendToJogador("Pinte: " + tabuleiro.getJogada().toString() + " " + color + " " + trave2 + "\r\n");
		}else {
			jogador.sendToJogador("Jogue novamente\r\n");
		}
	}
	
	public String[] separarMensagem(String msg) {
		String[] mensagem;
		mensagem = msg.split(" ");
		return mensagem;
	}
}
