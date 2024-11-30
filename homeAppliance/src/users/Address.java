package users;

public abstract class Address {
	String customerId;
	int number;
	String street;
	String city;
	String postCode;
	boolean mainAddress;
	String addressType;
	
	public void setAddress(int number, String street, String city, String postCode, String customerId) {
		this.number = number;
		this.street = street;
		this.city = city;
		this.postCode = postCode;
		this.customerId = customerId;
	}
	
	public String getCustomer(String customerId){
		return this.customerId;
	}
	
	public void setMainAddress(boolean main) {
		this.mainAddress = main;
	}
	
	public void setAddressType(String type) {
		this.addressType = type;
	}
}

class ShippingAddress extends Address {
	public ShippingAddress() {
		setAddressType("Shipping");
	}
}

class BillingAddress extends Address {
	public BillingAddress () {
		setAddressType("Billing");
	}
}

//class BuisnessAddress {
//	
//}
