package com.example.CustomerManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender emailSender;

    @Autowired
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendNewCustomerNotification(String to, String customerName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("New Customer Added");
        message.setText("A new customer has been added: " + customerName);
        emailSender.send(message);
    }

    public void sendCustomerRemovedNotification(String to, String customerName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Customer Removed");
        message.setText("A customer has been removed: " + customerName);
        emailSender.send(message);
    }
}
