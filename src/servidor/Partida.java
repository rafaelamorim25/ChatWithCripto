package servidor;

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
	
	public void addPlayer1(Jogador bfw) {
		player1 = bfw;
		player1.setComunicador(this);
		player1.sendToJogador("Partida ainda não começou, espere");
	}
	
	public void addPlayer2(Jogador bfw) {
		player2 = bfw;
		player2.setComunicador(this);
		statusPartida = PARTIDAEMANDAMENTO;
	}

	@Override
	public void comunicar(String msg, Jogador jogador) {
		
		System.out.println("chegou mensagem: " + msg);
		
		if(statusPartida==PARTIDAEMANDAMENTO) {
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
			jogador.sendToJogador("partida já acabou ou nem cmç");
		}

	}
	
	public void enviarMensagem(String msg, Jogador jogador) {
		
		System.out.println(msg+"cabou");
		
		if (jogador.equals(player1)) {
			System.out.println("Enviada ao player 1");
			player2.sendToJogador(msg); 
		}else {
			System.out.println("Enviada ao player 2");
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
		}
		
		System.out.println(tabuleiro.verificarGanhador());
	}
	
	public String[] separarMensagem(String msg) {
		String[] mensagem;
		mensagem = msg.split(" ");
		return mensagem;
	}
}
