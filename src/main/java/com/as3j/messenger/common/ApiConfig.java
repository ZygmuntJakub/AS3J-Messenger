package com.as3j.messenger.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "as3j.api")
@ConstructorBinding
public class ApiConfig {
    private final String bucketName;
    private final String tempBucketName;
    private final String projectId;
    private final String mjPublicKey;
    private final String mjPrivateKey;
    private final String mailSender;
    private final String mailAddress;

    public ApiConfig(String bucketName, String tempBucketName, String projectId, String mjPublicKey,
                     String mjPrivateKey, String mailSender, String mailAddress) {
        this.bucketName = bucketName;
        this.tempBucketName = tempBucketName;
        this.projectId = projectId;
        this.mjPublicKey = mjPublicKey;
        this.mjPrivateKey = mjPrivateKey;
        this.mailSender = mailSender;
        this.mailAddress = mailAddress;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getTempBucketName() {
        return tempBucketName;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getMjPublicKey() {
        return mjPublicKey;
    }

    public String getMjPrivateKey() {
        return mjPrivateKey;
    }

    public String getMailSender() {
        return mailSender;
    }

    public String getMailAddress() {
        return mailAddress;
    }

}
