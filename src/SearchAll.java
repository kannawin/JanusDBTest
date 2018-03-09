import com.tinkerpop.blueprints.*;
import java.util.ArrayList;


public class SearchAll {
	private Graph graph;
	int i = 0;
	private ArrayList<Vertex> returned;
	public SearchAll() {
		graph = EdgeFactory.getGraph();
	}
	public SearchAll(ArrayList<Vertex> input) {
		returned = input;
		graph = EdgeFactory.getGraph();
	}
	
	
	public SearchAll selectAllMovies(){
		ArrayList<Vertex> returnedMovies = new ArrayList<Vertex>();
		for(Edge e : graph.getVertex("f0").getEdges(Direction.OUT, "out")) {
			returnedMovies.add(e.getVertex(Direction.IN));
		}
		
		SearchAll movies = new SearchAll(returnedMovies);
		return movies;
	}

	
	public SearchAll selectAllMoviesOfSelected(){
		ArrayList<Vertex> returnedMovies = new ArrayList<Vertex>();
		
		for(Vertex v : returned) {
			if(v.getProperty("Type").equals("Movie")) {
				returnedMovies.add(v);
			}
		}
		
		SearchAll movies = new SearchAll(returnedMovies);
		return movies;
	}
	
	
	public SearchAll moviesByYear(String year){
		ArrayList<Vertex> returnedMovies = new ArrayList<Vertex>();
		for(Vertex v : returned) {
			if(v.getProperty("Year").equals(year)) {
				returnedMovies.add(v);
			}
		}
		SearchAll movie = new SearchAll(returnedMovies);
		return movie;
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public SearchAll moviesByGenre(String genre){
		ArrayList<Vertex> returnedMovies = new ArrayList<Vertex>();
		for(Vertex v : returned) {
			for(Edge e : v.getEdges(Direction.OUT, "out")) {
				if(e.getVertex(Direction.IN).getProperty("genre").equals(genre)) {
					returnedMovies.add(e.getVertex(Direction.IN));
				}
			}
		}
		SearchAll movie = new SearchAll(returnedMovies);
		return movie;
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public SearchAll moviesByDirector(String director){
		ArrayList<Vertex> returnedMovies = new ArrayList<Vertex>();
		for(Vertex v : returned) {
			for(Edge e : v.getEdges(Direction.OUT, "out")) {
				if(e.getVertex(Direction.IN).getProperty("Name").equals(director)) {
					returnedMovies.add(e.getVertex(Direction.IN));
				}
			}
		}
		
		
		SearchAll movie = new SearchAll(returnedMovies);
		return movie;
	}
	
	public SearchAll searchProperty(String property, String term) {
		String prop = "";
		ArrayList<Vertex> returnedMovies = new ArrayList<Vertex>();
		if(property.toLowerCase().equals("director".toLowerCase())) {
			prop = "Name";
		}
		else if (property.toLowerCase().equals("genre".toLowerCase())) {
			prop = "genre";
		}
		for(Vertex v : returned) {
			for(Edge e : v.getEdges(Direction.OUT, "out")) {
				try {
					if(e.getVertex(Direction.IN).getProperty(prop).equals(term)){
						returnedMovies.add(e.getVertex(Direction.IN));
					}
				} catch(NullPointerException z) {
					System.out.println(i++);
				}
			}
		}
		SearchAll movie = new SearchAll(returnedMovies);
		return movie;
	}
	
	
	public ArrayList<Vertex> selection(){
		return returned;
	}
}
