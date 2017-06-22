package lolita;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Graph {
	
	private LinkedList<Aresta> conjuntoArestas;
	private LinkedList<Vertice> conjuntoVertices;
	private LinkedList<Rotulo> conjuntoRotulos;
	private int totalVertices;
	private int totalRotulos;
	private List<String> linhasBrutas;
	private int incidenceMatrix[][];
	private LinkedList<LinkedList<Vertice>> adjacenceList;
	private LinkedList<SubGraph> listOfSubgraphs;
	@SuppressWarnings("unused")
	private int totalEdges = 0;
	private long finalTime = 0;
	
	public Graph(int totalVertices, int totalRotulos) {
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
					Vertice vj = new Vertice(j);
					vj.setLabelAdjacentFor(incidenceMatrix[i+1][j]);
					adjacenceList.get(i+1).add(vj);
					
					
					Vertice vi = new Vertice(i+1);
					vi.setLabelAdjacentFor(incidenceMatrix[i+1][j]);
					adjacenceList.get(j).add(vi);
					
					Rotulo rotulo = new Rotulo(incidenceMatrix[i+1][j]);
					
					if(!conjuntoRotulos.contains(rotulo))
						conjuntoRotulos.add(rotulo);
				}
			}
		}
		//printAdjacenceMatrix();
		//System.out.println("Total de Arestas: " + totalEdges);
		
		incidenceMatrix[totalVertices-1][totalVertices-1] = -1;
		totalVertices = recountTotalVertices();
	}
	
	private int recountTotalVertices() {
		int sum=0;
		for(LinkedList<Vertice> l : adjacenceList) {
			if(l.size() == 0)
				sum++;
		}
		return totalVertices-sum;
	}

	public void printAdjacenceList() {
		int i, j;
		System.out.println("Lista de Adjacencias");
		for(i=0; i<totalVertices;i++) {
			System.out.print(i + ": ");
			for(j=0; j<adjacenceList.get(i).size(); j++) {
				System.out.print(adjacenceList.get(i).get(j).getValor() +"(" + adjacenceList.get(i).get(j).getLabelAdjacentFor() + ")" + " ");
			}
			System.out.println("");
		}
		System.out.println("");
	}

	public int bicMLST() {
		//printIncidenceMatrix(-1, incidenceMatrix);
		long startTime = System.currentTimeMillis();
		
		getSubGraphs();
		orderSubGraphs();
		//printAllSubGraphProperties();
		
		HashSet<String> labelsMLST = new HashSet<String>();
		labelsMLST.add("L" + listOfSubgraphs.get(0).getLabel() + "; ");
		SubGraph mainSubgraph = this.listOfSubgraphs.get(0);
		int i=1;
		
		
		while(mainSubgraph.getMaxComponent() != this.totalVertices && i < totalRotulos) {
			SubGraph nextSubgraph = listOfSubgraphs.get(i);
			
			int indexNext = -1;
			LinkedList<Integer> unionList = new LinkedList<>();
			LinkedList<HashSet<Integer>> auxComponentsSet = new LinkedList<HashSet<Integer>>();
			boolean unionFlag = false;
			
			for(HashSet<Integer> hs1 : mainSubgraph.getComponentsSet()) {
				
				for(HashSet<Integer> hs2 : nextSubgraph.getComponentsSet()) {
					indexNext = nextSubgraph.getComponentsSet().indexOf(hs2);
					
					HashSet<Integer> intersection = new HashSet<Integer>(hs2);
					intersection.retainAll(hs1);
					
					if(intersection.size() != 0 && intersection.size() < hs2.size() && intersection.size() < hs1.size() || (hs1.size() < hs2.size() && intersection.size() == hs1.size())) {
						unionList.add(indexNext);
					}
					else if(hasIntersection(mainSubgraph.getConjuntoVertices(), nextSubgraph.getConjuntoVertices())) {
						unionFlag = true;
						if(!auxComponentsSet.contains(hs2))
							auxComponentsSet.add(hs2);
					}
				}
				
				HashSet<Integer> intersection = new HashSet<Integer>(hs1);
				
				if(unionList.size() > 0){
					unionFlag = true;
					for(Integer integer : unionList) {
						intersection.addAll(nextSubgraph.getComponentsSet().get(integer));
					}
					labelsMLST.add("L" + nextSubgraph.getLabel() + "; ");
				}
				auxComponentsSet.add(intersection);
				unionList = new LinkedList<>();
			}
			

			HashSet<Integer> indexRemove = new HashSet<>();
			LinkedList<HashSet<Integer>> finalComponentsSet = new LinkedList<HashSet<Integer>>();
			
			for(HashSet<Integer> hs1 : auxComponentsSet) {
				for(HashSet<Integer> hs2 : auxComponentsSet) {
					if(hs1.size() > hs2.size() && hs1 != hs2) {
						HashSet<Integer> intersection = new HashSet<Integer>(hs1);
						intersection.retainAll(hs2);
						
						if(intersection.size() != 0) {
							hs1.addAll(hs2);
							indexRemove.add(auxComponentsSet.indexOf(hs2));
						}
					}
				}
			}
			int j;
			
			for(j=0; j < auxComponentsSet.size(); j++) {
				if(!indexRemove.contains(j)) {
					HashSet<Integer> hs = auxComponentsSet.get(j);
					finalComponentsSet.add(hs);
				}
			}
			
			mainSubgraph.setComponentsSet(finalComponentsSet);
			
			if(unionFlag) {
				mainSubgraph.getConjuntoVertices().addAll(nextSubgraph.getConjuntoVertices());
				unionFlag = false;
			}
			i++;
			//printAllSubGraphProperties();
		}
		
		long endTime = System.currentTimeMillis();
		finalTime = endTime - startTime;
		
		/*
		if(mainSubgraph.getMaxComponent() == this.totalVertices)
			System.out.print("T ");
		
		if(i == totalRotulos)
			System.out.print("F ");
		
		if(mainSubgraph.getComponents() > 1)
			System.out.print("F2 ");
		
		System.out.print(mainSubgraph.getComponents() + " componentes " + labelsMLST.size() + " labels: ");
		System.out.print("Running time: " + finalTime + " ");
		
		Iterator<String> iterator = labelsMLST.iterator();
		while(iterator.hasNext()) {
			System.out.print(iterator.next());
		}
		System.out.println("\n");*/
		
		
		return labelsMLST.size();
	}
	
	private boolean hasIntersection(HashSet<Integer> conjuntoVerticesMain, HashSet<Integer> conjuntoVerticesProximo) {
		HashSet<Integer> intersection = new HashSet<Integer>(conjuntoVerticesMain);
		intersection.retainAll(conjuntoVerticesProximo);
		return !intersection.isEmpty();
	}

	private void orderSubGraphs() {
		Collections.sort(listOfSubgraphs, new Comparator<SubGraph>() {
	         @Override
	         public int compare(SubGraph s1, SubGraph s2) {
	        	 if(s1.getMaxComponent() > s2.getMaxComponent())
	        		 return -1;

	        	 if(s1.getMaxComponent() < s2.getMaxComponent())
	        		 return 1;
	        	 
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
	             return 0;/*
	        	 if(s1.getComponents() > s2.getComponents())
	    			 return -1;
	    		 if(s1.getComponents() < s2.getComponents())
	    			 return 1;
	    		 
	    		 if(s1.getComponents() == s2.getComponents()) {
	    			 if(s1.getMaxComponent() > s2.getMaxComponent())
		        		 return -1;
	
		        	 if(s1.getMaxComponent() < s2.getMaxComponent())
		        		 return 1;
		        	 
		        	 if(s1.getMaxComponent() == s2.getMaxComponent()) {
		        		 if(s1.getConjuntoVertices().size() > s2.getConjuntoVertices().size())
	        				 return -1;
	        			 if(s1.getConjuntoVertices().size() < s2.getConjuntoVertices().size())
	        				 return 1;
		        	 }
	    		 }
	    		 return 0;*/
	         }
	         
		         
	         
	     });
	}
	
	private void printSubgraphPropertie(SubGraph s) {
		System.out.print("SubGraphOfLabel: " + s.getLabel() + ", with " + s.getArestas()  + " edges ");
		
		Iterator<Aresta> iterator3 = s.getEdgeSet().iterator();
		while(iterator3.hasNext()) {
			Aresta a = iterator3.next();
			System.out.print("<" + a.getOrigem().getValor() + " " +  a.getDestino().getValor() + ">, ");
		}
		
		System.out.print(" between ");
		
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
	
	private void printAllSubGraphProperties() {
		System.out.println("");
		for(SubGraph s: listOfSubgraphs) {
			printSubgraphPropertie(s);
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
		
		LinkedList<HashSet<Integer>> listOfComponentSet = new LinkedList<>();
		HashSet<Aresta> edgeSet = new HashSet<Aresta>();
		int edges = 0;
		HashSet<Integer> hash = new HashSet<>();
		listOfComponentSet.add(new HashSet<>());
		int jDimension = 0;
		
		for(i=0 ; i < totalVertices; i++) {
			for(j=0; j < jDimension; j++) {
				
				if (/*matrixOfSubGraph[i][j] != -1 && */matrixOfSubGraph[i][j] != label && matrixOfSubGraph[i][j] != totalRotulos)
					matrixOfSubGraph[i][j] = totalRotulos;
				if (matrixOfSubGraph[i][j] == label) {
					hash.add(i);
					hash.add(j);
					edgeSet.add(new Aresta(new Rotulo(label), new Vertice(i), new Vertice(j)));
					
					boolean addedI = false;
					boolean addedJ = false;
					
					int indexJ = 0, indexI = 0;
					for(HashSet<Integer> hs : listOfComponentSet) {
						if(hs.isEmpty() || hs.contains(i)) {
							//hs.add(i);
							hs.add(j);
							addedI = true;
							indexI = listOfComponentSet.indexOf(hs);
						} 
					}
					
					for(HashSet<Integer> hs : listOfComponentSet) {
						if(hs.isEmpty() || hs.contains(j)) {
							hs.add(i);
							//hs.add(j);
							addedJ = true;
							indexJ = listOfComponentSet.indexOf(hs);
							if(indexJ != indexI)
								break;
						} 
					}
					
					if(indexI != indexJ && addedI && addedJ) {
						HashSet<Integer> h1 = listOfComponentSet.get(indexI);
						listOfComponentSet.get(indexJ).addAll(h1);
						listOfComponentSet.remove(indexI);
					}
					
					if(!addedI && !addedJ) {
						HashSet<Integer> hs = new HashSet<>();
						hs.add(i);
						hs.add(j);
						listOfComponentSet.add(hs);
					}
					edges++;
				}
			}
			//printIncidenceMatrix(label, matrixOfSubGraph);
			if(jDimension <= totalVertices)
				jDimension++;
		}
		
		SubGraph subGraph = new SubGraph(label, matrixOfSubGraph);
		subGraph.setEdgeSet(edgeSet);
		subGraph.setEdges(edges);
		subGraph.setConjuntoVertices(hash);
		subGraph.setComponentsSet(listOfComponentSet);
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
		System.out.println("Matriz de Incid�ncias: " + label);
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

	public long getTime() {
		return finalTime ;
	}
}