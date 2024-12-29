package users;

import java.util.ArrayList;

public abstract class User {
	private String firstName;
	private String lastName;
	private String username;
	private String emailAddress;
	private String password;
	private int customerId;
	private String telephoneNum;
	private String businessName;
	
	private ArrayList<Address> addressList;

	User(String firstname, String lastName, String emailAddress, String username, String password) {
		this.firstName = firstname;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.username = username;
		this.setPassword(password);
	}

	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public int getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	//handle telephone number
	public String getTelephoneNum () {
		return this.telephoneNum;
	}
	
	public void setTelephoneNum(String number) {
		if (number == null) {
			this.telephoneNum = null;
		} else if (number != null && number.matches("\\d{11}")) {
	        this.telephoneNum = number;
	    } else {
	        throw new IllegalArgumentException("Invalid telephone number. It must be 11 digits.");
	    }
	}

	//Handle user addresses
	public void addAddress(Address address) {
	    if (address == null) {
	        throw new IllegalArgumentException("Address cannot be null.");
	    }
	    if (addressList == null) {
	        addressList = new ArrayList<>();
	    }
	    addressList.add(address);
	}

	public ArrayList<Address> getAddresses() {
	    return addressList == null ? new ArrayList<>() : new ArrayList<>(addressList);
	}


	public void removeAddress(Address address) {
	    if (addressList != null) {
	        addressList.remove(address);
	    }
	}
	
	//Handle Passwords
	public String getPassword() {

//		    // Use BCrypt for hashing (example)
//		    this.password = BCrypt.hashpw(password, BCrypt.gensalt());


		return this.password;
	}

	public void setPassword(String password) {
		//hashpassword
		this.password = password;
	}
	
//	public boolean validatePassword(String inputPassword) {
//	    return BCrypt.checkpw(inputPassword, this.password);
//	}

	
	public abstract String getRole ();

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	
	public String toHTMLString() {
	    return "<tr>" +
	            "<td>" + firstName + "</td>" +
	            "<td>" + lastName + "</td>" +
	            "<td>" + username + "</td>" +
	            "<td>" + emailAddress + "</td>" +
	            "<td>" + password + "</td>" +
	            "<td>" + customerId + "</td>" +
	            "<td>" + telephoneNum + "</td>" +
	            "<td>" + businessName + "</td>" +
	            "</tr>";
	}

}
