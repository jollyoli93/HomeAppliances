package users;

/**
 * Represents a billing address, extending the Address class. 
 * This class sets the address type to "billing" to distinguish it from other address types.
 * It inherits the functionality of the Address class but specifically identifies itself as a billing address.
 * 
 * @author 24862664
 */
public class BillingAddress extends Address {

    /**
     * Constructs a BillingAddress object with the specified details.
     * 
     * @param number The house or building number of the billing address.
     * @param street The street name of the billing address.
     * @param city The city where the billing address is located.
     * @param country The country where the billing address is located.
     * @param postCode The postal code of the billing address.
     * @param customerId The customer ID associated with the billing address.
     * @param isPrimary A boolean indicating whether this address is the primary address for the customer.
     */
    public BillingAddress(String number, String street, String city, String country, String postCode, int customerId, boolean isPrimary) {
        super(number, street, city, country, postCode, customerId, isPrimary);
        // Set the address type as "billing"
        setAddressType("billing");
    }
}
