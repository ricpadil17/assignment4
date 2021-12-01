package Blockbuster;

import java.util.List;
import java.util.ArrayList;

public class Customer {
    private String _name;
    private List<Rental> rentals = new ArrayList<>();

    public Customer (String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }
}