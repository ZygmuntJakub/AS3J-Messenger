package com.as3j.messenger.events;

import com.as3j.messenger.services.EmailService;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RegistrationEventListener {
    @Value("${mail.registration.subject}")
    private String emailSubject;
    @Value("${mail.registration.content}")
    private String messageContent;

    private EmailService emailService;

    @Autowired
    public RegistrationEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    public void handleRegistration(RegistrationEvent event) throws MailjetSocketTimeoutException, MailjetException {
        emailService.sendEmail(event.getUser().getEmail(), event.getUser().getUsername(), emailSubject, messageContent);
    }
}
