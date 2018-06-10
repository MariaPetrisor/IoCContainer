package movie;

import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ColonMovieFinder implements MovieFinder {
	
	private String filename;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		System.out.println("Filename set with:" + filename);
		this.filename = filename;
	}

	@Override
	public LinkedList<Movie> findAll() {
		File f = new File("src/movie/" + filename);
		LinkedList<Movie> movieList = new LinkedList<Movie>();
		
		if(f.exists() && !f.isDirectory()) { 
			
			FileInputStream fis;
			try {
				fis = new FileInputStream(f);
			
			 
			//Construct BufferedReader from InputStreamReader
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		 
			String line = null;
			
			while ((line = br.readLine()) != null) {
				
				if(line.split("/").length == 2) {
					movieList.add(new Movie(line.split("/")[0], line.split("/")[1]));
				}
			}
			
			br.close();
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
			
		}
		return movieList;
	}

	@Override
	public String toString() {
		return "ColonMovieFinder [filename=" + filename + "]";
	}
	
	

}
