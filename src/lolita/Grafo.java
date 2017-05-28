package lolita;

import java.text.Collator;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
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
	private LinkedList<LinkedList<Vertice>> adjacenceList;
	private LinkedList<SubGraph> listOfSubgraphs;
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
					adjacenceList.get(i+1).add(new Vertice(j));
					adjacenceList.get(j).add(new Vertice(i+1));
					
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
	
	public void printAdjacenceList() {
		int i, j;
		System.out.println("Lista de Adjacencias");
		for(i=0; i<totalVertices;i++) {
			System.out.print(i + ": ");
			for(j=0; j<adjacenceList.get(i).size(); j++) {
				System.out.print(adjacenceList.get(i).get(j).getValor() +" ");
			}
			System.out.println("");
		}
		System.out.println("");
	}

	public void generateMLST() {
		//printIncidenceMatrix(-1, incidenceMatrix);
		long startTime = System.currentTimeMillis();
		
		getSubGraphs();
		orderSubGraphs();
		printSubGraphsProperties();
		
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		
		System.out.println("HashSet: " + duration);
		
		
	}
	
	private void orderSubGraphs() {
		Collections.sort(listOfSubgraphs, new Comparator<SubGraph>() {
	         @Override
	         public int compare(SubGraph s1, SubGraph s2) {
	        	 if(s1.getMaxComponent() > s2.getMaxComponent()) {
	        		 return -1;
	        	 }
	        	 if(s1.getMaxComponent() < s2.getMaxComponent()) {
	        		 return 1;
	        	 }
	        	 if(s1.getMaxComponent() == s2.getMaxComponent()) {
	        		 if(s1.getComponents() > s2.getComponents())
	        			 return -1;
	        		 if(s1.getComponents() < s2.getComponents())
	        			 return 1;
	        		 if(s1.getComponents() == s2.getComponents()) {
	        			 if(s1.getConjuntoVertices().size() > s2.getConjuntoVertices().size())
	        				 return -1;
	        			 if(s1.getConjuntoVertices().size() < s2.getConjuntoVertices().size())
	        				 return 1;
	        		 }
	        	 }
	             return 0;
	         }
	     });
	}
	
	private void printSubGraphsProperties() {
		for(SubGraph s: listOfSubgraphs) {
			System.out.print("SubGraphOfLabel: " + s.getLabel() + ", with " + s.getArestas()  + " edges between ");
			Iterator<Integer> iterator = s.getConjuntoVertices().iterator();
			while(iterator.hasNext()) {
				System.out.print(iterator.next() + ", ");
			}
			System.out.print(" grouped in " + s.getComponents() + " component as ");
			Iterator<HashSet<Integer>> iterator2 = s.getComponentsSet().iterator();
			while(iterator2.hasNext()) {
				System.out.print(iterator2.next() + ", ");
			}
			System.out.println();
		}
	}

	private void getSubGraphs() {
		int i;
		for(i=0; i<totalRotulos;i++) {
			listOfSubgraphs.add(getSubGraphOf(i));
		}
	}

	private SubGraph getSubGraphOf(int label) {
		int i, j;
		int matrixOfSubGraph[][] = new int[totalVertices][totalVertices];
		matrixOfSubGraph = Arrays.stream(incidenceMatrix)
	             .map((int[] row) -> row.clone())
	             .toArray((int length) -> new int[length][]);
		
		LinkedList<HashSet<Integer>> listSet = new LinkedList<>();
		int arestas = 0;
		HashSet<Integer> hash = new HashSet<>();
		listSet.add(new HashSet<>());
		int jDimension = 0;
		
		for(i=0 ; i < totalVertices; i++) {
			for(j=0; j < jDimension; j++) {
				
				if(/*matrixOfSubGraph[i][j] != -1 && */matrixOfSubGraph[i][j] != label && matrixOfSubGraph[i][j] != totalRotulos)
					matrixOfSubGraph[i][j] = totalRotulos;
				if(matrixOfSubGraph[i][j] == label) {
					hash.add(i);
					hash.add(j);
					boolean added = false;
					int indexJ = 0, indexI = 0;
					for(HashSet<Integer> hs : listSet) {
						if(hs.isEmpty() || hs.contains(i)) {
							//hs.add(i);
							hs.add(j);
							added = true;
							indexI = listSet.indexOf(hs);
						} 
					}
					
					for(HashSet<Integer> hs : listSet) {
						if(hs.isEmpty() || hs.contains(j)) {
							hs.add(i);
							//hs.add(j);
							added = true;
							indexJ = listSet.indexOf(hs);
							if(indexJ != indexI)
								break;
						} 
					}
					
					if(indexI != indexJ) {
						HashSet<Integer> h1 = listSet.get(indexI);
						listSet.get(indexJ).addAll(h1);
						listSet.remove(indexI);
					}
					
					if(!added) {
						HashSet<Integer> hs = new HashSet<>();
						hs.add(i);
						hs.add(j);
						listSet.add(hs);
					}
					arestas++;
				}
			}
			//printIncidenceMatrix(label, matrixOfSubGraph);
			if(jDimension <= totalVertices)
				jDimension++;
		}
		
		SubGraph subGraph = new SubGraph(label, matrixOfSubGraph);
		subGraph.setArestas(arestas);
		subGraph.setConjuntoVertices(hash);
		subGraph.setComponentsSet(listSet);
		subGraph.setComponents(listSet.size());
		//printIncidenceMatrix(label, subGraph.getIncidenceMatrix());
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

	public void printIncidenceMatrix(int label, int[][] ks) {
		int i, j;
		System.out.println("Matriz de Incidências: " + label);
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