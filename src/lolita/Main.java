package lolita;

import java.io.File;
import java.util.LinkedList;

public class Main {

	public static void main(String[] args) {
		GerenciadorArquivos gerenciador = new GerenciadorArquivos();
		gerenciador.loadFolders();
		
		int i, j;
		
		for(i=0; i<1/*gerenciador.getListOfInstancesFolder().length*/; i++)
		{
			gerenciador.lodFiles(0); //gerenciador.lodFiles(i)
			
			for(j=0; j<1/*gerenciador.getCurrentListOfFiles().length*/; j++)
			{
				File file = gerenciador.getFile(4); //File file = gerenciador.getFile(j)
				LinkedList<Grafo> listaGrafos = gerenciador.lerArquivoTransacao(file.toPath());
				
				Grafo g = listaGrafos.get(2);
				g.printAdjacenceMatrix();
				g.generateMLST();
				g.printLabelSetProperties();
				
				/*while(!listaGrafos.isEmpty()) {
					Grafo g = listaGrafos.pop();
					g.generateMLST();
				}*/
			}
		}
	}

}
