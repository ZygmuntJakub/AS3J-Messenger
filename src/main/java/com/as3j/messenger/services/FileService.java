package com.as3j.messenger.services;

import com.as3j.messenger.exceptions.ErrorProcessingImageException;
import com.as3j.messenger.exceptions.NoSuchFileException;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileService {
    UUID uploadTempPhoto(MultipartFile file) throws ErrorProcessingImageException;

    void updatePhoto(UUID tempPhotoId, UUID userId) throws NoSuchFileException;
}
