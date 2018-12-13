package servidor;

import java.io.IOException;

public class Partida implements IComunicacao {
	
	private static final int PARTIDANAOINICIADA = -1;
	private static final int PARTIDAEMANDAMENTO = 0;
	private static final int PARTIDAACABOU = 1;
	
	private ControladorTabuleiro tabuleiro;
	private Jogador player1;
	private Jogador player2;
	private int statusPartida;

	public Partida () {
		tabuleiro = new ControladorTabuleiro();
		statusPartida = PARTIDANAOINICIADA; 
	}
	
	public void addPlayer1(Jogador jogador) {
		player1 = jogador;
		player1.setComunicador(this);
	}
	
	public void addPlayer2(Jogador jogador) {
		player2 = jogador;
		player2.setComunicador(this);
		statusPartida = PARTIDAEMANDAMENTO;
		player1.sendToJogador("A partida começou, sua vez de jogar");
	}

	@Override
	public void comunicar(String msg, Jogador jogador) {
		
		System.out.println("chegou mensagem: " + msg);
		
		if(msg.contains("Encerrar ")) {
			desconect(jogador);
		} else if(statusPartida==PARTIDAEMANDAMENTO) {
			if(msg.contains("Mensagem: ")) {
				this.enviarMensagem(msg, jogador);
				System.out.println("Entrou na msg");
			}else if(msg.contains("Jogada: ")) {
				this.tratarJogada(msg, jogador);
			}else {
				jogador.sendToJogador("error");
				System.out.println("Mensagem enviada por aliens não pode ser decodificada");
			}	
		}else {
			jogador.sendToJogador("A partida ainda não começou");
		}

	}
	
	private void desconect(Jogador jogador) {
		if(jogador.getPlayer()==Tabuleiro.PLAYER1) {
			player2.sendToJogador("O outro player saiu e a partida foi desconectada");
		}else {
			player1.sendToJogador("O outro player saiu e a partida foi desconectada");
		}
		
		player1.desconectar();
		player2.desconectar();
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
			player1.sendToJogador("Pinte: " + tabuleiro.getJogada().toString() + " " + color + " " + trave1);
			player2.sendToJogador("Pinte: " + tabuleiro.getJogada().toString() + " " + color + " " + trave2);
			
		}else {
			jogador.sendToJogador("Jogue novamente ou espere a sua vez");
		}
		
		if(tabuleiro.verificarGanhador()) {
			jogador.sendToJogador("Você ganhou");
			Jogador j = (jogador==player1) ? player2 : player1;
			j.sendToJogador("Você perdeu");
			statusPartida = PARTIDAACABOU;
			this.reiniciarPartida();
		}
	}
	
	private void reiniciarPartida() {
		statusPartida = PARTIDANAOINICIADA;
		tabuleiro.recomecar();
		player1.sendToJogador("Reiniciar: " + ((tabuleiro.getVez()==Tabuleiro.PLAYER1) ? 1 : 0));
		player2.sendToJogador("Reiniciar: " + ((tabuleiro.getVez()==Tabuleiro.PLAYER1) ? 0 : 1));
		statusPartida = PARTIDAEMANDAMENTO;
	}
	
	private String[] separarMensagem(String msg) {
		String[] mensagem;
		mensagem = msg.split(" ");
		return mensagem;
	}

	@Override
	public void comunicar(String msg) throws IOException {

	}
}
