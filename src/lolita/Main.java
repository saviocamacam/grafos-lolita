package lolita;

import java.io.File;
import java.util.LinkedList;

public class Main {

	public static void main(String[] args) {
		GerenciadorArquivos gerenciador = new GerenciadorArquivos();
		gerenciador.loadFolders();
		
		int i, j;
		
		//for(i=0; i < gerenciador.getListOfInstancesFolder().length; i++)
		for(i=0; i < 1; i++)
		{
			gerenciador.lodFiles(0);
			
			//for(j=0; j < gerenciador.getCurrentListOfFiles().length; j++)
			for(j=0; j < 1; j++)
			{
				File file = gerenciador.getFile(4);
				LinkedList<Grafo> listaGrafos = gerenciador.lerArquivoTransacao(file.toPath());
				
				Grafo g = listaGrafos.get(0); //Aqui eu pego o grafo no arquivo
				
				g.printAdjacenceList();
				g.generateMLST();
				
				//g.printLabelSetProperties();
				
				
				/*int sum = 0;
				int totalGraphs = listaGrafos.size();
				while(!listaGrafos.isEmpty()) {
					Grafo g = listaGrafos.pop();
					sum += g.generateMLST();
				}
				System.out.println("Average Labels: " + sum/totalGraphs);*/
			}
		}
	}

}
