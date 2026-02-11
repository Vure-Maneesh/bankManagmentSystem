package com.BankManagmentSystem.model;

public class AccountNumberGenerator {

    private String generateAccountNumber(Account account) {

        String branchCode = account.getCustomer()
                .getBranch()
                .getBranchId()
                .toString();

        String timestamp = String.valueOf(System.currentTimeMillis());

        return branchCode + timestamp.substring(timestamp.length() - 6);
    }

}
