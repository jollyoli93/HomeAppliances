package users;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract class representing a users address. This class contains address parameters such as
 * (such as number, street, city, country, postCode, customerId, etc.) and provides methods for setting and getting
 * address details. It also allows setting one address as the primary address for a customer and manages a list of
 * address types.
 * 
 * @author 24862664
 */
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

    /**
     * Constructor for creating an address object.
     * 
     * @param number The house or building number.
     * @param street The name of the street.
     * @param city The name of the city.
     * @param country The name of the country.
     * @param postCode The postal code for the address.
     * @param customerId The ID of the customer associated with the address.
     * @param isPrimary A flag indicating whether this is the primary address.
     */
    protected Address(String number, String street, String city, String country, String postCode, int customerId, boolean isPrimary) {
        setAddress(number, street, city, country, postCode, customerId, isPrimary);
    }

    /**
     * Sets the full details of the address.
     * 
     * @param number The house or building number.
     * @param street The name of the street.
     * @param city The name of the city.
     * @param country The name of the country.
     * @param postCode The postal code for the address.
     * @param customerId The ID of the customer associated with the address.
     * @param isPrimary A flag indicating whether this is the primary address.
     * @throws IllegalArgumentException if any of the address fields is null.
     */
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

    /**
     * Returns the house or building number of the address.
     * 
     * @return The number of the address.
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the house or building number of the address.
     * 
     * @param number The number of the address.
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Returns the name of the street of the address.
     * 
     * @return The street of the address.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the name of the street of the address.
     * 
     * @param street The street name of the address.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Returns the name of the city of the address.
     * 
     * @return The city of the address.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the name of the city of the address.
     * 
     * @param city The city name of the address.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns the name of the country of the address.
     * 
     * @return The country of the address.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the name of the country of the address.
     * 
     * @param country The country name of the address.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Returns the postal code of the address.
     * 
     * @return The postal code of the address.
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * Sets the postal code of the address.
     * 
     * @param postCode The postal code of the address.
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * Returns the address ID.
     * 
     * @return The ID of the address.
     */
    public int getAddress_id() {
        return address_id;
    }

    /**
     * Sets the address ID.
     * 
     * @param address_id The ID to be set for the address.
     */
    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    /**
     * Returns the customer ID associated with the address.
     * 
     * @return The customer ID of the address.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Returns whether the address is the primary address for the customer.
     * 
     * @return True if the address is primary, false otherwise.
     */
    public boolean isPrimary() {
        return isPrimary;
    }

    /**
     * Sets whether the address should be marked as the primary address.
     * 
     * @param isPrimary True if the address should be set as primary, false otherwise.
     */
    public void setMainAddress(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    /**
     * Returns the type of address (e.g., billing, shipping).
     * 
     * @return The type of the address.
     */
    public String getAddressType() {
        return addressType;
    }

    /**
     * Sets the type of address (e.g., billing, shipping) and ensures the address type is unique in the list.
     * 
     * @param addressType The type of address to be set.
     */
    protected void setAddressType(String addressType) {
        this.addressType = addressType;
        synchronized (addressTypeList) {
            if (!addressTypeList.contains(addressType)) {
                addressTypeList.add(addressType);
            }
        }
    }

    /**
     * Returns a list of all unique address types that have been set across all address instances.
     * 
     * @return An unmodifiable list of address types.
     */
    public static List<String> getAddressTypes() {
        return Collections.unmodifiableList(addressTypeList);
    }

    /**
     * Returns a string representation of the address.
     * 
     * @return A formatted string representing the address.
     */
    @Override
    public String toString() {
        return addressType + " Address: " + number + " " + street + ", " + city + " " + postCode + ", " + country;
    }
}
