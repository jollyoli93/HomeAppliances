package users;

public abstract class Address {
    private int address_id;
    private int customerId;
    private String number;
    private String street;
    private String city;
    private String postCode;
    private String country;
    private boolean isPrimary;
    private String addressType;

    // Constructor
    protected Address(String number, String street, String city, String country, String postCode, int customerId) {
        setAddress(number, street, city, country, postCode, customerId);
    }

    public void setAddress(String number, String street, String city, String country, String postCode, int customerId) {
        if (number == null || street == null || city == null || country == null || postCode == null) {
            throw new IllegalArgumentException("Address fields cannot be null.");
        }
        this.number = number;
        this.street = street;
        this.city = city;
        this.country = country;
        this.postCode = postCode;
        this.customerId = customerId;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setMainAddress(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public String getAddressType() {
        return addressType;
    }

    protected void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return addressType + " Address: " + number + " " + street + ", " + city + " " + postCode + ", " + country;
    }
}

class ShippingAddress extends Address {
    public ShippingAddress(String number, String street, String city, String country, String postCode, int customerId) {
        super(number, street, city, country, postCode, customerId);
        setAddressType("Shipping");
    }
}

class BillingAddress extends Address {
    public BillingAddress(String number, String street, String city, String country, String postCode, int customerId) {
        super(number, street, city, country, postCode, customerId);
        setAddressType("Billing");
    }
}

