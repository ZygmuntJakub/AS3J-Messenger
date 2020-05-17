package com.as3j.messenger.services;

import com.as3j.messenger.exceptions.ErrorProcessingImageException;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileService {
    UUID uploadTempPhoto(MultipartFile file) throws ErrorProcessingImageException;
}
