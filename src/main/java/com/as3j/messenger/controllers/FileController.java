package com.as3j.messenger.controllers;

import com.as3j.messenger.dto.SingleValueDTO;
import com.as3j.messenger.exceptions.ErrorProcessingImageException;
import com.as3j.messenger.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(path = "/photos", consumes = "multipart/form-data")
    public SingleValueDTO<UUID> uploadPhoto(@RequestParam("file")MultipartFile file) throws ErrorProcessingImageException {
        return SingleValueDTO.of(fileService.uploadTempPhoto(file));
    }
}
