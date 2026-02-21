package com.BankManagmentSystem.interfaces;

import com.BankManagmentSystem.model.Account;
import com.BankManagmentSystem.model.User;

public interface EmailService {

    /**
     * Send PENDING status email to manager after registration
     */
    void sendPendingApprovalEmail(User manager);

    /**
     * Send APPROVED status email to manager after approval
     */
    void sendApprovalEmail(User manager);

    /**
     * Send account approval email to customer with account details
     */
    void sendAccountApprovalEmail(Account account);

}
