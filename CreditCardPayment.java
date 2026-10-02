public class CreditCardPayment implements IPayment {
    @Override
    public boolean processPayment(double amount) {
        System.out.printf(
                "Processing credit card payment of $%.2f%n",
                amount
        );
        return true;
    }
}