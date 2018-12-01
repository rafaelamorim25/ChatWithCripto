package servidor;

public abstract class CoordenadaGeneric implements ICoordenada{
	
	private int linha;
	private int coluna;
	
	public CoordenadaGeneric() {
	
	}
	
	public CoordenadaGeneric(int x, int y) {
		this.linha = x;
		this.coluna = y;
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	public void setLinha(int linha) {
		this.linha = linha;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}

	@Override
	public String toString() {
		return linha + " " + coluna;
	}
}
