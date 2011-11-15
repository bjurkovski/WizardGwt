package wizardGwt.client.pages;

import wizardGwt.client.wizardGwtConstants;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.core.client.GWT;

public abstract class WizardPage {
	final Label errorLabel = new Label();
	final wizardGwtConstants constants = GWT.create(wizardGwtConstants.class);
	
	public abstract Widget getWidget();
	
	protected String toBold(String str) {
		return "<b>" + str + "</b>";
	}
}
