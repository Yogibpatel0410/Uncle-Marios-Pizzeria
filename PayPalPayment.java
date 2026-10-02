public class PayPalPayment implements IPayment {
    @Override
    public boolean processPayment(double amount) {
        System.out.printf(
                "Processing PayPal payment of $%.2f%n",
                amount
        );
        return true;
    }
}