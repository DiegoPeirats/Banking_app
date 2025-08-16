package loan;

import lombok.Getter;

@Getter
public enum BorrowerRiskLevel {

    HIGH_RISK(10.0),     
    MEDIUM_RISK(7.0),   
    LOW_RISK(4.0);       

    private final double baseInterestRate;

    BorrowerRiskLevel(double baseInterestRate) {
        this.baseInterestRate = baseInterestRate;
    }
}