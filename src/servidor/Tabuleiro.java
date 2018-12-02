package servidor;

import servidor.CoordenadaTabuleiro;

public class Tabuleiro {
	
	public static final int limiteVertical = 6;
	public static final int limiteHorizontal = 7;
	
	public static final int INVALIDO = -1;
	public static final int INDISPONIVEL = 0;
	public static final int PLAYER1 = 1;
	public static final int PLAYER2 = 2;
	public static final int DISPONIVEL = 3;
	
	private int tabuleiro[][];
	
	public Tabuleiro() {
		tabuleiro = new int[limiteVertical][limiteHorizontal];
		this.initTabuleiro();
	}
	
	private void initTabuleiro() {
		for (int i = 0; i < limiteHorizontal ; i++) {
			tabuleiro[5][i] = DISPONIVEL;
		}
	}
	
	public void restart() {
		for(int i = 0; i < limiteVertical ; i++) {
			for(int j = 0; j < limiteHorizontal ; j++) {
				tabuleiro[i][j] = INDISPONIVEL;
			}
		}
		this.initTabuleiro();
	}
	
	public void setJogada(CoordenadaTabuleiro coordenada, final int player) throws IndexOutOfBoundsException{
		tabuleiro[coordenada.getLinha()][coordenada.getColuna()] = player;
		CoordenadaTabuleiro aux = new CoordenadaTabuleiro(coordenada.getLinha() - 1, coordenada.getColuna());
		atualizarDisponivel(aux);
	}
	
	private void atualizarDisponivel(CoordenadaTabuleiro coordenada) {
		if(coordenada.isValida()) {
			tabuleiro[coordenada.getLinha()][coordenada.getColuna()] = DISPONIVEL;
		}
	}
	
	public int getPosicao(CoordenadaTabuleiro coordenada) {
		if(coordenada.isValida()) {
			return tabuleiro[coordenada.getLinha()][coordenada.getColuna()];
		}
		return INVALIDO;
	}
	
	public int getLinhaDisponivel(int coluna) throws IndexOutOfBoundsException{
		for(int linha = limiteVertical - 1; linha >= 0 ; linha--) {
			if(tabuleiro[linha][coluna] == DISPONIVEL) {
				return linha;
			}
		}
		return INVALIDO;
	}
	
	public void printTabuleiro() {
		for (int i = 0; i < 6 ; i++) {
			for (int j = 0; j < 7 ; j++) {
				System.out.print(tabuleiro[i][j] + " ");
			}
			System.out.println();
		}
	}
}
