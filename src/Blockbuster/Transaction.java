package Blockbuster;

import java.util.ArrayList;
import java.util.List;

public class Transaction extends CouponComponent{
    private List<Rental> rentals = new ArrayList<>();
    private Customer customer;
    private double totalPrice;
    private double transactionFRP;

    public Transaction(Customer c){
        customer = c;
        totalPrice = 0;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double price) {
        totalPrice = price;
    }

    public void addRental(Rental r) {
        rentals.add(r);
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void removeRental(int index) {
        if(index < rentals.size()) {
            rentals.remove(index);
        }
    }

    public double computePrice() {
        double total = 0;
        for (Rental rental : rentals)
            total += rental.getRentalPrice();
        totalPrice = total;
        return total;
    }

    public double computeFRP() {
        double rentalTotal = 0;
        for (Rental rental: rentals)
            rentalTotal += rental.getFrequentRenterPoints();
        transactionFRP = rentalTotal;
        return rentalTotal;
    }

    public double getTransactionFRP() {
        if(transactionFRP == 0) {
            computeFRP();
        }
        return transactionFRP;
    }

    public String htmlRentalReceipt() {
        String result = "<h1>Rental record for <em>" + customer.getName() + "</em></h1>\n<table>\n";
        for (Rental rental : rentals)
            result += "\t<tr><td>" + rental.getMovie().getTitle() + "</td><td>" + ": "
                    + rental.getRentalPrice() + "</td></tr>\n";
        result += "</table>\n<p>Amount owed is <em>" + getTotalPrice() + "</em></p>\n";
        result += "<p>You earned <em>" + getTransactionFRP()
                + "</em> frequent renter points</p>\n\n";
        return result;
    }
}
