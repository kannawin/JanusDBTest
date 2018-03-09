import com.tinkerpop.blueprints.*;
import java.io.*;
import java.util.ArrayList;

public class EdgeFactory {
	private static Graph graph;
	private static ArrayList<String> directors;
	private static ArrayList<String> genres;
	
	public static void edgeMaker(String fileRelations) {
		graph = VertexFactory.getGraph();
		directors = VertexFactory.getDirectors();
		convertGenres();
		
		File file = new File(fileRelations);
		FileReader in = null;
		
		try {
			in = new FileReader(file);
			BufferedReader br = new BufferedReader(in);
			String nextLine = br.readLine();
			
			String[] header = nextLine.split(",");
			ArrayList<String> butes = new ArrayList<String>();
			for(int i = 0; i < header.length; i++) {
				butes.add(header[i]);
			}
			
			while((nextLine = br.readLine()) != null) {
				String[] inOut = nextLine.split(",");
				Vertex home = graph.getVertex("f0");
				Vertex movie = graph.getVertex(inOut[0]);
				graph.addEdge(null, movie, home, "out");
				graph.addEdge(null, home, movie, "out");
				
				//gets the directors
				Vertex dir1 = graph.getVertex(directorId(inOut[butes.indexOf("director1")]));
				graph.addEdge(null, movie, dir1, "out");
				graph.addEdge(null, dir1, movie, "out");
				Vertex dir2 = null;
				if(!inOut[butes.indexOf("director2")].equals("NaN")) {
					dir2 = graph.getVertex(directorId(inOut[butes.indexOf("director2")]));
					graph.addEdge(null, movie, dir2, "out");
					graph.addEdge(null, dir2, movie, "out");
				}
				
				//sets the array of genres per movie
				int startIndex = butes.indexOf("genre1");
				
				for(int i = 0; i < 8; i++) {
					if(!inOut[startIndex].equals("NaN")) {
						Vertex temp = graph.getVertex(genreId(inOut[startIndex]));
						graph.addEdge(null, temp, movie, "out");
						graph.addEdge(null, movie, temp, "out");
					}
					else if(inOut[startIndex].equals("NaN")) {
						break;
					}
					startIndex++;
				}
				
			}
			
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void convertGenres() {
		String[] gen = VertexFactory.getGenres();
		genres = new ArrayList<String>();
		for(int i = 0; i < gen.length; i++) {
			genres.add(gen[i]);
		}
	}
	
	public static String genreId(String genre) {
		int index = genres.indexOf(genre);
		return ("g" + String.valueOf(index));
	}
	public static String directorId(String director) {
		int index = directors.indexOf(director);
		return ("d" + String.valueOf(index));
	}
	
	public static ArrayList<String> getDirectors(){
		return directors;
	}
	public static ArrayList<String> getGenres(){
		return genres;
	}
	public static Graph getGraph() {
		return graph;
	}
}
