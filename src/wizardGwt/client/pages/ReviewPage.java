package wizardGwt.client.pages;


import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ReviewPage extends WizardPage {
	/*
	grid.clear();
	
	grid.setWidget(1, 0, new HTML(nameField.getText()));
	grid.setWidget(1, 1, new HTML(lastNameField.getText()));
	grid.setWidget(1, 2, new HTML(emailField.getText()));
	
	grid.setWidget(3, 0, new HTML(dateOfBirthField.getText()));
	grid.setWidget(3, 1, new HTML(countryField.getText()));
	grid.setWidget(3, 2, new HTML(cityField.getText()));
	
	boolean masc = sexMascField.getValue();
	if(masc) {
		grid.setWidget(5, 0, new HTML("Masc"));
	}
	else {
		grid.setWidget(5, 0, new HTML("Fem"));
	}
	
	vPanel.clear();
	vPanel.add(grid);
	vPanel.add(hPanel);*/
	
	public final Label name = new Label();
	public final Label lastName = new Label();
	public final Label dateOfBirth = new Label();
	public final Label country = new Label();
	public final Label city = new Label();
	public final Label email = new Label();
	public final Label sex = new Label();
	private final Grid grid = new Grid(7, 2);
	
	public ReviewPage() {
		grid.setWidget(0, 0, new HTML(toBold(constants.name())));
		grid.setWidget(0, 1, name);
		
		grid.setWidget(1, 0, new HTML(toBold(constants.lastName())));
		grid.setWidget(1, 1, lastName);
		
		grid.setWidget(2, 0, new HTML(toBold(constants.dateOfBirth())));
		grid.setWidget(2, 1, dateOfBirth);
		
		grid.setWidget(3, 0, new HTML(toBold(constants.city())));
		grid.setWidget(3, 1, city);
		
		grid.setWidget(4, 0, new HTML(toBold(constants.country())));
		grid.setWidget(4, 1, country);
		
		grid.setWidget(5, 0, new HTML(toBold(constants.email())));
		grid.setWidget(5, 1, email);
		
		grid.setWidget(6, 0, new HTML(toBold(constants.sex())));
		grid.setWidget(6, 1, sex);
	}
	
	public void fillWith(FormPage fp) {
		name.setText(fp.nameField.getText());
		lastName.setText(fp.lastNameField.getText());
		dateOfBirth.setText(fp.dateOfBirthField.getText());
		country.setText(fp.countryField.getValue(fp.countryField.getSelectedIndex()));
		city.setText(fp.cityField.getValue(fp.cityField.getSelectedIndex()));
		//country.setText(fp.countryField.getText());
		//city.setText(fp.cityField.getText());
		email.setText(fp.emailField.getText());
		if(fp.sexMascField.getValue()) {
			sex.setText(constants.masc());
		} else {
			sex.setText(constants.fem());
		}
	}

	@Override
	public Widget getWidget() {
		return grid;
	}
}
