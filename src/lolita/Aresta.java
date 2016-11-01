package lolita;

public class Aresta {
	
	private Rotulo rotulo;
	private Vertice origem;
	private Vertice destino;
	
	public Aresta(Rotulo rotulo, Vertice verticeI, Vertice verticeJ) {
		this.setRotulo(rotulo);
		this.setOrigem(verticeI);
		this.setDestino(verticeJ);
	}

	public Rotulo getRotulo() {
		return rotulo;
	}

	public void setRotulo(Rotulo rotulo) {
		this.rotulo = rotulo;
	}

	public Vertice getDestino() {
		return destino;
	}

	public void setDestino(Vertice destino) {
		this.destino = destino;
	}

	public Vertice getOrigem() {
		return origem;
	}

	public void setOrigem(Vertice origem) {
		this.origem = origem;
	}

}
