package com.example.socialnetworkproject.events;

import com.example.socialnetworkproject.models.entities.CustomUserDetails;
import com.example.socialnetworkproject.models.entities.PasswordToken;
import com.example.socialnetworkproject.repositories.PasswordTokenRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
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
        try {

            SimpleMailMessage mailMessage = new SimpleMailMessage();

            PasswordToken passwordToken = new PasswordToken();

            LocalDateTime now = LocalDateTime.now();

            LocalDateTime expiryDateTime = now.plus(JWT_EXPIRATION, ChronoUnit.MILLIS);

            passwordToken.setToken(event.getEncodedString());
            passwordToken.setExpiredDate(expiryDateTime);

            tokenRepository.save(passwordToken);

            mailMessage.setSubject("Click to this link to reset password");
            mailMessage.setFrom(sender);
            mailMessage.setTo(event.getEmail());


            mailMessage.setText("This link will forward you to the page that will help you reset the password: " +"http://localhost.com/api/auth/forgot-password/reset/" + URLEncoder.encode(event.getEncodedString()));

            // Sending the mail
            javaMailSender.send(mailMessage);

        }
        catch (Exception e) {
            log.error("Error while Sending Mail" +e.getMessage());
        }
    }
}
