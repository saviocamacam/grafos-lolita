package lolita;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FileManager {
	
	private String rootPath = Paths.get("").toAbsolutePath(). toString();
	private String subPathInstancesFolder = "/base/instancias_relatorio/";
	
	private static File instancesFolder= null;
	
	private File[] listOfInstancesFolder = null;
	private File[] currentListOfFiles = null;
	public FileManager() {
		FileManager.instancesFolder = new File(rootPath + subPathInstancesFolder);
	}
	
	public void loadFolders() {
		setListOfInstancesFolder(instancesFolder.listFiles());
	}
	
	public void lodFiles(int i) {
		setCurrentListOfFiles(listOfInstancesFolder[i].listFiles());
	}
	
	public LinkedList<Graph> readGraphFile(Path path, int var){
		System.out.print(var + " "+ path.getFileName() + " ");
		LinkedList<Graph> grafos = new LinkedList<>();
		List<String> linhas = new ArrayList<>();
		String[] cabecalho = null; 
	   
		try {
			linhas = Files.readAllLines(path);
			cabecalho = linhas.get(0).split(" ");
		   
		} catch (IOException e) {
			e.printStackTrace();
		}
	   
		int i, totalRotulos= Integer.valueOf(cabecalho[1]), totalVertices= Integer.valueOf(cabecalho[0]);
		int fromIndex = 1, toIndex = totalVertices;
	   
		for(i=0 ; i < 10 ; i++) { //for(i=0 ; i < 10 ; i++) {
			Graph grafo = new Graph(totalVertices, totalRotulos);
			grafo.setLinhasBrutas(linhas.subList(fromIndex, toIndex));
			Collections.reverse(grafo.getLinhasBrutas());
			//System.out.println("\nGrafo " + i);
			grafo.matrixGenerate();
			//grafo.printMatrix(grafo.getIncidenceMatrix());
			//grafo.geraGrafo();
			grafos.add(grafo);
			fromIndex = totalVertices;
			toIndex = toIndex + totalVertices;
		}
		return grafos;   
	}

	public File[] getListOfInstancesFolder() {
		return listOfInstancesFolder;
	}

	public void setListOfInstancesFolder(File[] listOfInstancesFolder) {
		this.listOfInstancesFolder = listOfInstancesFolder;
	}

	public File[] getCurrentListOfFiles() {
		return currentListOfFiles;
	}

	public void setCurrentListOfFiles(File[] currentListOfFiles) {
		this.currentListOfFiles = currentListOfFiles;
	}

	public File getFile(int j) {
		return currentListOfFiles[j];
	}
}
