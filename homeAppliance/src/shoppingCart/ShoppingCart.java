package shoppingCart;

import users.User;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private User user;
    private List<ShoppingCartItem> items;

    public ShoppingCart(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.user = user;
        this.items = new ArrayList<>();
    }

    // Add an item to the cart
    public void addItem(ShoppingCartItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        items.add(item);
    }

    public boolean removeItem(int itemId) {
        return items.removeIf(item -> item.getItemId() == itemId);
    }

    //map item price into stream
    public double getTotalPrice() {
    	double total = items.stream()
                .mapToDouble(ShoppingCartItem::getPrice)
                .sum();
    	
    	//No VAT for business users
    	if (user.getRole().equals("business")) {
    		return total * 0.8;
    	}
    		
        return total;
    }

    public List<ShoppingCartItem> getItems() {
        return new ArrayList<>(items);
    }

    public void clearCart() {
        items.clear();
    }

    public User getUser() {
        return user;
    }

    public String toString() {
        return "ShoppingCart{" +
                "user=" + user.getCustomerId() +
                ", items=" + items +
                '}';
    }
}
