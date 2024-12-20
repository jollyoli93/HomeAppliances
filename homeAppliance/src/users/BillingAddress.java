package users;

public class BillingAddress extends Address {
    public BillingAddress(String number, String street, String city, String country, String postCode, int customerId, boolean isPrimary) {
        super(number, street, city, country, postCode, customerId, isPrimary);
        setAddressType("billing");
    }
}
