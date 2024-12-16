package com.example.Employee_Details.Utils;

import com.example.Employee_Details.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Log4j2
@Component
public class MailUtil {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendEmail(User newUser, String subject, String messageBody) {
//        if (newUser == null || newUser.getEmail() == null || newUser.getEmail().isEmpty()) { // Updated: Added null/empty check for user and email.
//            log.warn("Cannot send email. User or email address is null/empty."); // Updated: Added warning log for invalid input.
//            return;
//        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(newUser.getEmail());
//            message.setSubject(subject); // Updated: Used the passed 'subject' parameter instead of hardcoded value.
//            message.setText(messageBody);
            message.setSubject("Welcome to Employee Details App");
            message.setText("Hi, \n\n Thank you for registering with us.");
            mailSender.send(message);
            log.info("Registration email sent to {}", newUser.getEmail());
        } catch (Exception e) {
            log.error("Failed to send registration email", e);
//            log.error("Failed to send registration email to {}: {}", newUser.getEmail(), e.getMessage(), e); // Updated: Enhanced error log with email and error details.
        }
    }
}
