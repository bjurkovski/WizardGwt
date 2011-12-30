package wizardGwt.client;

import java.util.List;
import java.util.Set;

import wizardGwt.client.pages.DynamicContentPage;
import wizardGwt.client.pages.FormPage;
import wizardGwt.client.pages.MoviePage;
import wizardGwt.client.pages.ReviewPage;
import wizardGwt.client.pages.WizardPage;
import wizardGwt.shared.FieldVerifier;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.VerticalSplitPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WizardGwt implements EntryPoint {
	private wizardGwtConstants constants = GWT.create(wizardGwtConstants.class);
	
	final VerticalPanel vPanel = new VerticalPanel();
	final HorizontalPanel hPanel = new HorizontalPanel();
	final HorizontalPanel languagePanel = new HorizontalPanel();
	final Button previousButton = new Button(constants.previous());
	final Button nextButton = new Button(constants.next());
	Anchor[] localeLinks;
	
	private WizardPage currentPage;
	final FormPage formPage = new FormPage(nextButton);
	final MoviePage moviePage = new MoviePage();
	final ReviewPage reviewPage = new ReviewPage();
	final DynamicContentPage dcPage = new DynamicContentPage();

	int currentPageNumber = 0;
	final int NUM_PAGES = 4;
	
	public void loadPage(int pageNumber) {
		if(pageNumber<0 || pageNumber>=NUM_PAGES) {
			pageNumber = 0;
		}
		
		currentPageNumber = pageNumber;
		vPanel.clear();
		vPanel.add(languagePanel);
		vPanel.setCellHorizontalAlignment(languagePanel, VerticalPanel.ALIGN_RIGHT);
		switch(pageNumber) {
			case 0:
				currentPage = formPage;
				previousButton.setEnabled(false);
				nextButton.setEnabled(true);
				break;
			case 1:
				currentPage = moviePage;
				previousButton.setEnabled(true);
				nextButton.setEnabled(true);
				break;
			case 2:
				currentPage = reviewPage;
				reviewPage.fillWith(formPage, moviePage);
				previousButton.setEnabled(true);
				nextButton.setEnabled(true);
				break;
			case 3:
				currentPage = dcPage;
				dcPage.fillWith(formPage);
				previousButton.setEnabled(true);
				nextButton.setEnabled(false);
				break;
		}
		
		vPanel.add(currentPage.getWidget());
		vPanel.add(hPanel);
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		class PreviousButtonHandler implements ClickHandler {
			public void onClick(ClickEvent event) {
				loadPage(currentPageNumber-1);
			}
		}
		
		class NextButtonHandler implements ClickHandler {
			public void onClick(ClickEvent event) {
				loadPage(currentPageNumber+1);
			}
		}
		
		PreviousButtonHandler pHandler = new PreviousButtonHandler();
		previousButton.addClickHandler(pHandler);
		NextButtonHandler nHandler = new NextButtonHandler();
		nextButton.addClickHandler(nHandler);
		
		languagePanel.setSpacing(10);
		String[] locales = LocaleInfo.getAvailableLocaleNames();
		localeLinks = new Anchor[locales.length];
		int i=0;
		for(final String l : locales) {
			String lName = l;
			if(lName.equals("default")) lName = "en";
			localeLinks[i] = new Anchor(lName);
			localeLinks[i].addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					changeLocale(l);
				}
			});
			languagePanel.add(localeLinks[i]);
			i++;
		}
		
		hPanel.add(previousButton);
		hPanel.add(nextButton);
		
		loadPage(0);
		
		RootPanel.get("interface").add(vPanel);
	}
	
	/**
     * JSNI method to change the locale of the application - it effectively
     * parses the existing URL and creates a new one for the chosen locale.
     *
     * Modified from http://www.mail-archive.com/google-web-toolkit@googlegroups.com/msg09290.html
     * on 30 Dec 2011, 8:53 PM (Paris Time)
     *
     * @param newLocale
     */
    private native void changeLocale(String newLocale)/*-{
        var currLocation = $wnd.location.toString();
        var noHistoryCurrLocArray = currLocation.split("#");
        var noHistoryCurrLoc = noHistoryCurrLocArray[0];
        var locArray = noHistoryCurrLoc.split("?");
        var hasLocale = false;
        var newParams = "?";
        if(locArray.length > 1) {
        	var params = locArray[1].split("&");
        	for(var i=0; i<params.length; i++) {
        		if(i>0) newParams += "&";
        		
        		var paramKeyVal = params[i].split("=");
        		if(paramKeyVal[0] == "locale") {
        			newParams += "locale=" + newLocale;
        			hasLocale = true;
        		}
        		else
        			newParams += params[i];
        	}
        }
        
        if(!hasLocale) {
        	if(params.length > 0) newParams += "&";
        	newParams += "locale=" + newLocale;
        }
        
        $wnd.location.href = locArray[0] + newParams;
    }-*/;
}
