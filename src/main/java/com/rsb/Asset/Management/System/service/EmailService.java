package com.rsb.Asset.Management.System.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired private JavaMailSender mailSender;

    public void sendOtpEmail(String to, String otp) {
        String body = "Your OTP is: " + otp + "\nIt will expire in 5 minutes.";
        sendEmail(to, "Your OTP Code", body);
    }

    public void sendResetLink(String to, String token) {
        String resetUrl = "http://localhost:5173/reset-password?token=" + token;
        String body = "Click below to reset your password:\n" + resetUrl;
        sendEmail(to, "Password Reset Link", body);
    }

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}