package wizardGwt.client.pages;


import wizardGwt.client.RandomTextService;
import wizardGwt.client.RandomTextServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class DynamicContentPage extends WizardPage {	
	private final Grid grid = new Grid(3, 1);
	private final RandomTextServiceAsync randomTextService = GWT.create(RandomTextService.class);
	private String name;
	
	public DynamicContentPage() {
		grid.setWidget(0, 0, new HTML(toBold(constants.serverResponse())));
	}
	
	public void fillWith(FormPage page) {
		name = page.nameField.getText();
	}
	
	@Override
	public Widget getWidget() {
		randomTextService.generateText(name,
				new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						grid.setWidget(2, 0, new HTML(constants.errorConnectingServer()));
					}

					public void onSuccess(String result) {
						grid.setWidget(2, 0, new HTML(result));
					}
				});
		
		return grid;
	}
}
