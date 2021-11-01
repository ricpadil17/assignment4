package blockbuster;

import blockbuster.RunnerProperties;

public class blockbusterRunner {

    public static void main(String[] args) {
        Customer customer = new Customer("Eugene McDermott");
        populateMovieSelection();

        customer.addRental(new Rental(RunnerProperties.selection.get(0), RunnerProperties.TWO_DAYS_RENTED, new RegularPriceStrategy(), new FrequentRenterPointsStrategy()));
        customer.addRental(new Rental(RunnerProperties.selection.get(1), RunnerProperties.FOUR_DAYS_RENTED, new ChildrensPriceStrategy(), new FrequentRenterPointsStrategy()));
        customer.addRental(new Rental(RunnerProperties.selection.get(2), RunnerProperties.THREE_DAYS_RENTED ,new NewPriceStrategy(), new BonusFrequentRenterPointsStrategy()));

        System.out.println(customer.htmlRentalReceipt());

    }

    public static void populateMovieSelection() {
        RunnerProperties.selection.add(new Movie("Jurassic Park"));
        RunnerProperties.selection.add(new Movie("Empire Strikes Back"));
        RunnerProperties.selection.add(new Movie("Parasite"));
    }
}
