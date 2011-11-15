package wizardGwt.client.pages;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import wizardGwt.client.AsyncCall;
import wizardGwt.client.xml.XMLCities;
import wizardGwt.shared.FieldVerifier;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class FormPage extends WizardPage {
	public final TextBox nameField = new TextBox();
	public final TextBox lastNameField = new TextBox();
	public final TextBox dateOfBirthField = new TextBox();
	//public final TextBox countryField = new TextBox();
	public final ListBox countryField = new ListBox();
	//public final TextBox cityField = new TextBox();
	public final ListBox cityField = new ListBox();
	public final TextBox emailField = new TextBox();
	public final RadioButton sexMascField = new RadioButton("sexField", constants.masc());
	public final RadioButton sexFemField = new RadioButton("sexField", constants.fem());
	private final VerticalPanel vPanel = new VerticalPanel();
	private final Grid grid = new Grid(6, 3);
	
	private Button nextButton; 
	
	private boolean validName = false;
	private boolean validLastName = false;
	private boolean validEmail = false;
	private boolean validDateOfBirth = true;
	
	Document citiesXML;
	XMLCities xml;
	
	public FormPage(Button nextButton) {
		this.nextButton = nextButton;
		
		xml = new XMLCities(constants.worldCitiesFile());
		
		new AsyncCall() {
			public void process() throws Exception {
				List<String> countries = new ArrayList<String>(xml.getAllCountries());
				Collections.sort(countries);
				for (Iterator country=countries.iterator(); country.hasNext();) {
				    countryField.addItem((String) country.next());
				}
				countryField.fireEvent(new ChangeEvent() {});
			}
		};
		
		countryField.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				new AsyncCall() {
					public void process() throws Exception {
						String selectedCountry = countryField.getValue(countryField.getSelectedIndex());
						ArrayList<String> cities = (ArrayList<String>) xml.getCities(selectedCountry);
						Collections.sort(cities);
						cityField.clear();
						for (int i=0; i<cities.size(); i++) {
							cityField.addItem((String) cities.get(i));
						}
					}
				};
			}
		});
		
		grid.setWidget(0, 0, new HTML(constants.name()));
		grid.setWidget(1, 0, nameField);
		grid.setWidget(0, 1, new Label(constants.lastName()));
		grid.setWidget(1, 1, lastNameField);
		grid.setWidget(0, 2, new Label(constants.email()));
		grid.setWidget(1, 2, emailField);
		
		grid.setWidget(2, 0, new Label(constants.dateOfBirth()));
		grid.setWidget(3, 0, dateOfBirthField);
		grid.setWidget(2, 1, new Label(constants.country()));
		grid.setWidget(3, 1, countryField);
		grid.setWidget(2, 2, new Label(constants.city()));
		grid.setWidget(3, 2, cityField);
		
		HorizontalPanel sexPanel = new HorizontalPanel();
		sexPanel.add(sexMascField);
		sexPanel.add(sexFemField);
		
		grid.setWidget(4, 0, new Label(constants.sex()));
		grid.setWidget(5, 0, sexPanel);
		
		vPanel.add(errorLabel);
		vPanel.add(grid);
		//grid.setWidget(5, 2, errorLabel);
		
		sexMascField.setValue(true);
		dateOfBirthField.setValue("01/01/1980");
		
		setupValidations();
	}
	
	private void showErrorMessages() {
		String msg = "";
		
		if(!validName) {
			msg += constants.errorInvalidName() + " ";
		}
		
		if(!validLastName) {
			msg += constants.errorInvalidLastName() + " ";
		}
		
		if(!validEmail) {
			msg += constants.errorInvalidEmail() + " ";
		}
		
		if(!validDateOfBirth) {
			msg += constants.errorInvalidDateOfBirth() + " ";
		}
		
		errorLabel.setText(msg);
		errorLabel.addStyleName("serverResponseLabelError");
	}
	
	public boolean formIsValid() {
		return (validEmail && validDateOfBirth 
				&& validName && validLastName);
	}
	
	private void setupValidations() {
		final DialogBox db = new DialogBox();
		
		class FieldVerifierHandler implements BlurHandler {
			private String fieldName;
			
			public FieldVerifierHandler(String fieldName) {
				this.fieldName = fieldName;
			}
			
			@Override
			public void onBlur(BlurEvent event) {
				if(fieldName == "email") {
					validEmail = FieldVerifier.isValidEmail(emailField.getText());
				}
				else if(fieldName == "dateOfBirth") {
					validDateOfBirth = FieldVerifier.isValidDate(dateOfBirthField.getText());
				}
				else if(fieldName == "name") {
					validName = FieldVerifier.isValidName(nameField.getText());
				}
				else if(fieldName == "lastName") {
					validLastName = FieldVerifier.isValidName(lastNameField.getText());
				}
				
				nextButton.setEnabled(formIsValid());
				showErrorMessages();
			}
		}
		
		emailField.addBlurHandler(new FieldVerifierHandler("email"));
		dateOfBirthField.addBlurHandler(new FieldVerifierHandler("dateOfBirth"));
		nameField.addBlurHandler(new FieldVerifierHandler("name"));
		lastNameField.addBlurHandler(new FieldVerifierHandler("lastName"));
	}

	@Override
	public Widget getWidget() {
		nextButton.setEnabled(formIsValid());
		//return grid;
		return vPanel;
	}

}
