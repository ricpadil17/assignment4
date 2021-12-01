package Blockbuster;

public class HalfOffCouponDecorator extends CouponDecorator{

    private CouponComponent component;

    public HalfOffCouponDecorator(CouponComponent c) {
        component = c;
    }

    @Override
    public double computePrice() {
        double halvedTotal = component.computePrice()/2;
        component.setTotalPrice(halvedTotal);
        return halvedTotal;
    }

    @Override
    public double getTotalPrice() {
        return component.getTotalPrice();
    }

    @Override
    public void setTotalPrice(double price) {
        component.setTotalPrice(price);
    }
}
