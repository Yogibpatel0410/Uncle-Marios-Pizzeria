public class StudentDiscount implements IDiscount {
    @Override
    public double applyDiscount(double originalPrice) {
        return originalPrice * 0.90;
    }
}