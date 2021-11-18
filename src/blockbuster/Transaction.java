package blockbuster;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private List<Rental> rentals = new ArrayList<>();
    private Customer customer;
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

    private double getTotalFrequentRenterPoints() {
        double total = 0;
        for (Rental rental: rentals)
            total += rental.getFrequentRenterPoints();
        return total;
    }

    public int getTotalTypesofMovies() {
        List<Movie> movies = new ArrayList<>();
        int instances = 0;
        movies.add(rentals.get(0).getMovie());
        for(Rental r: rentals) {
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
            if(r.getMovie().getReleaseDate()< RunnerProperties.TWO_WEEKS) {
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
                + "</em> frequent renter points</p>";
        return result;
    }
}
