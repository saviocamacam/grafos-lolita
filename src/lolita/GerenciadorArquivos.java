package lolita;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GerenciadorArquivos {

	public GerenciadorArquivos() {
		
	}
	
	public static LinkedList<Grafo> lerArquivoTransacao(String nomeArquivo){
	   LinkedList<Grafo> grafos = new LinkedList<>();
	   List<String> linhas = new ArrayList<>();
	   
	   try {
		   linhas = Files.readAllLines(Paths.get(nomeArquivo+".txt", ""));
		   linhas.remove(0);
	   } catch (IOException e) {
			e.printStackTrace();
	   }
	   
	   return grafos;   
	}

}
