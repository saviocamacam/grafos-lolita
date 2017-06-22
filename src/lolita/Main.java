package lolita;

import java.io.File;
import java.util.LinkedList;

public class Main {

	public static void main(String[] args) {
		FileManager manager = new FileManager();
		manager.loadFolders();
		
		int i, j;
		
		for(i=0; i < manager.getListOfInstancesFolder().length; i++)
		//for(i=0; i < 1; i++)
		{
			manager.lodFiles(i);
			
			for(j=0; j < manager.getCurrentListOfFiles().length; j++)
			//for(j=0; j < 1; j++)
			{
				int var = j;
				File file = manager.getFile(var);
				LinkedList<Graph> graphsList = manager.readGraphFile(file.toPath(), var);
				
				/*Graph g = graphsList.get(3); //Aqui eu pego o grafo no arquivo
				
				g.printAdjacenceList();
				g.bicMLST();
				
				//g.printLabelSetProperties();
				
				*/
				float sumLabels = 0;
				float sumTime = 0;
				int totalGraphs = graphsList.size();
				while(!graphsList.isEmpty()) {
					Graph g = graphsList.pop();
					//g.printAdjacenceList();
					sumLabels += g.bicMLST();
					sumTime += g.getTime();
				}
				System.out.println("Average Labels: " + sumLabels/totalGraphs + " Average Time: " + sumTime/totalGraphs);
			}
		}
	}

}
