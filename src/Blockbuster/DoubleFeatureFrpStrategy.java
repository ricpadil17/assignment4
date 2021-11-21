package Blockbuster;

import java.util.List;

public class DoubleFeatureFrpStrategy extends TransactionFRPStrategy{

    public DoubleFeatureFrpStrategy(){

    }

    public double computeFrequentRenterPoints(Transaction transaction){
        double totalBonusPoints;

        List<Rental> rentals = transaction.getRentals();
        totalBonusPoints = rentals.size();

        return totalBonusPoints;
    }
}
