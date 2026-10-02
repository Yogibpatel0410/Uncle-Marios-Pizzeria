import java.util.ArrayList;
import java.util.List;

public class CustomCalzone implements ICalzone {
    private String name;
    private String cheese;
    private List<String> stuffings;

    private static final double BASE_PRICE = 11.99;
    private static final double STUFFING_PRICE = 0.50;
    private static final double EXTRA_CHEESE_PRICE = 0.50;

    public CustomCalzone() {
        this("Custom Calzone");
    }

    public CustomCalzone(String name) {
        this.name = name;
        this.cheese = "Mozzarella Cheese";
        this.stuffings = new ArrayList<>();
    }

    public void setCheese(String cheese) {
        this.cheese = cheese;
    }

    @Override
    public void addStuffings(String stuffing) {
        stuffings.add(stuffing);
    }

    @Override
    public void order() {
        System.out.println("Ordering calzone: " + name);
    }

    @Override
    public void pay() {
        System.out.println("Calzone marked as paid.");
    }

    @Override
    public double getPrice() {
        double price = BASE_PRICE;

        if (cheese.equalsIgnoreCase("Extra Mozzarella")
                || cheese.equalsIgnoreCase("Three-Cheese Blend")) {
            price += EXTRA_CHEESE_PRICE;
        }

        price += stuffings.size() * STUFFING_PRICE;
        return price;
    }

    @Override
    public String toString() {
        String stuffingDisplay = stuffings.isEmpty()
                ? "None"
                : String.join(", ", stuffings);

        return "Calzone: " + name
                + "\nCheese: " + cheese
                + "\nStuffings: " + stuffingDisplay
                + String.format("\nCalzone Price: $%.2f", getPrice());
    }
}