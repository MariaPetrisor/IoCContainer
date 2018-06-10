package movie;

import java.util.*;

public class MovieLister {
	private MovieFinder finder;
	
	public void setFinder(MovieFinder finder) {
		System.out.println("Finder set " + finder.toString());
		this.finder = finder;
	}
	
	public Movie[] moviesDirectedBy(String arg) { 
		System.out.println(finder);
		LinkedList<Movie> allMovies = finder.findAll();
		for (Iterator<Movie> it = allMovies.iterator(); it.hasNext();) { 
			Movie movie = (Movie) it.next(); 
			if (!movie.getDirector().equals(arg)) {
				it.remove(); 
			}
		}
		return (Movie[]) allMovies.toArray(new Movie[allMovies.size()]); 
	}

	@Override
	public String toString() {
		return "MovieLister [finder=" + finder.toString() + "]";
	}  
	
	
}
