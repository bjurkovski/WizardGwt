package wizardGwt.client.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.google.gwt.http.client.Request;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

public class XMLCities extends XMLDocReader {
	private HashMap<String, Collection<String>> cities = new HashMap<String, Collection<String>>();
	
	public XMLCities(String docName) {
		super(docName);
	}

	@Override
	protected void proccess() {
		NodeList countryNodes = xmlDoc.getElementsByTagName("country");
	    for(int i=0; i<countryNodes.getLength(); i++) {
	    	Element country = (Element) countryNodes.item(i);
	    	ArrayList countryCities = new ArrayList<String>();
	    	NodeList cityNodes = country.getElementsByTagName("city");
    		for(int j=0; j<cityNodes.getLength(); j++) {
    			Element city = (Element) cityNodes.item(j);
    			countryCities.add(city.getAttribute("name"));
    			//cityField.addItem(city.getAttribute("name"));
    		}
	    	//countryField.addItem(country.getAttribute("name"));
    		cities.put(country.getAttribute("name"), countryCities);
	    }
	}

	@Override
	protected void error(Request res, Throwable throwable) {
	}

	public Collection<String> getAllCountries() throws Exception {
		if(!finishedReading) {
			throw new Exception();
		}
		
		return cities.keySet();
	}
	
	public Collection<String> getCities(String country) throws Exception {
		if(!finishedReading) {
			throw new Exception();
		}

		if(cities.containsKey(country)) {
			return cities.get(country);
		}
		else {
			return new ArrayList<String>();
		}
	}
}
