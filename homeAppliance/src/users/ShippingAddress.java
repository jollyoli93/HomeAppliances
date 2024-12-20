package users;

public class ShippingAddress extends Address {
    public ShippingAddress(String number, String street, String city, String country, String postCode, int customerId, boolean isPrimary) {
        super(number, street, city, country, postCode, customerId, isPrimary);
        setAddressType("shipping");
    }
}
