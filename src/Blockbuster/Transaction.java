package Blockbuster;

import java.util.ArrayList;
import java.util.List;

import static Blockbuster.RunnerProperties.TWO_WEEKS;

public class Transaction {
    private List<Rental> rentals = new ArrayList<>();
    private Customer customer;
    private TransactionFRPStrategy transactionFRPStrategy;

    public Transaction(Customer c){
        customer = c;
    }

    public void addRental(Rental r) {
        rentals.add(r);
    }

    public List<Rental> getRentals() {
        return rentals;
    }

        private double getTotalCharge() {
        double total = 0;
        for (Rental rental : rentals)
            total += rental.getRentalPrice();
        return total;
    }

    private double getRentalFRP() {
        double rentalTotal = 0;
        for (Rental rental: rentals)
            rentalTotal += rental.getFrequentRenterPoints();
        return rentalTotal;
    }

    private double getTotalFrequentRenterPoints() {
        double totalFRP = getRentalFRP();
        totalFRP += computeTransactionFRP();
        return totalFRP;
    }

    public void setTransactionFRPStrategy(TransactionFRPStrategy strategy) {
        this.transactionFRPStrategy = strategy;
    }

    public double computeTransactionFRP() {
        return transactionFRPStrategy.computeFrequentRenterPoints(this);
    }

    public int getTotalTypesOfMovies() {
        List<Movie> movies = new ArrayList<>();
        int instances = 0;
        movies.add(rentals.get(0).getMovie());
        for(Rental r: rentals) {
            instances = 0;
            for(int i = 0; i < movies.size(); i++) {
                if(r.getMovie().getGenre() == movies.get(i).getGenre()){
                    instances++;
                }
            }
            if(instances > 0) {
                movies.add(r.getMovie());
            }
        }
        return movies.size();
    }

    public boolean hasNewRelease() {
        for(Rental r: rentals) {
            if(r.getMovie().getReleaseDate()< TWO_WEEKS) {
                return true;
            }
        }
        return false;
    }

    public String htmlRentalReceipt() {
        String result = "<h1>Rental record for <em>" + customer.getName() + "</em></h1>\n<table>\n";
        for (Rental rental : rentals)
            result += "\t<tr><td>" + rental.getMovie().getTitle() + "</td><td>" + ": "
                    + rental.getRentalPrice() + "</td></tr>\n";
        result += "</table>\n<p>Amount owed is <em>" + getTotalCharge() + "</em></p>\n";
        result += "<p>You earned <em>" + getTotalFrequentRenterPoints()
                + "</em> frequent renter points</p>\n\n";
        return result;
    }
}
