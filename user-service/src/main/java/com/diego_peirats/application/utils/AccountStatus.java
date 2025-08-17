package com.diego_peirats.application.utils;

public enum AccountStatus {
    
    ACCOUNT_EXISTS("001", "This user already has an account"),
    ACCOUNT_CREATED("002", "Account has been successfully created"),
    ACCOUNT_NOT_FOUND("003", "User wasn't found"),
    ACCOUNT_FOUND("004", "User account found"),
    ACCOUNT_CREDITED("005", "User account credited"),
    INSUFFICIENT_BALANCE("006", "Balance on the account was not enough"),
    ACCOUNT_DEBITED("007", "User account debited"),
    TRANSFER_SUCCESSFUL("008", "Transfer Successful"),
    NOT_AUTHORIZED_TRANSFER("009", "Transfer Not Authorized"),
    LOAN_ACCEPTED("010", "Loan accepted"),
    LOAN_DENIED("011", "Loan denied");

    private final String code;
    private final String message;

    AccountStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}
