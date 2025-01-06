package users;

/**
 * Represents a shipping address for a customer. Extends the Address class.
 * This class is specifically used to handle shipping addresses in the system.
 * 
 * @author 24862664
 */
public class ShippingAddress extends Address {

    /**
     * Constructs a ShippingAddress with the specified details.
     * 
     * @param number The house/building number of the shipping address.
     * @param street The street name of the shipping address.
     * @param city The city of the shipping address.
     * @param country The country of the shipping address.
     * @param postCode The postal code of the shipping address.
     * @param customerId The ID of the customer to who this address belongs to.
     * @param isPrimary Indicates if this is the primary shipping address for the customer.
     */
    public ShippingAddress(String number, String street, String city, String country, String postCode, int customerId, boolean isPrimary) {
        super(number, street, city, country, postCode, customerId, isPrimary);
        setAddressType("shipping");
    }
}
