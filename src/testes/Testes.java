package testes;

import servidor.CoordenadaTabuleiro;
import servidor.ICoordenada;
import servidor.Tabuleiro;

public abstract class Testes {

	public static void main(String[] args) {
		Tabuleiro t = new Tabuleiro();
		t.printTabuleiro();
		t.restart();
		t.printTabuleiro();
		System.out.println(t.getPosicao(new CoordenadaTabuleiro(5, 0)));
		t.setJogada(new CoordenadaTabuleiro(5, 0), t.PLAYER1);
		t.printTabuleiro();
	}
	
}
