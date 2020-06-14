package com.as3j.messenger.services.impl;

import com.as3j.messenger.common.ApiConfig;
import com.as3j.messenger.services.EmailService;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class EmailServiceImpl implements EmailService {
    private final ApiConfig apiConfig;
    private MailjetClient client;

    @Autowired
    public EmailServiceImpl(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    @Override
    public void sendEmail(String address, String name, String subject, String message)
            throws MailjetSocketTimeoutException, MailjetException {
        MailjetRequest request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", apiConfig.getMailAddress())
                                        .put("Name", apiConfig.getMailSender()))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                            .put("Email", address)
                                            .put("Name", name)))
                        .put(Emailv31.Message.SUBJECT, subject)
                        .put(Emailv31.Message.HTMLPART, message)));
        client.post(request);
    }

    @PostConstruct
    public void initialize() {
        this.client = new MailjetClient(apiConfig.getMjPublicKey(), apiConfig.getMjPrivateKey());
    }
}
