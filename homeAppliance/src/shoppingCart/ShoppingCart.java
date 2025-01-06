package shoppingCart;

import users.User;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a shopping cart for a user, allowing items to be added, removed, 
 * and total prices calculated. Also supports clearing the cart.
 * 
 * @author 24862664
 */
public class ShoppingCart {
    private User user;
    private List<ShoppingCartItem> items;

    /**
     * Constructs a shopping cart for the specified user.
     * 
     * @param User object. Unique for the user who owns the shopping cart. Must not be null.
     * @throws IllegalArgumentException if the user is null
     */
    public ShoppingCart(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.user = user;
        this.items = new ArrayList<>();
    }

    /**
     * Adds an item to the shopping cart.
     * 
     * @param item The item to add. Must not be null. 
     * @throws IllegalArgumentException if the item is {@code null}.
     */
    public void addItem(ShoppingCartItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        items.add(item);
    }

    /**
     * Removes an item from the cart by its ID.
     * 
     * @param itemId The ID of the item to remove.
     * @return true if the item was successfully removed, false otherwise.
     */
    public boolean removeItem(int itemId) {
        return items.removeIf(item -> item.getItemId() == itemId);
    }

    /**
     * Calculates the total price of all items in the cart, applying a discount 
     * for business users (20% off).
     * 
     * @return The total price of the cart after any applicable discounts.
     */
    public double getTotalPrice() {
        double total = items.stream()
                .mapToDouble(ShoppingCartItem::getPrice)
                .sum();
        
        // No VAT for business users
        if (user.getRole().equals("business")) {
            return total * 0.8;
        }
        
        return total;
    }

    /**
     * Returns a list of items currently in the cart.
     * 
     * @return A list of shopping cart items.
     */
    public List<ShoppingCartItem> getItems() {
        return new ArrayList<>(items);
    }

    /**
     * Clears all items from the shopping cart.
     */
    public void clearCart() {
        items.clear();
    }

    /**
     * Returns the user associated with the shopping cart.
     * 
     * @return The user associated with this cart.
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns a string representation of the shopping cart.
     * 
     * @return A string representation of the shopping cart.
     */
    @Override
    public String toString() {
        return "ShoppingCart{" +
                "user=" + user.getCustomerId() +
                ", items=" + items +
                '}';
    }
}
