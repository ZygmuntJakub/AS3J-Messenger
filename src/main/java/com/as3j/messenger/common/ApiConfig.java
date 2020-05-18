package com.as3j.messenger.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "as3j.api")
@ConstructorBinding
public class ApiConfig {
    private final String bucketName;
    private final String tempBucketName;

    public ApiConfig(String bucketName, String tempBucketName) {
        this.bucketName = bucketName;
        this.tempBucketName = tempBucketName;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getTempBucketName() {
        return tempBucketName;
    }
}
