package wizardGwt.client.pages;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import wizardGwt.client.AsyncCall;
import wizardGwt.client.xml.XMLMovies;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class MoviePage extends WizardPage {
	private XMLMovies xml;
	private HashMap<String, String> moviePosters = new HashMap<String, String>(); 
	private final Image currentPoster = new Image();
	public final ListBox movieList = new ListBox();
	private final Grid grid = new Grid(1, 2);
	
	public MoviePage() {
		xml = new XMLMovies("Movies.xml");
		
		new AsyncCall() {
			public void process() throws Exception {
				List<String> movieNames = new ArrayList<String>(xml.getAllMovies());
				Collections.sort(movieNames);
				moviePosters = xml.getAllPosters();
				for (Iterator country=movieNames.iterator(); country.hasNext(); ) {
					movieList.addItem((String) country.next());
				}
				movieList.fireEvent(new ChangeEvent() {});
				grid.setWidget(0, 0, currentPoster);
			}
		};
		
		class MovieListHandler implements ChangeHandler {
			@Override
			public void onChange(ChangeEvent event) {
				String name = movieList.getValue(movieList.getSelectedIndex());
				currentPoster.setUrl(moviePosters.get(name));
				currentPoster.setAltText(name);
				currentPoster.setVisibleRect(0, 0, currentPoster.getWidth(), currentPoster.getHeight());
			}
		}
		
		MovieListHandler listHandler = new MovieListHandler();
		movieList.addChangeHandler(listHandler);

		grid.setWidget(0, 1, movieList);
	}

	@Override
	public Widget getWidget() {
		return grid;
	}

}
