package wizardGwt.client.xml;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

public abstract class XMLDocReader {
	protected boolean finishedReading;
	protected Document xmlDoc;
	
	public XMLDocReader(String docName) {
		try {
			finishedReading = false;
			Request rq = new RequestBuilder(RequestBuilder.GET, docName).sendRequest("", new RequestCallback() {
			  @Override
			  public void onResponseReceived(Request req, Response resp) {
				  xmlDoc = XMLParser.parse(resp.getText());
				  proccess();
				  finishedReading = true;
			  }
		
			  @Override
			  public void onError(Request res, Throwable throwable) {
				  error(res, throwable);
				  finishedReading = true;
			  }
			});
		} catch(RequestException e) {
			finishedReading = true;
		}
	}
	
	protected abstract void proccess();
	protected abstract void error(Request res, Throwable throwable);
}
