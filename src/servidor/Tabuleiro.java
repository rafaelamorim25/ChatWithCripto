package servidor;

import servidor.ICoordenada;

public class Tabuleiro {
	
	private int tabuleiro[][];
	
	private final int LINHAS = 6;
	private final int COLUNAS= 7;
	
	public final int PLAYER1 = 1;
	public final int PLAYER2 = 2;
	public final int DISPONIVEL = 3;
	public final int INDISPONIVEL = 0;
	public final int INVALIDO = -1;
	
	public Tabuleiro() {
		tabuleiro = new int[LINHAS][COLUNAS];
		this.initTabuleiro();
	}
	
	private void initTabuleiro() {
		for (int i = 0; i < COLUNAS; i++) {
			tabuleiro[5][i] = DISPONIVEL;
		}
	}
	
	public void restart() {
		for(int i = 0; i < LINHAS; i++) {
			for(int j = 0; j < COLUNAS; j++) {
				tabuleiro[i][j] = INDISPONIVEL;
			}
		}
		this.initTabuleiro();
	}
	
	public void setJogada(ICoordenada coordenada, final int player) throws IndexOutOfBoundsException{
		tabuleiro[coordenada.getLinha()][coordenada.getColuna()] = player;
		coordenada.setLinha(coordenada.getLinha()-1);
		atualizarDisponivel(coordenada);
	}
	
	private void atualizarDisponivel(ICoordenada coordenada) {
		if(coordenada.coordenadaIsValida()) {
			tabuleiro[coordenada.getLinha()][coordenada.getColuna()] = DISPONIVEL;
		}
	}
	
	public final int getPosicao(ICoordenada coordenada) {
		if(coordenada.coordenadaIsValida()) {
			return tabuleiro[coordenada.getLinha()][coordenada.getColuna()];
		}
		
		return INVALIDO;
	}
	
	public void printTabuleiro() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				System.out.print(tabuleiro[i][j] + " ");
			}
			System.out.println();
		}
	}
}
