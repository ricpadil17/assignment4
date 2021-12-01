package Blockbuster;

public class TenOffCouponDecorator extends CouponDecorator{
    private CouponComponent component;

    public TenOffCouponDecorator(CouponComponent c) {
        component = c;
    }

    @Override
    public double computePrice() {
        double newTotal = component.computePrice();

        if(newTotal >=50) {
            newTotal -= 10;
        }
        component.setTotalPrice(newTotal);
        return newTotal;
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
