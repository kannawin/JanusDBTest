import com.tinkerpop.blueprints.*;
import java.util.ArrayList;


public class JanusMain {
	public static void main(String[] args) {
		VertexFactory.vertexFactory("C:\\Users\\kannas11\\Desktop\\WATCHLIST2.csv");
		EdgeFactory.edgeMaker("C:\\Users\\kannas11\\Desktop\\WATCHLIST2.csv");
		
		
		SearchMethods searchAPI = new SearchMethods();
		
		ArrayList<Vertex> movies = new ArrayList<Vertex>();
		
		//Search movies by Director
		//searchAPI.directorSearch("Joel Coen");
		
		//Search movies by Director and Genre
		movies = searchAPI.directorSearchGenre("Peter Jackson", "fantasy");
		
		//Search movies by year
		movies = searchAPI.moviesOfYear("1999");
		System.out.println("movies in 1999:\t" + movies.size());
		
		movies = searchAPI.moviesOfYearAndGenre("2000", "action");
		System.out.println("movies in 2000 that are action:\t" + movies.size());
		
		movies = searchAPI.moviesBetweenYears("1940", "2010");
		System.out.println("movies between 1940 and 2010:\t" + movies.size());
		
		movies = searchAPI.moviesBetweenYearsAndGenre("2000", "2001", "action");
		System.out.println("movies between 2000 and 2002 that are action:\t" + movies.size());
		
		movies = searchAPI.sortByYear(movies);
		System.out.println("Sorted the previous by year");
		
		movies = searchAPI.sortAlphabetically(movies);
		System.out.println("Sorted alphabetically");

		//movies = searchAPI.moviesBetweenYearsAndDirector("1940", "2010", "Peter Jackson");
		//System.out.println("movies between 1940 and 2010 directed by Peter Jackson:\t" + movies.size());
		
		SearchAll search = new SearchAll();
		
		//movies = search.selectAllMovies().moviesByGenre("fantasy").moviesByDirector("Peter Jackson").selection();
		
		//movies = search.selectAllMovies().moviesByDirector("Mel Gibson").selection();
		movies = search.selectAllMovies().searchProperty("genre", "action").moviesByYear("1999").selection();
		System.out.println(movies.size());
	}
}
