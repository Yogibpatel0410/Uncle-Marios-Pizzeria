public class MenuItem implements IMenuItems {

    private String name;
    private double price;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void order() {
        System.out.println(name + " ordered.");
    }

    @Override
    public void pay() {
        System.out.println(name + " marked as paid.");
    }

    @Override
    public String toString() {
        return String.format("%s - $%.2f", name, price);
    }
}