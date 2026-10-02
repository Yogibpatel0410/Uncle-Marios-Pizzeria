import java.util.ArrayList;
import java.util.List;

public class CustomPizza2 implements IPizza2 {

    private String name;
    private String size;
    private String crust;
    private String sauce;
    private String cheese;
    private List<String> toppings;

    // Pizza size prices
    private static final double SMALL_PRICE = 10.99;
    private static final double MEDIUM_PRICE = 13.99;
    private static final double LARGE_PRICE = 16.99;
    private static final double XL_PRICE = 19.99;

    // Extra charges
    private static final double TOPPING_PRICE = 0.50;
    private static final double STUFFED_CRUST_PRICE = 2.00;
    private static final double EXTRA_CHEESE_PRICE = 0.50;
    private static final double THREE_CHEESE_PRICE = 0.50;

    public CustomPizza2() {
        this("Custom Pizza");
    }

    public CustomPizza2(String name) {
        this.name = name;

        // Default pizza options
        this.size = "Medium";
        this.crust = "Regular";
        this.sauce = "Tomato";
        this.cheese = "Mozzarella Cheese";

        this.toppings = new ArrayList<>();
    }

    @Override
    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public void setCrust(String crust) {
        this.crust = crust;
    }

    @Override
    public void setSauce(String sauce) {
        this.sauce = sauce;
    }

    @Override
    public void setCheese(String cheese) {
        this.cheese = cheese;
    }

    @Override
    public void addToppings(String topping) {
        toppings.add(topping);
    }

    @Override
    public void order() {
        System.out.println("Ordering pizza: " + name);
    }

    @Override
    public void pay() {
        System.out.println("Pizza marked as paid.");
    }

    @Override
    public double getPrice() {

        double price;

        // Set base price based on pizza size
        switch (size.toLowerCase()) {

            case "small":
                price = SMALL_PRICE;
                break;

            case "large":
                price = LARGE_PRICE;
                break;

            case "xl":
                price = XL_PRICE;
                break;

            case "medium":
            default:
                price = MEDIUM_PRICE;
                break;
        }

        // Stuffed crust costs an additional $2
        if (crust.equalsIgnoreCase("Stuffed")) {
            price += STUFFED_CRUST_PRICE;
        }

        // Extra cheese options
        if (cheese.equalsIgnoreCase("Extra Mozzarella")) {
            price += EXTRA_CHEESE_PRICE;
        }

        if (cheese.equalsIgnoreCase("Three-Cheese Blend")) {
            price += THREE_CHEESE_PRICE;
        }

        // Every topping costs $0.50
        price += toppings.size() * TOPPING_PRICE;

        return price;
    }

    @Override
    public String toString() {

        String toppingDisplay;

        if (toppings.isEmpty()) {
            toppingDisplay = "None";
        } else {
            toppingDisplay = String.join(", ", toppings);
        }

        return "Pizza: " + name +
                "\nSize: " + size +
                "\nCrust: " + crust +
                "\nSauce: " + sauce +
                "\nCheese: " + cheese +
                "\nToppings: " + toppingDisplay +
                String.format("\nPizza Price: $%.2f", getPrice());
    }
}