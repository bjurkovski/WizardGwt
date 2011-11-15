package wizardGwt.client.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.google.gwt.http.client.Request;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

public class XMLMovies extends XMLDocReader {
	private HashMap<String, String> posters = new HashMap<String, String>();
	
	public XMLMovies(String docName) {
		super(docName);
	}

	@Override
	protected void proccess() {
		NodeList movieNodes = xmlDoc.getElementsByTagName("movie");
	    for(int i=0; i<movieNodes.getLength(); i++) {
	    	Element movie = (Element) movieNodes.item(i);
	    	Element poster = (Element) movie.getElementsByTagName("poster").item(0);
    		posters.put(movie.getAttribute("name"), poster.getAttribute("url"));
	    }
	}

	@Override
	protected void error(Request res, Throwable throwable) {
	}
	
	public Collection<String> getAllMovies() throws Exception {
		if(!finishedReading) {
			throw new Exception();
		}
		
		return posters.keySet();
	}
	
	public HashMap<String, String> getAllPosters() throws Exception {
		if(!finishedReading) {
			throw new Exception();
		}
		
		return posters;
	}
	
	public String getPoster(String movieName) throws Exception {
		if(!finishedReading) {
			throw new Exception();
		}

		if(posters.containsKey(movieName)) {
			return posters.get(movieName);
		}
		else {
			return "";
		}
	}
}
