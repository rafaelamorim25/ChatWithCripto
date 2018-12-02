package servidor;

public class ControladorTabuleiro {
	
	private int vez;
	private Tabuleiro tabuleiro;
	private int quantidadeJogadas;
	private CoordenadaTabuleiro ultimaJogada;
	private CoordenadaTabuleiro aux;
	
	public ControladorTabuleiro() {
		tabuleiro = new Tabuleiro();
		vez = Tabuleiro.PLAYER1;
		ultimaJogada = new CoordenadaTabuleiro();
		aux = new CoordenadaTabuleiro();
	}
	
	private CoordenadaTabuleiro criarCoordenada(int coluna) {
		int linha = tabuleiro.getLinhaDisponivel(coluna);
		return new CoordenadaTabuleiro(linha, coluna);
	}
	
	public boolean jogar(int coluna, final int player) {
		coluna = coluna - 1;
		CoordenadaTabuleiro coordenada = (CoordenadaTabuleiro.colunaIsvalida(coluna))?criarCoordenada(coluna):CoordenadaTabuleiro.coordenadaInvalida();
		if(coordenada.isValida() && vez == player) {
			System.out.println(coordenada.toString());
			tabuleiro.setJogada(coordenada, player);
			ultimaJogada = coordenada;
			vez = (player==Tabuleiro.PLAYER1)?Tabuleiro.PLAYER2:Tabuleiro.PLAYER1;
			quantidadeJogadas++;
			return true;
		}
		return false;
	}
	
	public int getVez() {return vez;}
	
	public boolean verificarGanhador() {
		if(quantidadeJogadas < 7 && !ultimaJogada.isValida()) {
			return false;
		}
		return (verificarLinha() || 
				verificarColuna() || 
				verificarDiagonalDireita() || 
				verificarDiagonalEsquerda())?true:false;
	}
	
	public boolean verificarLinha () {
		int linha = ultimaJogada.getLinha(), count = 0;
		
		for(int coluna = 0; coluna < 7; coluna++) {
			aux.setLinha(linha); aux.setColuna(coluna);
			
			count = ((tabuleiro.getPosicao(aux) == 
					((vez == Tabuleiro.PLAYER1) ? 
					Tabuleiro.PLAYER2 : Tabuleiro.PLAYER1)) ?
				count + 1 : 0);
		
			if(count==4) {
				return true;
			}
		}
		return false;
	}
	
	public boolean verificarColuna () {
		
		int coluna = ultimaJogada.getColuna(), count = 0;
		
		for(int linha = 5; linha >= 0; linha--) {
			aux.setLinha(linha); aux.setColuna(coluna);
			
			count = (tabuleiro.getPosicao(aux) == 
					((vez == Tabuleiro.PLAYER1) ? 
					Tabuleiro.PLAYER2 : Tabuleiro.PLAYER1)) ?
				count + 1 : 0;
			
			if(count==4) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean verificarDiagonalDireita () {
		
		int coluna = ultimaJogada.getColuna();
		int linha = ultimaJogada.getLinha();
		int count = 0;
		
		coluna = (coluna + 3 > 6) ? 6 : coluna + 3;

		linha = (linha + 3 > 5) ? 5 : linha + 3;
		
		for (;coluna >= 0 && linha >= 0; coluna--, linha--) {
			
			count = (tabuleiro.getPosicao(aux) == 
					((vez == Tabuleiro.PLAYER1) ? 
					Tabuleiro.PLAYER2 : Tabuleiro.PLAYER1)) ?
				count + 1 : 0;
			
			if(count==4) {
				return true;
			}
			
		}
		
		return false;
	}
	
	public boolean verificarDiagonalEsquerda () {
		
		int coluna = ultimaJogada.getColuna();
		int linha = ultimaJogada.getLinha();
		int count = 0;
		
		coluna = (coluna - 3 < 0) ? 0 : coluna - 3;
		
		linha = (linha + 3 > 5) ? 5 : linha + 3;
	
		for (;coluna <= 6 && linha >= 0; coluna++, linha--) {
			
			count = (tabuleiro.getPosicao(aux) == 
					((vez == Tabuleiro.PLAYER1) ? 
					Tabuleiro.PLAYER2 : Tabuleiro.PLAYER1)) ?
				count + 1 : 0;
			
			if(count==4) {
				return true;
			}
			
		}
		
		return false;
	}
	
	public Tabuleiro getTabuleiro() {
		return this.tabuleiro;
	}
	
	public CoordenadaTabuleiro getJogada() {
		return this.ultimaJogada;
	}
}
