package servidor;

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
		}else {
			jogador.sendToJogador("error");
			System.out.println("Mensagem enviada por aliens não pode ser decodificada");
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
			jogador.sendToJogador("Jogue novamente");
		}
		
		if(tabuleiro.verificarGanhador()) {
			jogador.sendToJogador("Alguém ganhou");
		}
		
		System.out.println(tabuleiro.verificarGanhador());
	}
	
	public String[] separarMensagem(String msg) {
		String[] mensagem;
		mensagem = msg.split(" ");
		return mensagem;
	}
}
