package checker;

import java.util.Iterator;
import java.util.LinkedList;

import movie.Movie;

public class TextEditor {
	private SpellChecker spellChecker;

	public SpellChecker getSpellChecker() {
		return spellChecker;
	}

	public void setSpellChecker(SpellChecker spellChecker) {
		this.spellChecker = spellChecker;
	}
	
	public String[] filterCorrectShortText() {
		LinkedList<String> textList = spellChecker.isSpelledCorrectly();
		for (Iterator<String> it = textList.iterator(); it.hasNext();) { 
			
			String text = (String) it.next(); 
			
			if (text.length() > 30) {
				it.remove(); 
			}
		}
		return (String[]) textList.toArray(new String[textList.size()]); 
	}

	@Override
	public String toString() {
		return "TextEditor [spellChecker=" + spellChecker + "]";
	}
	
	

}
