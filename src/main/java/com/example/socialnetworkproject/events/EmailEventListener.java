package com.example.socialnetworkproject.events;

import com.example.socialnetworkproject.models.entities.CustomUserDetails;
import com.example.socialnetworkproject.models.entities.PasswordToken;
import com.example.socialnetworkproject.repositories.PasswordTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Component
public class EmailEventListener {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private PasswordTokenRepository tokenRepository;

    private final long JWT_EXPIRATION = 604800000L;


    @Async
    @EventListener
    public void sendEmail(SendEmailEvent event){
        // Try block to check for exceptions
        try {
            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details

            Random random = new Random();
            int number = random.nextInt(999999);

            String token = String.format("%06d",number);
            PasswordToken passwordToken = new PasswordToken();

            LocalDateTime now = LocalDateTime.now();

            LocalDateTime expiryDateTime = now.plus(JWT_EXPIRATION, ChronoUnit.MILLIS);

            passwordToken.setToken(token);
            passwordToken.setExpiredDate(expiryDateTime);
            passwordToken.setUsers(event.getUser());

            tokenRepository.save(passwordToken);

            mailMessage.setSubject("Click to this link to reset password");
            mailMessage.setFrom(sender);
            mailMessage.setTo(event.getEmail());
            mailMessage.setText("This link will forward you to the page that will help you reset the password: http://localhost.com/api/auth/forgot-password/" + token);

            // Sending the mail
            javaMailSender.send(mailMessage);

        }
        catch (Exception e) {
            System.out.println("Error while Sending Mail");
        }
    }
}
