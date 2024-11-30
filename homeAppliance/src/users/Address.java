package users;

public abstract class Address {
	private int customerId;
	private int number;
	private String street;
	private String city;
	private String postCode;
	private boolean mainAddress;
	private String addressType;

	
	public void setAddress(int number, String street, String city, String postCode, int customerId) {
		this.number = number;
		this.street = street;
		this.city = city;
		this.postCode = postCode;
		this.customerId = customerId;
	}
	
	public int getCustomerId() {
	    return this.customerId;
	}

	
	public void setMainAddress(boolean main) {
		this.mainAddress = main;
	}
	
	public void setAddressType(String type) {
		this.addressType = type;
	}
	
    public String toString() {
        return number + " " + street + ", " + city + " " + postCode;
    }
}

class ShippingAddress extends Address {
	public ShippingAddress(int number, String street, String city, String postCode, int customerId) {
	    setAddress(number, street, city, postCode, customerId);
	    setAddressType("Shipping");
	}

}

class BillingAddress extends Address {
	public BillingAddress(int number, String street, String city, String postCode, int customerId) {
	    setAddress(number, street, city, postCode, customerId);
	    setAddressType("Billing");
	}

}

//class BuisnessAddress {
//	
//}
