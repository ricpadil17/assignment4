package Blockbuster;

import static Blockbuster.RunnerProperties.*;


public class BlockbusterRunner {

    public static void main(String[] args) {
        populateMovieSelection();

        /* Demonstrates 50% off entire rental price here
           Original price is $15.5, but coupon reduces to $7.75*/
        Customer customerOne = new Customer(EUGENE_MCDERMOTT);
        Transaction transactionOne = new Transaction(customerOne);

        transactionOne.addRental(new Rental(selection.get(0), TWO_DAYS_RENTED));
        transactionOne.addRental(new Rental(selection.get(1), FOUR_DAYS_RENTED));
        transactionOne.addRental(new Rental(selection.get(2), THREE_DAYS_RENTED));
        transactionOne.addRental(new Rental(selection.get(3), ONE_DAY_RENTED));
        setRentalStrategies(transactionOne);
        setRentalFRPStrategies(transactionOne);
        computeRentalPrice(new HalfOffCouponDecorator(transactionOne));
        //uncomment the line below, and COMMENT the line above, to see the price computed without the coupon
//        computeRentalPrice(transactionOne);
        processTransaction(transactionOne);

        /* Demonstrates $10 off $50 total rental more than 50$ here
           Original Price is $128, but coupon reduces to $118*/
        Customer customerTwo = new Customer(LINUS_TORVALDS);
        Transaction transactionTwo = new Transaction(customerTwo);
        transactionTwo.addRental(new Rental(selection.get(0), FOUR_DAYS_RENTED));
        transactionTwo.addRental(new Rental(selection.get(1), FOUR_DAYS_RENTED));
        transactionTwo.addRental(new Rental(selection.get(2), FORTY_DAYS_RENTED));
        setRentalStrategies(transactionTwo);
        setRentalFRPStrategies(transactionTwo);
        computeRentalPrice(new TenOffCouponDecorator(transactionTwo));
        //uncomment the line below, and COMMENT the line above, to see the price computed without the coupon
//        computeRentalPrice(transactionTwo);
        processTransaction(transactionTwo);

        /* Demonstrates combined coupon case here
           Original amount owed is $66. With $10 off AND 50% off, new total is $28 */
        Customer customerThree = new Customer(GRADY_BOOCH);
        Transaction transactionThree = new Transaction(customerThree);
        transactionThree.addRental(new Rental(selection.get(2), THREE_DAYS_RENTED));
        transactionThree.addRental(new Rental(selection.get(1), FORTY_DAYS_RENTED));
        setRentalStrategies(transactionThree);
        setRentalFRPStrategies(transactionThree);
        computeRentalPrice(new HalfOffCouponDecorator(new TenOffCouponDecorator(transactionThree)));
        //uncomment the line below, and COMMENT the line above, to see the price computed without the coupon
//        computeRentalPrice(transactionThree);
        processTransaction(transactionThree);


        /* Demonstrate the 10 FRP = a free movie here
        With 11 FRP's, a movie is removed, the price is adjusted, but the FRP remains in tact
        Despite the Free Movie being deducted from the total price
           */
        Customer customerFour = new Customer(ETHAN_SILVER);
        Transaction transactionFour = new Transaction(customerFour);
        transactionFour.addRental(new Rental(selection.get(2), THREE_DAYS_RENTED));
        transactionFour.addRental(new Rental(selection.get(1), FORTY_DAYS_RENTED));
        transactionFour.addRental(new Rental(selection.get(2), THREE_DAYS_RENTED));
        transactionFour.addRental(new Rental(selection.get(2), THREE_DAYS_RENTED));
        transactionFour.addRental(new Rental(selection.get(2), THREE_DAYS_RENTED));
        transactionFour.addRental(new Rental(selection.get(2), THREE_DAYS_RENTED));

        setRentalStrategies(transactionFour);
        setRentalFRPStrategies(transactionFour);
        computeRentalPrice(new FreeMovieCouponDecorator(transactionFour));
        //uncomment the line below, and COMMENT the line above, to see the price computed without the coupon
//        computeRentalPrice(transactionFour);
        processTransaction(transactionFour);
    }

    public static void processTransaction(Transaction transaction) {

        printTransaction(transaction);
    }

    public static void populateMovieSelection() {
        selection.add(new Movie(JURASSIC_PARK, THRILLER_GENRE, TWO_YEARS));
        selection.add(new Movie(EMPIRE_STRIKES_BACK, CHILDRENS_GENRE, THREE_WEEKS));
        selection.add(new Movie(PARASITE, THRILLER_GENRE, ONE_WEEK));
        selection.add(new Movie(CORY_IN_THE_HOUSE, CHILDRENS_GENRE, TWO_WEEKS));
    }

    public static void setRentalStrategies(Transaction transaction) {
        for(Rental r : transaction.getRentals()) {
            if (r.getMovie().getReleaseDate() < TWO_WEEKS) {
                r.setPriceStrategy(new NewPriceStrategy());
            }
            else if (r.getMovie().getGenre().equalsIgnoreCase(CHILDRENS_GENRE)) {
                r.setPriceStrategy(new ChildrensPriceStrategy());
            }
            else {
                r.setPriceStrategy(new RegularPriceStrategy());
            }
        }
    }

    public static void setRentalFRPStrategies(Transaction transaction) {
        for(Rental r : transaction.getRentals()) {
            if (r.getMovie().getReleaseDate() < TWO_WEEKS) {
                r.setFrequentRenterPointsStrategy(new BonusFrequentRenterPointsStrategy());
            }
            else {
                r.setFrequentRenterPointsStrategy(new FrequentRenterPointsStrategy());
            }
        }
    }

    public static void computeRentalPrice(CouponComponent component) {
        component.computePrice();
    }

    public static void printTransaction(Transaction transaction) {
        System.out.println(transaction.htmlRentalReceipt());
    }
}
