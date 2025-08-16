package loan;

import lombok.Getter;

@Getter
public enum LoanType {
    
    HOUSE(3.5, 30),
    VEHICLE(5.0, 20),
    PERSONAL(7.5, 10);

    private final double interestRate;
    
    private final Integer minAmountInAccount;

    LoanType(double interestRate, Integer minAmountInAccount) {
        this.interestRate = interestRate;
        this.minAmountInAccount = minAmountInAccount;
    }
}
