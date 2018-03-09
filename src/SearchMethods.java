import com.tinkerpop.blueprints.*;

import java.util.ArrayList;
import java.util.Collections;

public class SearchMethods {
	private Graph graph;
	
	public SearchMethods() {
		graph = VertexFactory.getGraph();
	}
	
	/**
	 * Allows the search of movies made by a director
	 * 
	 * @param dir
	 * @return
	 */
	public ArrayList<Vertex> directorSearch(String dir) {
		ArrayList<Vertex> returnedMovies = new ArrayList<Vertex>();
		String dirId = EdgeFactory.directorId(dir);
		Vertex currentDir = graph.getVertex(dirId);
		for(Edge e : currentDir.getEdges(Direction.OUT, "out")) {
			returnedMovies.add(e.getVertex(Direction.IN));
		}
		return returnedMovies;
	}
	
	/**
	 * Allows the search of movies made by a director and Genres
	 * 
	 * @param dir
	 * @param gen
	 * @return
	 */
	public ArrayList<Vertex> directorSearchGenre(String dir, String gen) {
		ArrayList<Vertex> returnedMovies = new ArrayList<Vertex>();
		String dirId = EdgeFactory.directorId(dir);
		String genId = EdgeFactory.genreId(gen);
		for(Edge e : graph.getVertex(dirId).getEdges(Direction.OUT, "out")) {
			for(Edge g : e.getVertex(Direction.IN).getEdges(Direction.OUT, "out")) {
				if(g.getVertex(Direction.IN) == graph.getVertex(genId)) {
					returnedMovies.add(e.getVertex(Direction.IN));
				}
			}
		}
		return returnedMovies;
	}
	
	/**
	 * Search movies released in searched year
	 * 
	 * @param year
	 * @return
	 */
	public ArrayList<Vertex> moviesOfYear(String year){
		ArrayList<Vertex> returnedMovies = new ArrayList<Vertex>();
		for(Edge e : graph.getVertex("f0").getEdges(Direction.OUT, "out")) {
			Vertex inNode = e.getVertex(Direction.IN);
			if(inNode.getProperty("Year").equals(year)) {
				returnedMovies.add(inNode);
			}
		}
		return returnedMovies;
	}
	
	/**
	 * Search movies based on a range of years
	 * 
	 * @param year1
	 * @param year2
	 * @return
	 */
	public ArrayList<Vertex> moviesBetweenYears(String year1, String year2){
		int beginning = Integer.valueOf(year1);
		int end = Integer.valueOf(year2);
		ArrayList<Vertex> returnedMovies = new ArrayList<Vertex>();
		for(Edge e : graph.getVertex("f0").getEdges(Direction.OUT, "out")) {
			Vertex inNode = e.getVertex(Direction.IN);
			int movYear = Integer.valueOf(inNode.getProperty("Year"));
			if(movYear >= beginning && movYear <= end) {
				returnedMovies.add(inNode);
			}
		}
		return returnedMovies;
	}
	
	/**
	 * Returns movies based on year and genre
	 * 
	 * @param year
	 * @param genre
	 * @return
	 */
	public ArrayList<Vertex> moviesOfYearAndGenre(String year, String genre){
		ArrayList<Vertex> returnedMovies = new ArrayList<Vertex>();
		String genId = EdgeFactory.genreId(genre);
		Vertex genVertex = graph.getVertex(genId);
		
		for(Edge e : graph.getVertex("f0").getEdges(Direction.OUT, "out")) {
			Vertex inNode = e.getVertex(Direction.IN);
			if(inNode.getProperty("Year").equals(year)) {
				for(Edge g : inNode.getEdges(Direction.OUT, "out")) {
					if(genVertex.equals(g.getVertex(Direction.IN))) {
						returnedMovies.add(inNode);
					}
				}
			}
		}
		
		return returnedMovies;
	}
	
