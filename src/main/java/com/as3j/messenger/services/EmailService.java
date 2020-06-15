package com.as3j.messenger.services;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;

public interface EmailService {
    void sendEmail(String address, String name, String subject, String message)
            throws MailjetSocketTimeoutException, MailjetException;
}
