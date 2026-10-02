import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public void addItem(IMenuItems item) {
        addItem(item, 1);
    }

    public void addItem(IMenuItems item, int quantity) {
        if (item == null) {
            return;
        }

        if (quantity <= 0) {
            quantity = 1;
        }

        items.add(new CartItem(item, quantity));

        System.out.println();
        System.out.println(quantity + " item(s) added to cart!");
    }

    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            CartItem removed = items.remove(index);

            System.out.println();
            System.out.println("Item removed from cart.");
            System.out.println(removed.getItem());
        } else {
            System.out.println("Invalid cart item.");
        }
    }

    public double getSubtotal() {
        double subtotal = 0;

        for (CartItem cartItem : items) {
            subtotal += cartItem.getTotalPrice();
        }

        return subtotal;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getItemCount() {
        int count = 0;

        for (CartItem cartItem : items) {
            count += cartItem.getQuantity();
        }

        return count;
    }

    public void clear() {
        items.clear();
    }

    public void displayCart() {
        System.out.println();
        System.out.println("YOUR CART");

        if (items.isEmpty()) {
            System.out.println();
            System.out.println("Your cart is empty.");
            return;
        }

        for (int i = 0; i < items.size(); i++) {
            CartItem cartItem = items.get(i);

            System.out.println();
            System.out.println((i + 1) + ". Quantity: " + cartItem.getQuantity());
            System.out.println(cartItem.getItem());

            if (cartItem.getQuantity() > 1) {
                System.out.printf(
                        "Item Total: $%.2f%n",
                        cartItem.getTotalPrice()
                );
            }
        }

        System.out.println();
        System.out.printf("Cart Subtotal: $%.2f%n", getSubtotal());
    }
}