	/**
	 * Returns movies based on a year range and genre
	 * 
	 * @param year1
	 * @param year2
	 * @param genre
	 * @return
	 */
	public ArrayList<Vertex> moviesBetweenYearsAndGenre(String year1, String year2, String genre){
		ArrayList<Vertex> returnedMovies = new ArrayList<Vertex>();
		String genId = EdgeFactory.genreId(genre);
		Vertex genVertex = graph.getVertex(genId);
		
		int beginning = Integer.valueOf(year1);
		int end = Integer.valueOf(year2);
		
		for(Edge e : graph.getVertex("f0").getEdges(Direction.OUT, "out")) {
			Vertex inNode = e.getVertex(Direction.IN);
			int yearUsed = Integer.valueOf(inNode.getProperty("Year"));
			if(yearUsed >= beginning && yearUsed <= end) {
				for(Edge g : inNode.getEdges(Direction.OUT, "out")) {
					if(genVertex.equals(g.getVertex(Direction.IN))) {
						returnedMovies.add(inNode);
					}
				}
			}
		}
		
		return returnedMovies;
	}
	
	/**
	 * Returns movies that are within a range of years and by director
	 * 
	 * @param year1
	 * @param year2
	 * @param director
	 * @return
	 */
	public ArrayList<Vertex> moviesBetweenYearsAndDirector(String year1, String year2, String director){
		ArrayList<Vertex> returnedMovies = new ArrayList<Vertex>();
		String dirId = EdgeFactory.directorId(director);
		Vertex dirVertex = graph.getVertex(dirId);
		
		int beginning = Integer.valueOf(year1);
		int end = Integer.valueOf(year2);
		for(Edge e : graph.getVertex("f0").getEdges(Direction.OUT, "out")) {
			Vertex inNode = e.getVertex(Direction.IN);
			int yearUsed = Integer.valueOf(inNode.getProperty("out"));
			if(yearUsed >= beginning && yearUsed <= end) {
				for(Edge g : inNode.getEdges(Direction.IN, "out")) {
					if(dirVertex.equals(g.getVertex(Direction.OUT))) {
						returnedMovies.add(inNode);
					}
				}
			}
		}
		return returnedMovies;
	}
	
	/**
	 * Returns a sorted list of movies by year
	 * 
	 * @param movies
	 * @return
	 */
	public ArrayList<Vertex> sortByYear(ArrayList<Vertex> movies){
		ArrayList<Double> yearsDouble = new ArrayList<Double>();
		ArrayList<Vertex> moviesReturned = new ArrayList<Vertex>();
		ArrayList<String> doubleHelp = new ArrayList<String>();
		
		
		for(Vertex v : movies) {
			String tempHelp = String.valueOf(v.getProperty("Year") + "." + movies.indexOf(v)) + "7";
			yearsDouble.add(Double.valueOf(tempHelp));
		}
		Collections.sort(yearsDouble);

		for(Double d : yearsDouble) {
			doubleHelp.add(d.toString());
		}
		for(String s : doubleHelp) {
			String temp = s.substring(5,s.length() - 1);
			moviesReturned.add(movies.get(Integer.valueOf(temp)));
		}
		
		return moviesReturned;
	}
	
	/**
	 * Sorts array of movies alphabetically
	 * 
	 * @param movies
	 * @return
	 */
	public ArrayList<Vertex> sortAlphabetically(ArrayList<Vertex> movies){
		ArrayList<String> titles = new ArrayList<String>();
		ArrayList<Vertex> sortedMovies = new ArrayList<Vertex>();
		for(int i =0; i < movies.size(); i++) {
			String setup = movies.get(i).getProperty("Title") + ">" + i;
			titles.add(setup);
		}
		Collections.sort(titles);
		for(String s : titles) {
			String[] temp = s.split(">");

			sortedMovies.add(movies.get(Integer.valueOf(temp[1])));
		}
		
		return sortedMovies;
	}
	
	
	public void printGenres(Vertex movie) {
		ArrayList<String> genres = new ArrayList<String>();
		ArrayList<String> directors = new ArrayList<String>();
		for(Edge e : movie.getEdges(Direction.OUT, "is")) { 
			genres.add(e.getVertex(Direction.IN).getProperty("genre"));
		}
		for(Edge e : movie.getEdges(Direction.IN, "directed")) {
			directors.add(e.getVertex(Direction.OUT).getProperty("Name"));
		}
		
		if(directors.size() == 2) {
			System.out.println(movie.getProperty("Title") + ", directed by: " + directors.get(0) + " and " + directors.get(1));
		}else if (directors.size() == 1){
			System.out.println(movie.getProperty("Title") + ", directed by: " + directors.get(0));
		}
		System.out.println("Genres:");
		for(String s : genres) {
			System.out.println('\t' + s);
		}
	}
	
	
}
