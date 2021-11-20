package blockbuster;

import java.util.ArrayList;
import java.util.List;

public class blockbusterRunner {

    public static void main(String[] args) {
        populateMovieSelection();

        //This customer demonstrates the 18-22 transaction FRP strategy
        Customer customerOne = new Customer("Eugene McDermott", 18);
        Transaction transactionOne = new Transaction(customerOne);
        transactionOne.addRental(new Rental(RunnerProperties.selection.get(0), RunnerProperties.TWO_DAYS_RENTED));
        transactionOne.addRental(new Rental(RunnerProperties.selection.get(1), RunnerProperties.FOUR_DAYS_RENTED));
        transactionOne.addRental(new Rental(RunnerProperties.selection.get(2), RunnerProperties.THREE_DAYS_RENTED));
        transactionOne.addRental(new Rental(RunnerProperties.selection.get(3), RunnerProperties.ONE_DAY_RENTED));
        processTransaction(transactionOne, customerOne);

        //This customer demonstrates the >2 types of movies FRP strategy
        Customer customerTwo = new Customer("Linus Torvalds", 51);
        Transaction transactionTwo = new Transaction(customerTwo);
        transactionTwo.addRental(new Rental(RunnerProperties.selection.get(0), RunnerProperties.FOUR_DAYS_RENTED));
        transactionTwo.addRental(new Rental(RunnerProperties.selection.get(1), RunnerProperties.FOUR_DAYS_RENTED));
        transactionTwo.addRental(new Rental(RunnerProperties.selection.get(2), RunnerProperties.FOUR_DAYS_RENTED));
        processTransaction(transactionTwo, customerTwo);

        //This customer demonstrates the base case of Transaction FRP strategies, which is none
        Customer customerThree = new Customer("Grady Booch", 66);
        Transaction transactionThree = new Transaction(customerThree);
        transactionThree.addRental(new Rental(RunnerProperties.selection.get(2), RunnerProperties.THREE_DAYS_RENTED));
        transactionThree.addRental(new Rental(RunnerProperties.selection.get(1), RunnerProperties.FOUR_DAYS_RENTED));
        processTransaction(transactionThree, customerThree);
    }

    public static void processTransaction(Transaction transaction, Customer customer) {
        setRentalStrategies(transaction);
        setRentalFRPStrategies(transaction);
        setTransactionFRPStrategy(transaction, customer);
        printTransaction(transaction);
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

    public static void setRentalFRPStrategies(Transaction transaction) {
        for(Rental r : transaction.getRentals()) {
            if (r.getMovie().getReleaseDate() < RunnerProperties.TWO_WEEKS) {
                r.setFrequentRenterPointsStrategy(new BonusFrpStrategy());
            }
            else {
                r.setFrequentRenterPointsStrategy(new FrequentRenterPointsStrategy());
            }
        }
    }

    public static void setTransactionFRPStrategy(Transaction transaction, Customer customer) {

        if (transaction.getTotalTypesOfMovies() > RunnerProperties.DOUBLE_FRP_THRESHOLD) {
            transaction.setFrequentRenterPointsStrategy(new DoubleFeatureFrpStrategy());
        }
        else if (RunnerProperties.COLLEGE_AGE_MIN <= customer.getAge() && customer.getAge() <= RunnerProperties.COLLEGE_AGE_MAX
                && transaction.hasNewRelease()) {
            transaction.setFrequentRenterPointsStrategy(new UndergradBonusFRPStrategy());
        }
        else {
            transaction.setFrequentRenterPointsStrategy(new TransactionFRPStrategy());
        }
    }

    public static void printTransaction(Transaction transaction) {
        System.out.println(transaction.htmlRentalReceipt());
    }
}
