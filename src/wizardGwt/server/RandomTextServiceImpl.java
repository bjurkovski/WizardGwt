package wizardGwt.server;

import java.util.Random;

import wizardGwt.client.RandomTextService;
import wizardGwt.client.wizardGwtConstants;
import wizardGwt.shared.FieldVerifier;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RandomTextServiceImpl extends RemoteServiceServlet implements
		RandomTextService {
	public String generateText(String input) throws IllegalArgumentException {
		String words[][] = {{"love","hate"}, {"chocolate", "ice cream", "cake"}};
		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		
		//String randomWords[] = constants.randomMsgWords().split(",");
		//return randomWords[0] + ", " + input + "!<br><br>" + randomWords[1] + " " + randomWords[2] + " " + randomWords[4];
		
		Random numberGenerator = new Random();
		int indices[] = {numberGenerator.nextInt(words[0].length),
						 numberGenerator.nextInt(words[1].length)}; 
		return "Hello, " + input + "!<br><br>I " + words[0][indices[0]] + " " + words[1][indices[1]];
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
}
