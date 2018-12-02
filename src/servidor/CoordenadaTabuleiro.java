package servidor;

public class CoordenadaTabuleiro{
	
	private int linha;
	private int coluna;
	
	public CoordenadaTabuleiro() {}
	
	public CoordenadaTabuleiro(int x, int y) {
		this.linha = x;
		this.coluna = y;
	}

	public int getLinha() { return linha;}

	public int getColuna() { return coluna;}
	
	public void setLinha(int linha) {this.linha = linha;}

	public void setColuna(int coluna) {this.coluna = coluna;}
	
	public static boolean linhasIsValida(int linha) {
		return (linha >= 0 && linha < Tabuleiro.limiteVertical)?true:false;
	}
	
	public static boolean colunaIsvalida(int coluna) {
		return (coluna >= 0 && coluna < Tabuleiro.limiteHorizontal)?true:false;
	}
	
	public boolean isValida() {
		return (CoordenadaTabuleiro.colunaIsvalida(this.coluna) && CoordenadaTabuleiro.linhasIsValida(this.linha))?true:false;
	}
	
	public static CoordenadaTabuleiro coordenadaInvalida() {
		return new CoordenadaTabuleiro(-1, -1);
	}
	
	@Override
	public String toString() {
		return linha + " " + coluna;
	}
}
