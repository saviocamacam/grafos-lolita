package lolita;

import java.text.Collator;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Grafo {
	
	private LinkedList<Aresta> conjuntoArestas;
	private LinkedList<Vertice> conjuntoVertices;
	private LinkedList<Rotulo> conjuntoRotulos;
	private int totalVertices;
	private int totalRotulos;
	private List<String> linhasBrutas;
	private int incidenceMatrix[][];
	private LinkedList<LinkedList<Integer>> adjacenceList;
	private LinkedList<int[][]> listOfSubgraphs;
	private int totalEdges = 0;
	
	public Grafo(int totalVertices, int totalRotulos) {
		this.conjuntoArestas = new LinkedList<Aresta>();
		this.conjuntoVertices = new LinkedList<Vertice>();
		this.conjuntoRotulos = new LinkedList<Rotulo>();
		this.setTotalVertices(totalVertices);
		this.setTotalRotulos(totalRotulos);
		this.incidenceMatrix = new int[totalVertices][totalVertices];
		this.listOfSubgraphs = new LinkedList<>();
		this.adjacenceList = new LinkedList<>();
		initializeAdjacenceList();
	}
	
	private void initializeAdjacenceList() {
		int i;
		for(i=0; i<totalVertices;i++) {
			adjacenceList.add(new LinkedList<>());
		}
	}

	public void matrixGenerate() {
		int i, j;
		
		for(i=0; i<totalVertices -1 ; i++) {
			Arrays.fill(incidenceMatrix[i], i, totalVertices, -1);
			String[] stringRotulos = linhasBrutas.get(i).split(" ");
			
			for(j=0; j<stringRotulos.length; j++) {
				this.incidenceMatrix[i+1][j] = Integer.valueOf(stringRotulos[j]);
				
				if(incidenceMatrix[i+1][j] != totalRotulos) {
					totalEdges  += 1;
					adjacenceList.get(i+1).add(j);
					adjacenceList.get(j).add(i+1);
					
					Rotulo rotulo = new Rotulo(incidenceMatrix[i+1][j]);
					
					if(!conjuntoRotulos.contains(rotulo))
						conjuntoRotulos.add(rotulo);
				}
			}
		}
		//printAdjacenceMatrix();
		//System.out.println("Total de Arestas: " + totalEdges);
		
		incidenceMatrix[totalVertices-1][totalVertices-1] = -1;
	}
	
	public void printAdjacenceMatrix() {
		int i, j;
		
		for(i=0; i<totalVertices;i++) {
			System.out.print(i + ": ");
			for(j=0; j<adjacenceList.get(i).size(); j++) {
				System.out.print(adjacenceList.get(i).get(j)+" ");
			}
			System.out.println("");
		}
		System.out.println("");
	}

	public void generateMLST() {
		getSubGraphs();
		//System.out.println("<<<<<<<<<<<<<");
		printMatrix(incidenceMatrix);
	}
	
	private void getSubGraphs() {
		int i;
		for(i=0; i<totalRotulos;i++) {
			listOfSubgraphs.add(getSubGraphOf(i));
		}
	}

	private int[][] getSubGraphOf(int label) {
		//System.out.println("Subgraph Label: " + label);
		int i, j;
		int subGraph[][] = new int[totalVertices][totalVertices];
		subGraph = Arrays.stream(incidenceMatrix)
	             .map((int[] row) -> row.clone())
	             .toArray((int length) -> new int[length][]);
		
		for(i=0;i<totalVertices; i++) {
			for(j=0; j<totalVertices; j++) {
				if(subGraph[i][j] != -1 && subGraph[i][j] != label)
					subGraph[i][j] = totalRotulos;
			}
		}
		//printMatrix(subGraph);
		return subGraph;
	}

	public void geraGrafo() {
		int i, j;
		
		for(i=0 ; i < totalVertices - 1 ; i++) {
			//LinkedList<Rotulo> rotulos = new LinkedList<>();
			String[] stringRotulos = linhasBrutas.get(i).split(" ");
			Vertice verticeI = new Vertice(i+1);
			
			if(!conjuntoVertices.contains(verticeI)) {
				conjuntoVertices.add(verticeI);
			}
			
			for(j=0 ; j < stringRotulos.length ; j++) {
				Vertice verticeJ = new Vertice(j);
				Rotulo rotulo = new Rotulo(Integer.valueOf(stringRotulos[j]));
				
				if(!conjuntoRotulos.contains(rotulo))
					conjuntoRotulos.add(rotulo);
				
				if(!conjuntoVertices.contains(verticeJ)) {
					conjuntoVertices.add(verticeJ);
				}
				
				if(rotulo.getValor() != this.totalRotulos) {
					Aresta aresta = new Aresta(rotulo, verticeI, verticeJ);
					conjuntoVertices.get(conjuntoVertices.indexOf(verticeI)).getConjuntoLocalAresta().add(aresta);
					conjuntoArestas.add(aresta);
				}
			}
		}
	}

	public LinkedList<Aresta> getConjuntoArestas() {
		return conjuntoArestas;
	}

	public void setConjuntoArestas(LinkedList<Aresta> conjuntoArestas) {
		this.conjuntoArestas = conjuntoArestas;
	}

	public LinkedList<Vertice> getConjuntoVertices() {
		return conjuntoVertices;
	}

	public void setConjuntoVertices(LinkedList<Vertice> conjuntoVertices) {
		this.conjuntoVertices = conjuntoVertices;
	}

	public LinkedList<Rotulo> getConjuntoRotulos() {
		return conjuntoRotulos;
	}

	public void setConjuntoRotulos(LinkedList<Rotulo> conjuntoRotulos) {
		this.conjuntoRotulos = conjuntoRotulos;
	}

	public int getTotalRotulos() {
		return totalRotulos;
	}

	public void setTotalRotulos(int totalRotulos) {
		this.totalRotulos = totalRotulos;
	}

	public int getTotalVertices() {
		return totalVertices;
	}

	public void setTotalVertices(int totalVertices) {
		this.totalVertices = totalVertices;
	}

	public List<String> getLinhasBrutas() {
		return linhasBrutas;
	}

	public void setLinhasBrutas(List<String> linhasBrutas) {
		this.linhasBrutas = linhasBrutas;
	}

	public void printMatrix(int[][] ks) {
		int i, j;
		for(i=0; i<totalVertices; i++) {
			for(j=0; j<totalVertices; j++) {
				System.out.print(ks[i][j]+" ");
			}
			System.out.println("");
		}
	}
	
	public void printLabelSetProperties() {
		Collections.sort(conjuntoRotulos, new Comparator<Rotulo>() {
	         @Override
	         public int compare(Rotulo o1, Rotulo o2) {
	        	 if(o1.getValor() < o2.getValor()) {
	        		 return -1;
	        	 }
	        	 if(o1.getValor() > o2.getValor()) {
	        		 return 1;
	        	 }
	             return 0;
	         }
	     });
		
		for(Rotulo r: conjuntoRotulos) {
			System.out.println(r.getValor());
		}
	}

	public int[][] getIncidenceMatrix() {
		return incidenceMatrix;
	}
}