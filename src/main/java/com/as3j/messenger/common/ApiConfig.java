package com.as3j.messenger.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "as3j.api")
@ConstructorBinding
public class ApiConfig {
    private final String bucketName;
    private final String tempBucketName;
    private final String projectId;

    public ApiConfig(String bucketName, String tempBucketName, String projectId) {
        this.bucketName = bucketName;
        this.tempBucketName = tempBucketName;
        this.projectId = projectId;
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
}
