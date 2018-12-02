package testes;

import java.util.Scanner;

import servidor.ControladorTabuleiro;
import servidor.CoordenadaTabuleiro;

public abstract class Testes {
	
	public void teste1 () {
		ControladorTabuleiro controladorTabuleiro = new ControladorTabuleiro(); 
		Scanner ler = new Scanner(System.in);
		
		while(!controladorTabuleiro.verificarGanhador()) {
			System.out.println(controladorTabuleiro.jogar(ler.nextInt(), controladorTabuleiro.getVez()));
			controladorTabuleiro.getTabuleiro().printTabuleiro();
		}
		
		ler.close();
	}

	public static void main(String[] args) {
		
		String a = "Mensagem: terra";
		
		if(a.contains("Mensagem:")) {
			System.out.println("tem");
		}else {
			System.out.println("n tem");
		}
	}
}
