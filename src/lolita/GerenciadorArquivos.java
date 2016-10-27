package lolita;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GerenciadorArquivos {

	public GerenciadorArquivos() {
		
	}
	
	public static LinkedList<Grafo> lerArquivoTransacao(String nomeArquivo){
	   LinkedList<Grafo> grafos = new LinkedList<>();
	   List<String> linhas = new ArrayList<>();
	   String[] cabecalho = null; 
	   
	   try {
		   linhas = Files.readAllLines(Paths.get(nomeArquivo, ""));
		   cabecalho = linhas.get(0).split(" ");
		   
	   } catch (IOException e) {
			e.printStackTrace();
	   }
	   
	   int i, totalRotulos= Integer.valueOf(cabecalho[1]), totalVertices= Integer.valueOf(cabecalho[0]);
	   int fromIndex = 1, toIndex = totalVertices;
	   
	   for(i=0 ; i < 10 ; i++) {
		   Grafo grafo = new Grafo(totalVertices, totalRotulos);
		   grafo.setLinhasBrutas(linhas.subList(fromIndex, toIndex));
		   Collections.reverse(grafo.getLinhasBrutas());
		   grafo.paranaue();
		   grafos.add(grafo);
	   }
	   
	   
	   return grafos;   
	}

}
