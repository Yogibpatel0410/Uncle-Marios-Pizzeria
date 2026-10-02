public class SpecialtyPizza implements IMenuItems {

    private String name;
    private String size;
    private String description;
    private double price;

    public SpecialtyPizza(
            String name,
            String size,
            String description,
            double price) {

        this.name = name;
        this.size = size;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void order() {
        System.out.println(
                name + " ordered.");
    }

    @Override
    public void pay() {
        System.out.println(
                name + " marked as paid.");
    }

    @Override
    public String toString() {

        return name
                + " Pizza"
                + " (" + size + ")"
                + " - "
                + description
                + String.format(
                        " - $%.2f",
                        price);
    }
}