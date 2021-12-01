package Blockbuster;

public class FreeMovieCouponDecorator extends CouponDecorator{
    private CouponComponent component;

    public FreeMovieCouponDecorator(CouponComponent c) {
        component = c;
    }

    @Override
    public double computePrice() {
        double newTotal;
        if(component.computeFRP() >= 10) {
            component.removeRental(0);
            newTotal = component.computePrice();
        }
        else {
            newTotal = component.computePrice();
        }
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
