public class CartItem {
    private IMenuItems item;
    private int quantity;

    public CartItem(IMenuItems item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public IMenuItems getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity() {
        quantity++;
    }

    public double getTotalPrice() {
        return item.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return quantity + "x " + item;
    }
}