package blockbuster;

import java.util.List;
import java.util.ArrayList;

public class Customer {
    private String _name;
    private List<Rental> rentals = new ArrayList<>();

    public Customer (String name) {
        _name = name;
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    public String getName() {
        return _name;
    }

    public String htmlRentalReceipt() {
        String result = "<h1>Rental record for <em>" + getName() + "</em></h1>\n<table>\n";
        for (Rental rental : rentals)
            result += "\t<tr><td>" + rental.getMovie().getTitle() + "</td><td>" + ": "
                    + rental.getRentalPrice() + "</td></tr>\n";
        result += "</table>\n<p>Amount owed is <em>" + getTotalCharge() + "</em></p>\n";
        result += "<p>You earned <em>" + getTotalFrequentRenterPoints()
                + "</em> frequent renter points</p>";
        return result;
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
}