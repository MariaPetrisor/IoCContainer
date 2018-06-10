package assembler;
import movie.*;
import checker.*;
 
public class TestMain {

	public static void main(String[] args) {
		
	    ApplicationContext ctx = new FileSystemXmlApplicationContext("src/assembler/config.xml"); 
	    MovieLister lister = (MovieLister) ctx.getBean("MovieLister");   
	    Movie[] movies = lister.moviesDirectedBy("Sergio Leone"); 
	    System.out.println(movies[0].getTitle());
	    
	    ApplicationContext ctx2 = new FileSystemXmlApplicationContext("src/assembler/CheckerConfig.xml"); 
	    TextEditor editor = (TextEditor) ctx2.getBean("TextEditor");   
	    String[] textList = editor.filterCorrectShortText();
	    for(int i = 0; i < textList.length; i++)
	    	System.out.println(textList[i]);
	 
	}

}
