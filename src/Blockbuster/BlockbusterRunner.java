package Blockbuster;

import static Blockbuster.RunnerProperties.*;

public class BlockbusterRunner {

    public static void main(String[] args) {
        populateMovieSelection();

        /*This customer demonstrates the 18-22 transaction FRP strategy.
         Eugene is 18 here. */
        Customer customerOne = new Customer(EUGENE_MCDERMOTT, EUGENE_AGE);
        Transaction transactionOne = new Transaction(customerOne);
        transactionOne.addRental(new Rental(selection.get(0), TWO_DAYS_RENTED));
        transactionOne.addRental(new Rental(selection.get(1), FOUR_DAYS_RENTED));
        transactionOne.addRental(new Rental(selection.get(2), THREE_DAYS_RENTED));
        transactionOne.addRental(new Rental(selection.get(3), ONE_DAY_RENTED));
        processTransaction(transactionOne, customerOne);

        /*This customer demonstrates the >2 types of movies FRP strategy.
         Linus is 51 and rents >2 types of movies. */
        Customer customerTwo = new Customer(LINUS_TORVALDS, LINUS_AGE);
        Transaction transactionTwo = new Transaction(customerTwo);
        transactionTwo.addRental(new Rental(selection.get(0), FOUR_DAYS_RENTED));
        transactionTwo.addRental(new Rental(selection.get(1), FOUR_DAYS_RENTED));
        transactionTwo.addRental(new Rental(selection.get(2), FOUR_DAYS_RENTED));
        processTransaction(transactionTwo, customerTwo);

        /*This customer demonstrates the base case of Transaction FRP strategies, which is none
         Grady is neither between 18-22, nor does he rent >2 types of movies. */
        Customer customerThree = new Customer(GRADY_BOOCH, GRADY_AGE);
        Transaction transactionThree = new Transaction(customerThree);
        transactionThree.addRental(new Rental(selection.get(2), THREE_DAYS_RENTED));
        transactionThree.addRental(new Rental(selection.get(1), FOUR_DAYS_RENTED));
        processTransaction(transactionThree, customerThree);
    }

    public static void processTransaction(Transaction transaction, Customer customer) {
        setRentalStrategies(transaction);
        setRentalFRPStrategies(transaction);
        setTransactionFRPStrategy(transaction, customer);
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
                r.setFrequentRenterPointsStrategy(new BonusFrpStrategy());
            }
            else {
                r.setFrequentRenterPointsStrategy(new FrequentRenterPointsStrategy());
            }
        }
    }

    public static void setTransactionFRPStrategy(Transaction transaction, Customer customer) {
        if (COLLEGE_AGE_MIN <= customer.getAge() && customer.getAge() <= COLLEGE_AGE_MAX
                && transaction.hasNewRelease()) {
            transaction.setTransactionFRPStrategy(new UndergradBonusFRPStrategy());
        }
        else if (transaction.getTotalTypesOfMovies() > DOUBLE_FRP_THRESHOLD) {
            transaction.setTransactionFRPStrategy(new DoubleFeatureFrpStrategy());
        }
        else {
            transaction.setTransactionFRPStrategy(new TransactionFRPStrategy());
        }
    }

    public static void printTransaction(Transaction transaction) {
        System.out.println(transaction.htmlRentalReceipt());
    }
}
