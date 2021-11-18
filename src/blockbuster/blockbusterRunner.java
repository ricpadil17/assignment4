package blockbuster;

import java.util.ArrayList;
import java.util.List;

public class blockbusterRunner {

    public static void main(String[] args) {
        Customer customer = new Customer("Eugene McDermott", 18);
        Transaction transaction = new Transaction(customer);
        List<Rental> Rentals = new ArrayList<>();
        populateMovieSelection();

        transaction.addRental(new Rental(RunnerProperties.selection.get(0), RunnerProperties.TWO_DAYS_RENTED));
        transaction.addRental(new Rental(RunnerProperties.selection.get(1), RunnerProperties.FOUR_DAYS_RENTED));
        transaction.addRental(new Rental(RunnerProperties.selection.get(2), RunnerProperties.THREE_DAYS_RENTED));
        transaction.addRental(new Rental(RunnerProperties.selection.get(3), RunnerProperties.ONE_DAY_RENTED));

//        Rentals.add(new Rental(RunnerProperties.selection.get(0), RunnerProperties.TWO_DAYS_RENTED));
//        Rentals.add(new Rental(RunnerProperties.selection.get(1), RunnerProperties.FOUR_DAYS_RENTED));
//        Rentals.add(new Rental(RunnerProperties.selection.get(2), RunnerProperties.THREE_DAYS_RENTED));
//        Rentals.add(new Rental(RunnerProperties.selection.get(3), RunnerProperties.ONE_DAY_RENTED));

        setRentalStrategies(transaction);
        setFRPStrategies(transaction, customer);
//        addCustomerRentals(customer, transaction.getRentals());

        System.out.println(transaction.htmlRentalReceipt());
    }



    public static void populateMovieSelection() {
        RunnerProperties.selection.add(new Movie("Jurassic Park", RunnerProperties.THRILLER_GENRE, RunnerProperties.TWO_YEARS));
        RunnerProperties.selection.add(new Movie("Empire Strikes Back", RunnerProperties.CHILDRENS_GENRE, RunnerProperties.THREE_WEEKS));
        RunnerProperties.selection.add(new Movie("Parasite", RunnerProperties.THRILLER_GENRE, RunnerProperties.ONE_WEEK));
        RunnerProperties.selection.add(new Movie("Cory in the House", RunnerProperties.CHILDRENS_GENRE, RunnerProperties.TWO_WEEKS));
    }

    public static void setRentalStrategies(Transaction transaction) {
        for(Rental r : transaction.getRentals()) {
            if (r.getMovie().getReleaseDate() < RunnerProperties.TWO_WEEKS) {
                r.setPriceStrategy(new NewPriceStrategy());
            }
            else if (r.getMovie().getGenre().equalsIgnoreCase(RunnerProperties.CHILDRENS_GENRE)) {
                r.setPriceStrategy(new ChildrensPriceStrategy());
            }
            else {
                r.setPriceStrategy(new RegularPriceStrategy());
            }
        }
    }

    public static void setFRPStrategies(Transaction transaction, Customer customer) {
        for(Rental r : transaction.getRentals()) {
            if (r.getMovie().getReleaseDate() < RunnerProperties.TWO_WEEKS) {
                r.setFrequentRenterPointsStrategy(new BonusFrpStrategy());
            }
            /*
            else if (transaction.getTotalTypesofMovies() > RunnerProperties.DOUBLE_FRP_THRESHOLD) {
                r.setFrequentRenterPointsStrategy(new DoubleFeatureFrpStrategy());
            }
            else if (RunnerProperties.COLLEGE_AGE_MIN <= customer.getAge() && customer.getAge() <= RunnerProperties.COLLEGE_AGE_MAX
            && transaction.hasNewRelease()) {
                r.setFrequentRenterPointsStrategy(new UndergradBonusFRPStrategy());
            }
            TODO: add extra if cases for new strategies.
            TODO: need to account for REGULAR POINTS vs BONUS POINTS
             */
            else {
                r.setFrequentRenterPointsStrategy(new FrequentRenterPointsStrategy());
            }
        }
    }

//    public static void addCustomerRentals(Customer customer, List<Rental> rentals) {
//        for(Rental r : rentals) {
//            transaction.addRental(r);
//        }
//    }
}
