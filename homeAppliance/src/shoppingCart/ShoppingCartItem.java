package shoppingCart;

import appliances.Appliance;
import users.User;

/**
 * Represents an item in the shopping cart, including the user, appliance, 
 * description, and price associated with it.
 * 
 * @author 24862664
 */
public class ShoppingCartItem {
    private int id;
    private User user;
    private Appliance appliance;
    private String desc; // Cached description
    private double price; // Cached price

    /**
     * Constructs a shopping cart item for the specified user and appliance.
     * 
     * @param user The user who owns the item. Must not be null.
     * @param appliance The appliance associated with the item. Must not be null.
     * @throws IllegalArgumentException if user or appliance is null.
     */
    public ShoppingCartItem(User user, Appliance appliance) {
        if (user == null || appliance == null) {
            throw new IllegalArgumentException("User and Appliance cannot be null");
        }
        this.user = user;
        this.appliance = appliance;
        this.desc = appliance.getDescription();
        this.price = appliance.getPrice();
    }

    /**
     * Returns the ID of the shopping cart item.
     * 
     * @return The ID of the item.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the shopping cart item.
     * 
     * @param id The ID to set for the item.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the user ID associated with the item.
     * 
     * @return The user ID of the associated user.
     */
    public int getUserId() {
        return user.getCustomerId();
    }

    /**
     * Sets the user for the shopping cart item.
     * 
     * @param user The user to associate with the item. Must not be null.
     * @throws IllegalArgumentException if the user is null.
     */
    public void setUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.user = user;
    }

    /**
     * Returns the ID of the appliance associated with the item.
     * 
     * @return The appliance ID.
     */
    public int getItemId() {
        return appliance.getId();
    }

    /**
     * Sets the appliance for the shopping cart item.
     * 
     * @param appliance The appliance to associate with the item. Must not be null.
     * @throws IllegalArgumentException if the appliance is null.
     */
    public void setAppliance(Appliance appliance) {
        if (appliance == null) {
            throw new IllegalArgumentException("Appliance cannot be null");
        }
        this.appliance = appliance;
        this.desc = appliance.getDescription();
        this.price = appliance.getPrice();
    }

    /**
     * Returns the description of the appliance for the item.
     * 
     * @return The description of the appliance.
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Returns the price of the appliance for the item.
     * 
     * @return The price of the appliance.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns a string representation of the shopping cart item.
     * 
     * @return A string representation of the item, including user ID, appliance ID, description, and price.
     */
    @Override
    public String toString() {
        return "ShoppingCartItem{" +
                "id=" + id +
                ", userId=" + getUserId() +
                ", itemId=" + getItemId() +
                ", desc='" + desc + '\'' +
                ", price=" + price +
                '}';
    }
}
