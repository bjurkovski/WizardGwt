package wizardGwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>RandomTextService</code>.
 */
public interface RandomTextServiceAsync {
	void generateText(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;
}
