public class NoDiscount implements IDiscount {
    @Override
    public double applyDiscount(double originalPrice) {
        return originalPrice;
    }
}