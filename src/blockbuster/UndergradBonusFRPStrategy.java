package blockbuster;

import java.util.List;

public class UndergradBonusFRPStrategy extends TransactionFRPStrategy{

    public UndergradBonusFRPStrategy(){

    }

    public double computeFrequentRenterPoints(Transaction transaction){
        double totalBonusPoints;

        List<Rental> rentals = transaction.getRentals();
        totalBonusPoints = rentals.size();

        return totalBonusPoints;
    }
}
