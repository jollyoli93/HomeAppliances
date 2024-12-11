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
		return this.firstName + this.lastName;
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
	
	public void setTelephoneNum (String number) {
		try {
			if (number.length() == 11) {
				this.telephoneNum = number;
			}
		} catch (Exception e) {
			System.out.println("Invalid telephone number");
		}
	}
	
	//Handle user addresses
	public void addAddress(Address address) {
	    if (addressList == null) {
	        addressList = new ArrayList<>();
	    }
	    addressList.add(address);
	}

	public ArrayList<Address> getAddresses() {
	    return new ArrayList<>(addressList); 
	}

	public void removeAddress(Address address) {
	    if (addressList != null) {
	        addressList.remove(address);
	    }
	}
	
	//Handle Passwords
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		//hashpassword
		this.password = password;
	}
	
	public abstract String getRole ();

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

}
