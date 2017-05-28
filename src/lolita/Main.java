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
			gerenciador.lodFiles(5); //gerenciador.lodFiles(i) Aqui eu pego a pasta. Pasta 5 (500) pasta 0 (30)
			
			for(j=0; j<1/*gerenciador.getCurrentListOfFiles().length*/; j++)
			{
				File file = gerenciador.getFile(3); //File file = gerenciador.getFile(j) aqui eu pego o arquivo na pasta Arquivo 3 ou 7 para pasta 5. Arquivo 4 para pasta 0.
				LinkedList<Grafo> listaGrafos = gerenciador.lerArquivoTransacao(file.toPath());
				
				Grafo g = listaGrafos.get(2); //Aqui eu pego o grafo no arquivo
				
				//g.printAdjacenceList();
				g.generateMLST();
				
				//g.printLabelSetProperties();
				
				/*while(!listaGrafos.isEmpty()) {
					Grafo g = listaGrafos.pop();
					g.generateMLST();
				}*/
			}
		}
	}

}
