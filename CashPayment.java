public class CashPayment implements IPayment {
    @Override
    public boolean processPayment(double amount) {
        System.out.printf(
                "Receiving cash payment of $%.2f%n",
                amount
        );
        return true;
    }
}