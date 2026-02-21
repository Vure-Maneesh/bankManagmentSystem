package com.BankManagmentSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.BankManagmentSystem.interfaces.EmailService;
import com.BankManagmentSystem.model.Account;
import com.BankManagmentSystem.model.User;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.from:noreply@bankmanagement.com}")
    private String fromEmail;

    @Override
    public void sendPendingApprovalEmail(User manager) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(manager.getEmail());
            message.setSubject("Manager Registration Pending - Admin Approval Required");
            message.setText(buildPendingEmailBody(manager));

            mailSender.send(message);
            System.out.println("PENDING approval email sent to: " + manager.getEmail());
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void sendApprovalEmail(User manager) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(manager.getEmail());
            message.setSubject("Manager Approval Confirmed - You Can Now Login");
            message.setText(buildApprovalEmailBody(manager));

            mailSender.send(message);
            System.out.println("APPROVED email sent to: " + manager.getEmail());
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String buildPendingEmailBody(User manager) {
        return "Dear " + manager.getName() + ",\n\n"
                + "Thank you for registering as a Branch Manager in our Bank Management System.\n\n"
                + "Your registration has been received and is currently pending admin approval.\n"
                + "Status: PENDING\n\n"
                + "You will receive another email once the admin approves your registration.\n"
                + "Upon approval, you will be able to login to the system.\n\n"
                + "If you have any questions, please contact the admin.\n\n"
                + "Best regards,\n"
                + "Bank Management System\n"
                + "support@bankmanagement.com";
    }

    private String buildApprovalEmailBody(User manager) {
        return "Dear " + manager.getName() + ",\n\n"
                + "Congratulations! Your manager registration has been approved.\n\n"
                + "Status: APPROVED\n\n"
                + "You can now login to the Bank Management System using your email and password.\n\n"
                + "Login URL: http://localhost:4200/login\n"
                + "Email: " + manager.getEmail() + "\n\n"
                + "Please keep your credentials secure and do not share them with anyone.\n\n"
                + "If you have any questions, please contact the admin.\n\n"
                + "Best regards,\n"
                + "Bank Management System\n"
                + "support@bankmanagement.com";
    }

    @Override
    public void sendAccountApprovalEmail(Account account) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(account.getCustomer().getEmail());
            message.setSubject("Account Approval Confirmed - Welcome to Our Bank");
            message.setText(buildAccountApprovalEmailBody(account));

            mailSender.send(message);
            System.out.println("Account approval email sent to: " + account.getCustomer().getEmail());
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String buildAccountApprovalEmailBody(Account account) {
        return "Dear " + account.getCustomer().getName() + ",\n\n"
                + "Congratulations! Your account has been approved successfully.\n\n"
                + "========== ACCOUNT DETAILS ==========\n"
                + "Account Holder Name: " + account.getCustomer().getName() + "\n"
                + "Account Number: " + account.getAccountNumber() + "\n"
                + "Account Type: " + account.getAccountType() + "\n"
                + "Initial Balance: Rs. " + String.format("%.2f", account.getBalance()) + "\n"
                + "Account Status: " + account.getStatus() + "\n"
                + "Account Created Date: " + account.getCreatedAt() + "\n"
                + "======================================\n\n"
                + "You can now start using your account for all banking operations.\n\n"
                + "Important:\n"
                + "- Keep your account number safe and confidential\n"
                + "- Do not share your credentials with anyone\n"
                + "- Review your account regularly for unauthorized transactions\n\n"
                + "For any assistance, please contact our support team at support@bankmanagement.com\n\n"
                + "Best regards,\n"
                + "Bank Management System\n"
                + "support@bankmanagement.com";
    }

}
