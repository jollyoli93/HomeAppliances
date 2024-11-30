package users;

import java.util.ArrayList;
import java.util.function.BooleanSupplier;

public abstract class User {
	private String firstName;
	private String lastName;
	private String username;
	private String emailAddress;
	private String password;
	private int customerId;
	protected String buisnessName = null;
	private String telephoneNum;
	private boolean admin;
	
	private ArrayList<Address> addressList;
	
	User(String firstname, String lastName, String emailAddress, String password, String username, String telephoneNum){
		this.firstName = firstname;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.username = username;
		this.password = password;
		this.telephoneNum = telephoneNum;
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
	
	public String getTelephoneNum () {
		return this.telephoneNum;
	}
	
	public String getBusinessName() {
		return this.buisnessName;
	};
	
	public boolean getAdminStatus() {
		return admin;
	}
	
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	public void setBusinessName (String name) {
		this.buisnessName = name;
	};
	public void setAdmin (boolean isAdmin) {
		this.admin = isAdmin;
	};

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
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


}

class CustomerUser extends User{

	CustomerUser(String firstname, String lastName, String emailAddress, String username, String password, String telephoneNum) {
		super(firstname, lastName, emailAddress, username, password, telephoneNum);
		
	}
	
	
}


class AdminUser extends User {

	AdminUser(String firstname, String lastName, String emailAddress, String username,String password, String telephoneNum) {
		super(firstname, lastName, emailAddress, username, password, telephoneNum);
		setAdmin(true);
	}

}
