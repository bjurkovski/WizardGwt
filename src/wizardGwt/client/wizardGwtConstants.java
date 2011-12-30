package wizardGwt.client;

import com.google.gwt.i18n.client.Constants;

public interface wizardGwtConstants extends Constants {
	@DefaultStringValue("Name")
	String name();
	
	@DefaultStringValue("Last Name")
	String lastName();
	
	@DefaultStringValue("e-mail")
	String email();
	
	@DefaultStringValue("Date of Birth")
	String dateOfBirth();
	
	@DefaultStringValue("Country")
	String country();
	
	@DefaultStringValue("City")
	String city();
	
	@DefaultStringValue("Favorite Movie")
	String favMovie();
	
	@DefaultStringValue("Sex")
	String sex();
	
	@DefaultStringValue("Masc")
	String masc();
	
	@DefaultStringValue("Fem")
	String fem();
	
	@DefaultStringValue("Previous")
	String previous();
	
	@DefaultStringValue("Next")
	String next();
	
	@DefaultStringValue("Server Response:")
	String serverResponse();
	
	@DefaultStringValue("Please enter a valid e-mail.")
	String errorInvalidEmail();
	
	@DefaultStringValue("Please enter a valid date of birth (dd/mm/yyyy).")
	String errorInvalidDateOfBirth();
	
	@DefaultStringValue("Please enter a valid name.")
	String errorInvalidName();
	
	@DefaultStringValue("Please enter a valid last name.")
	String errorInvalidLastName();
	
	@DefaultStringValue("Couldn't get message from server.")
	String errorConnectingServer();
	
	@DefaultStringValue("WorldCities.xml")
	String worldCitiesFile();
}
