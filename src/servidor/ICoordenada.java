package servidor;

public interface ICoordenada {
	
	public int getLinha();

	public int getColuna();
	
	public void setLinha(int linha);

	public void setColuna(int coluna);
	
	public boolean coordenadaIsValida();
}
