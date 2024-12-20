package users;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    private static final List<String> addressTypeList = new ArrayList<>();

    // Constructor
    protected Address(String number, String street, String city, String country, String postCode, int customerId, boolean isPrimary) {
        setAddress(number, street, city, country, postCode, customerId, isPrimary);
    }

    public void setAddress(String number, String street, String city, String country, String postCode, int customerId, boolean isPrimary) {
        if (number == null || street == null || city == null || country == null || postCode == null) {
            throw new IllegalArgumentException("Address fields cannot be null.");
        }
        this.number = number;
        this.street = street;
        this.city = city;
        this.country = country;
        this.postCode = postCode;
        this.customerId = customerId;
        this.isPrimary = isPrimary;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
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
        synchronized (addressTypeList) {
            if (!addressTypeList.contains(addressType)) {
                addressTypeList.add(addressType);
            }
        }
    }

    public static List<String> getAddressTypes() {
        return Collections.unmodifiableList(addressTypeList);
    }

    @Override
    public String toString() {
        return addressType + " Address: " + number + " " + street + ", " + city + " " + postCode + ", " + country;
    }
}
