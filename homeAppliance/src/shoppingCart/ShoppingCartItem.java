package shoppingCart;

import appliances.Appliance;
import users.User;

public class ShoppingCartItem {
    private int id;
    private User user;
    private Appliance appliance;
    private String desc; // Cached description
    private double price; // Cached price

    public ShoppingCartItem(int id, User user, Appliance appliance) {
        if (user == null || appliance == null) {
            throw new IllegalArgumentException("User and Appliance cannot be null");
        }
        this.id = id;
        this.user = user;
        this.appliance = appliance;
        this.desc = appliance.getDescription();
        this.price = appliance.getPrice();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return user.getCustomerId();
    }

    public void setUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.user = user;
    }

    public int getItemId() {
        return appliance.getId();
    }

    public void setAppliance(Appliance appliance) {
        if (appliance == null) {
            throw new IllegalArgumentException("Appliance cannot be null");
        }
        this.appliance = appliance;
        this.desc = appliance.getDescription();
        this.price = appliance.getPrice();
    }

    public String getDesc() {
        return desc;
    }

    public double getPrice() {
        return price;
    }

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
