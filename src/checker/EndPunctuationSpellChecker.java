package checker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

import movie.Movie;

public class EndPunctuationSpellChecker implements SpellChecker {
	private String filename;
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public LinkedList<String> isSpelledCorrectly() {
		File f = new File("src/checker/" + filename);
		LinkedList<String> textList = new LinkedList<String>();
		if(f.exists() && !f.isDirectory()) { 
			
			FileInputStream fis;
			try {
				fis = new FileInputStream(f);
			
			//Construct BufferedReader from InputStreamReader
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		 
			String line = null;
			
			while ((line = br.readLine()) != null) {
				
				if(line.matches(".*[.?!]")) {
					textList.add(line);		
				}
			}
			
			br.close();
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return textList;
	}

	@Override
	public String toString() {
		return "EndPunctuationSpellChecker [filename=" + filename + "]";
	}
	
	

}
