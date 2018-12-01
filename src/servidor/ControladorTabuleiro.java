package servidor;

public class ControladorTabuleiro {
	
	private Coordenada jogadaAtual;
	private int playerAtual;
	private int vez;
	private Tabuleiro tabuleiro;
	
	public ControladorTabuleiro() {
		tabuleiro = new Tabuleiro();
		vez = tabuleiro.PLAYER1;
	}
	
	public boolean jogar(int coluna, final int player) {
		
		this.jogadaAtual = this.criarCoordenada(coluna);
		this.playerAtual = player;
		
		if(jogadaAtual.isValida()) {
			tabuleiro[jogadaAtual.getLinha()][jogadaAtual.getColuna()] = player;
			if(player==PLAYER1) {
				vez = PLAYER2;
			}else {
				vez = PLAYER1;
			}
			atualizarDisponivel(new Coordenada(jogadaAtual.getLinha()-1, jogadaAtual.getColuna()));
			return true;
		}
		
		return false;
	}
	
	public Coordenada criarCoordenada (int coluna) {
		
		if(coluna >= 1 && coluna <= 7) {
			for(int linha = 5; linha >= 0; linha--) {
				if(tabuleiro[linha][coluna-1]==DISPONIVEL) {
					return new Coordenada(linha, coluna-1);
				}
			}
		}
		
		return new Coordenada(-1, coluna);
	}
	
	public boolean Jogar () {
		
	}
	
	public int getVez() {
		return vez;
	}
	
	public Coordenada getUltimaJogada () {
		return jogadaAtual;
	}
	
	public void verificarGanhador() {
		if(verificarLinha() || verificarColuna() || verificarDiagonalDireita() || verificarDiagonalEsquerda()) {
			System.out.println("O player "+ playerAtual + " ganhou");
		}else {
			System.out.println("Ainda não tem ganhador");
		}
	}
	
	public boolean verificarLinha () {
		
		int linha = jogadaAtual.getLinha();
		int count = 0;
		
		for(int coluna = 0; coluna < 7; coluna++) {
			if (tabuleiro[linha][coluna]==playerAtual) {
				count++;
			}else {
				count = 0;
			}
			
			if(count==4) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean verificarColuna () {
		
		int coluna = jogadaAtual.getColuna();
		int count = 0;
		
		for(int linha = 5; linha >= 0; linha--) {
			if (tabuleiro[linha][coluna]==playerAtual) {
				count++;
			}else {
				count = 0;
			}
			
			if(count==4) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean verificarDiagonalDireita () {
		
		int coluna = jogadaAtual.getColuna();
		int linha = jogadaAtual.getLinha();
		int count = 0;
		
		if(coluna + 3 > 6) {
			coluna = 6;
		}else {
			coluna = coluna + 3;
		}
		
		if (linha + 3 > 5) {
			linha = 5;
		}else {
			linha = linha + 3;
		}
		
		for (;coluna >= 0 && linha >= 0; coluna--, linha--) {
			
			if (tabuleiro[linha][coluna]==playerAtual) {
				count++;
			}else {
				count = 0;
			}
			
			if(count==4) {
				return true;
			}
			
		}
		
		return false;
	}
	
	public boolean verificarDiagonalEsquerda () {
		
		int coluna = jogadaAtual.getColuna();
		int linha = jogadaAtual.getLinha();
		int count = 0;
		
		if(coluna - 3 < 0) {
			coluna = 0;
		}else {
			coluna = coluna - 3;
		}
		
		if (linha + 3 > 5) {
			linha = 5;
		}else {
			linha = linha + 3;
		}
		
		for (;coluna <= 6 && linha >= 0; coluna++, linha--) {
			
			if (tabuleiro[linha][coluna]==playerAtual) {
				count++;
			}else {
				count = 0;
			}
			
			if(count==4) {
				return true;
			}
			
		}
		
		return false;
	}
}
