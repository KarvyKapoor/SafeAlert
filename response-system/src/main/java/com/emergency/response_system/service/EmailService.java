package com.emergency.response_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String message) {

    System.out.println("📧 EMAIL METHOD ENTERED → " + to);

    try {
        System.out.println("📤 BEFORE MAIL SEND");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject("Emergency Alert");
        mail.setText(message);

        mailSender.send(mail);

        System.out.println("✅ EMAIL SENT → " + to);

    } catch (Exception e) {
        System.out.println("❌ EMAIL FAILED");
        e.printStackTrace();
    }
}
}