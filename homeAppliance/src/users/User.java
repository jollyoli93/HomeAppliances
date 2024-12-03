package users;

import java.util.ArrayList;

public abstract class User {
	private String firstName;
	private String lastName;
	private String username;
	private String emailAddress;
	private String password;
	private int customerId;
	protected String businessName;
	private String telephoneNum;
	private String role;
	
	private ArrayList<Address> addressList;
	
	//factory pattern ???
	
	User(String firstname, String lastName, String emailAddress, String username, String password){
		this.firstName = firstname;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.username = username;
		this.setPassword(password);
	}

	User(String firstname, String lastName, String emailAddress, String username, String password, String telephoneNum){
		this.firstName = firstname;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.username = username;
		this.setPassword(password);
		this.setTelephoneNum(telephoneNum);
		this.addressList = new ArrayList<Address>();
	}
	
	User(String firstname, String lastName, String emailAddress, String username, String password, String telephoneNum, String businessName ){
		this.firstName = firstname;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.username = username;
		this.businessName = businessName;
		this.setPassword(password);
		this.setTelephoneNum(telephoneNum);
		this.addressList = new ArrayList<Address>();
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
	
	
	public String getBusinessName() {
		return this.businessName;
	}
	
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	public void setBusinessName (String name) {
		this.businessName = name;
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
			System.out.println("Invalid number");
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

}
