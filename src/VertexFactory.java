import com.tinkerpop.blueprints.*;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;

import java.io.*;
import java.util.*;

public class VertexFactory {
	final static String[] genres = {"crime","drama","action",
			"adventure","horror","thriller",
			"sci_fi","fantasy","war",
			"history","biography","family",
			"mystery","comedy","animation",
			"sport","romance","music","western",
			"musical","documentary",""};
	private static ArrayList<String> directors = new ArrayList<String>();
	private static Graph graph;
	
	
	public static void vertexFactory(String fileLocation) {
		
		graph = new TinkerGraph();
		
		//import all data from this file
		File file = new File(fileLocation);
		try {
			FileReader in = new FileReader(file);
			BufferedReader br = new BufferedReader(in);
			String nextLine;
			nextLine = br.readLine();
			
			String[] attributes = nextLine.split(",");
			ArrayList<String> butes = new ArrayList<String>();
			
			for(int i = 0; i < attributes.length;i++) {
				butes.add(attributes[i]);
			}
			String dirId = "d0";
			String genId = "g0";
			

			
			for(int i = 0; i < genres.length; i++) {
				Vertex temp = graph.addVertex(genId);
				temp.setProperty("genre", genres[i]);
				genId = genId(genId);
			}
			Vertex feature = graph.addVertex("f0");
			feature.setProperty("type", "Feature Film");
			
			while((nextLine = br.readLine()) != null) {
				String[] tempLine = nextLine.split(",");
				Vertex temp = graph.addVertex(tempLine[0]);
				temp.setProperty("Title", tempLine[butes.indexOf("Title")]);
				temp.setProperty("Rating", tempLine[butes.indexOf("IMDb Rating")]);
				temp.setProperty("Runtime", tempLine[butes.indexOf("Runtime (mins)")]);
				temp.setProperty("Year", tempLine[butes.indexOf("Year")]);
				temp.setProperty("Type", "Movie");
				if(directors.indexOf(tempLine[butes.indexOf("director1")]) < 0 &&
						!tempLine[butes.indexOf("director1")].equals("NaN")) {
					Vertex dir = graph.addVertex(dirId);
					dir.setProperty("Name", tempLine[butes.indexOf("director1")]);
					directors.add(tempLine[butes.indexOf("director1")]);
					dirId = dirId(dirId);
				}
				
				if(directors.indexOf(tempLine[butes.indexOf("director2")]) < 0 &&
						!tempLine[butes.indexOf("director2")].equals("NaN")) {
					Vertex dir = graph.addVertex(dirId);
					dir.setProperty("Name", tempLine[butes.indexOf("director2")]);
					directors.add(tempLine[butes.indexOf("director2")]);
					dirId = dirId(dirId);
				}
			}
		} catch (IOException e) {
			System.err.println("IOException");
		}

	}
	
	private static String dirId(String prev) {
		int split = Integer.valueOf(prev.substring(1));
		split++;
		String iteratedDir = "d" + String.valueOf(split);
		return iteratedDir;
	}
	private static String genId(String prev) {
		int split = Integer.valueOf(prev.substring(1));
		split++;
		String iteratedDir = "g" + String.valueOf(split);
		return iteratedDir;
	}
	
	public static ArrayList<String> getDirectors() {
		return directors;
	}
	public static String[] getGenres() {
		return genres;
	}
	public static Graph getGraph() {
		return graph;
	}
}
