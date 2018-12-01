package servidor;

public class CoordenadaTabuleiro extends CoordenadaGeneric {
	
	public CoordenadaTabuleiro() {
		super();
	}
	
	public CoordenadaTabuleiro(int linha, int coluna) {
		super(linha, coluna);
	}

	@Override
	public boolean coordenadaIsValida() {
		return (this.getLinha() < 6 && 
				this.getLinha() >= 0 &&
				this.getColuna() < 7 &&
				this.getColuna() >= 0)?true:false;
	}

}
